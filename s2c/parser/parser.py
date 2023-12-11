import xml.etree.ElementTree as ElementTree
import urllib.parse

from pathlib import Path
from shutil import copy
from s2c import ERRORS, NOT_SUPPORTED_FILE_ERROR, isdebug
from s2c.optimizer import optimizer
from s2c.parser.file_template import template
from s2c.parser.nodes.draw_node import ArcToDrawNode, CurveToDrawNode, DrawNode, HorizontalLineToDrawNode, LineToDrawNode, MoveToDrawNode, ReflectiveCurveToDrawNode, VerticalLineToDrawNode

SHELL_COLOR_WARNING="\033[43m"
SHELL_NO_COLOR = "\033[0m"
ANDROID_NS = "{http://schemas.android.com/apk/res/android}"
SVG_NS = "{http://www.w3.org/2000/svg}"

def _get_width(
    root: ElementTree.Element,
    is_xml: bool,
) -> str:
    return root.attrib[f"{ANDROID_NS}width" if is_xml else "width"].removesuffix("dp")

def _get_height(
    root: ElementTree.Element,
    is_xml: bool,
) -> str:
    return root.attrib[f"{ANDROID_NS}height" if is_xml else "height"].removesuffix("dp")

def _get_viewport(
    root: ElementTree.Element,
    is_xml: bool,
) -> tuple[str, str]:
    if (is_xml):
        return root.attrib[f"{ANDROID_NS}viewportWidth"], root.attrib[f"{ANDROID_NS}viewportHeight"]
    else:
        viewbox = root.attrib["viewBox"].split()
        return viewbox[-2], viewbox[-1]

def _get_fill(
    pathElement: ElementTree.Element,
    is_xml: bool,
) -> str:
    return pathElement.get(f"{ANDROID_NS}fillColor" if is_xml else "fill")

def _get_opacity(
    pathElement: ElementTree.Element,
    is_xml: bool,
) -> str:
    return pathElement.get(f"{ANDROID_NS}opacity" if is_xml else "opacity")

def _get_path(
    pathElement: ElementTree.Element,
    is_xml: bool,
) -> str:
    return pathElement.get(f"{ANDROID_NS}pathData" if is_xml else "d")

