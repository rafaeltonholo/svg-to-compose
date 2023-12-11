from pathlib import Path
from shutil import copy
import xml.etree.ElementTree as ElementTree

from s2c import ERRORS, NOT_SUPPORTED_FILE_ERROR
from s2c.optimizer import optimizer
from s2c.parser.file_template import template
from s2c.parser.nodes.draw_node import ArcToDrawNode, CurveToDrawNode, DrawNode, HorizontalLineToDrawNode, LineToDrawNode, MoveToDrawNode, ReflectiveCurveToDrawNode, VerticalLineToDrawNode

SHELL_COLOR_WARNING="\033[43m"
SHELL_NO_COLOR = "\033[0m"

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
    
    width = root.attrib["width"]
    height = root.attrib["height"]
    
    viewbox = root.attrib["viewBox"].split()
    viewport_width=viewbox[-2]
    viewport_height=viewbox[-1]

    paths = []
    for child in root:
        if child.tag.endswith("path"):
            fill_color = child.get("fill")
            path = child.get("d")
            opacity = child.get("opacity")

            normalized_path = __normalize_path(path)

            print(f"// {normalized_path}")

            commands = normalized_path.split()
            last_command = ''
            nodes: list[DrawNode] = []
            while len(commands) > 0:
                current = commands[0]
                current_command = current[0]
                if (current_command.isdigit() and last_command != ""):
                    current_command = last_command
                    current = last_command + current
                
                if current.lower().startswith("m"):
                    nodes.append(MoveToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("a"):
                    nodes.append(ArcToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("v"):
                    nodes.append(VerticalLineToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("h"):
                    nodes.append(HorizontalLineToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("l"):
                    nodes.append(LineToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("c"):
                    nodes.append(CurveToDrawNode(commands))
                    last_command = current_command
                elif current.lower().startswith("s"):
                    nodes.append(ReflectiveCurveToDrawNode(commands))
                    last_command = current_command


            plain_nodes = "".join(list(map(lambda node: node.materialize(), nodes)))
            color = "SolidColor(Color.Black), // No color defined. Using fallback color."
            if fill_color.startswith("#"):
                real_color = fill_color.removeprefix("#")
                if (len(real_color) == 6):
                    real_color = "FF" + real_color

                color = f"SolidColor(Color(0x{real_color})),"

            path_params_list = [
                f"fill = {color}",
            ]
            if opacity:
                path_params_list.append(f"fillAlpha = {float(opacity)}f,")

            path_params = "\n                ".join(path_params_list)
            paths.append(f"""
            path(
                {path_params}
            ) {{
                {plain_nodes}
            }}""")

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
        paths="\n".join(paths),
    )

    return file_contents

def __normalize_path(path: str) -> str:
    parsed_path = ""
    last_char = ''
    for char in path:
        if char.isalpha() and char.lower() != "z" or (last_char.isdigit() and char == "-"):
            parsed_path += f" {char}"
        else:
            parsed_path += char

        last_char = char

    return parsed_path
