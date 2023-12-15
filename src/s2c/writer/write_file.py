import os

from pathlib import Path
from config import ROOT_PATH, isdebug

from utils.string_utils import pascal_case

def write(
    icon_name: str,
    file_contents: str,
    output: Path,
):
    print(f"📝 Writing icon file on {output}")

    target_file = Path(output)

    if isdebug():
        print()
        print("========================== Writing document ==========================")
        print()

    if (not output.name.endswith(".kt") and not output.exists()):
        print("📢 Output directory is missing. Creating it automatically.")
        os.mkdir(output)

    if (output.is_dir()):
        filename = f"{pascal_case(icon_name)}.kt"
        target_file = target_file / filename
        if isdebug():
            print(f"Output is directory. Appending icon name to path. Target output = {target_file}")

    if isdebug():
        print("Writing...")
        print()

    with open(target_file, "w") as file:
        file.write(file_contents.lstrip())

    print("✅ Done writing the file")
    if isdebug():
        print()
        print("========================== Deleting temp files =======================")
        print()

    temp_svg = Path(f"{ROOT_PATH}/target.svg")
    if isdebug():
        print("Deleting target.svg")
    temp_svg.unlink(missing_ok = True)
    if isdebug():
        print("Deleted target.svg")

    temp_optimized_svg = Path(f"{ROOT_PATH}/target.optimized.svg")
    if isdebug():
        print("Deleting target.optimized.svg")
    temp_optimized_svg.unlink(missing_ok = True)
    if isdebug():
        print("Deleted target.optimized.svg")

    temp_xml = Path(f"{ROOT_PATH}/target.xml")
    if isdebug():
        print("Deleting target.xml")
    temp_xml.unlink(missing_ok = True)
    if isdebug():
        print("Deleted target.xml")

    if isdebug():
        print()
        print("======================================================================")
        print()

    if isdebug():
        print()
        print("========================== Finished writing ==========================")
        print()