def _get_path_nodes(
    child: ElementTree.ElementTree,
    is_xml: bool,    
):
    if (isdebug()):
        print(f"child tag={child.tag}")

    path = _get_path(child, is_xml)

    normalized_path = __normalize_path(path)

    if isdebug():
        print()
        print("========================== Starting path ==========================")
        print()

    commands = normalized_path.split()
    last_command = ''
    nodes: list[DrawNode] = []
    while len(commands) > 0:
        current = commands[0]
        current_command = current[0]

        if ((current_command.isdigit() or current_command == "-") and last_command != ""):
            current_command = last_command
            current = last_command + current
        
        if current.lower().startswith("m"):
            nodes.append(MoveToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("a"):
            nodes.append(ArcToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("v"):
            nodes.append(VerticalLineToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("h"):
            nodes.append(HorizontalLineToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("l"):
            nodes.append(LineToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("c"):
            nodes.append(CurveToDrawNode(commands, current_command.islower()))
            last_command = current_command
        elif current.lower().startswith("s"):
            nodes.append(ReflectiveCurveToDrawNode(commands, current_command.islower()))
            last_command = current_command


    plain_nodes = "".join(list(map(lambda node: node.materialize(), nodes)))
    return plain_nodes, normalized_path

def _process_path(
    child: ElementTree.ElementTree,
    is_xml: bool,
) -> str:
    plain_nodes, normalized_path = _get_path_nodes(child, is_xml)
    fill_color = _get_fill(child, is_xml)
    opacity = _get_opacity(child, is_xml)

    color = "SolidColor(Color.Black), // No color defined. Using fallback color."
    if fill_color and fill_color.startswith("#"):
        real_color = fill_color.removeprefix("#")
        if (len(real_color) == 6):
            real_color = "FF" + real_color

        color = f"SolidColor(Color(0x{real_color})),"

    path_params_list = []
    if fill_color:
        path_params_list.append(f"fill = {color}")

    if opacity:
        path_params_list.append(f"fillAlpha = {float(opacity)}f,")

    path_params = "\n                ".join(path_params_list)
    current_path = f"""
            // {normalized_path}
            path(
                {path_params}
            ) {{
                {plain_nodes}
            }}"""

    if isdebug():
        print()
        print("========================== Finished path ==========================")
        print()

    return current_path

def _process_group(
    root: ElementTree.ElementTree,
    child: ElementTree.ElementTree,
    is_xml: bool,
):
    if isdebug():
        print()
        print("========================== Starting group =========================")
        print()

    group_paths = []
    clip_paths = []
    for group_child in child:
        if group_child.tag.startswith("path") or group_child.tag == f"{SVG_NS}path":
            group_paths.append(_process_path(group_child, is_xml))
        elif is_xml and group_child.tag.startswith("clip-path"):
            clip_nodes, _ = _get_path_nodes(group_child, is_xml)
            clip_paths.append(clip_nodes)

    # svg group is handled different from android xml.
    # in case, we need to find the clip_path from the mask element when we have a
    # group.
    if not is_xml:
        mask_id = child.get("mask").removeprefix("url(#").removesuffix(")")
        xpath = f".//{SVG_NS}mask[@id='{mask_id}']/*"
        mask_element = root.findall(xpath)
        print(mask_element)
        for mask_child in mask_element:
            clip_nodes, _ = _get_path_nodes(mask_child, is_xml)
            clip_paths.append(clip_nodes)

    group_paths = "\n                ".join(group_paths)
    clip_paths = "\n                ".join(clip_paths)
    group =  f"""
            group(
                clipPathData = PathData {{
                    {clip_paths}
                }},
            ) {{
                {group_paths}
            }}
    """.rstrip()

    if isdebug():
        print()
        print("========================== End group ==============================")
        print()

    return group

def parse(
    file: Path,
    optimize: bool,
    package: str,
    theme: str,
    context_provider: str,
    add_to_material: bool,
) -> str:
    extension = file.suffix
    print(f"👓 Parsing the {extension} file")

    if extension != ".svg" and extension != ".xml":
        print(ERRORS[NOT_SUPPORTED_FILE_ERROR])
        exit(NOT_SUPPORTED_FILE_ERROR)

    # Copy file to work it locally
    target_file_name = f"target{extension}"
    copy(file, target_file_name)
    target_file = Path(target_file_name)

    if optimize:
        optimizer.optimize(file)
        target_file = Path("target.xml")
    else:
        print(f"{SHELL_COLOR_WARNING} WARNING ⚠️ {SHELL_NO_COLOR}: Generating Vector paths without optimization.")
        print("             The image may be incomplete due to non-optimized svg issues.")
        print("             It is strongly adviced to run this script with optimization instead.")

    tree = ElementTree.parse(target_file)
    root = tree.getroot()
    is_xml = target_file.name.endswith(".xml")

    width = _get_width(root, is_xml)
    height = _get_height(root, is_xml)
    
    viewport_width, viewport_height=_get_viewport(root, is_xml)

    if isdebug():
        print()
        print("======================== Retrieving base data =======================")
        print(f"is_xml = {is_xml}")
        print(f"width = {width}")
        print(f"height = {height}")
        print(f"viewport_width = {viewport_width}")
        print(f"viewport_width = {viewport_width}")
        print()
        print("====================================================================")

    paths = []
    has_group = False
    for child in root:
        if child.tag.endswith("path"):
            paths.append(_process_path(child, is_xml))
        elif child.tag.endswith("group" if is_xml else "g"):
            has_group = True
            paths.append(_process_group(root, child, is_xml))
        else:
            title = urllib.parse.quote(f"FEATURE REQUEST: add support to {child.tag} tag")
            print(f"""
{SHELL_COLOR_WARNING} ATENTION ⚠️ {SHELL_NO_COLOR}: The following tag {child.tag} is not supported.
If you feel it should be supported, please open a issue.
https://github.com/rafaeltonholo/svg-to-compose/issues/new?title={title}.

Skipping current tag.
""")

    file_contents = template(
        package=package,
        icon_name=file.name.removesuffix(".svg").removesuffix(".xml"),
        context_provider=context_provider,
        add_to_material=add_to_material,
        theme=theme,
        width=width,
        height=height,
        viewport_width=viewport_width,
        viewport_height=viewport_height,
        has_group=has_group,
        paths="\n".join(paths),
    )

    return file_contents

def __normalize_path(path: str) -> str:
    if isdebug():
        print()
        print("========================= Normalizing path =========================")
        print(f"Original path value = {path}")
        print("====================================================================")
        print()

    parsed_path = ""
    last_char = ''
    for char in path:
        if (char.isalpha() and char.lower() != "z") or (last_char.isdigit() and char == "-"):
            parsed_path += f" {char}"
        else:
            parsed_path += char

        last_char = char

    if isdebug():
        print()
        print("======================= Finished Normalizing =======================")
        print(f"Normalized path value = {parsed_path}")
        print("====================================================================")
        print()

    return parsed_path
