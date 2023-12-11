import os

from pathlib import Path

from s2c.utils.string_utils import pascal_case

def write(
    icon_name: str,
    file_contents: str,
    output: Path,
):
    print(f"📝 Writing icon file on {output}")

    target_file = Path(output)
    if (not output.name.endswith(".kt") and not output.exists()):
        print("📢 Output directory is missing. Creating it automatically.")
        os.mkdir(output)

    if (output.is_dir()):
        target_file = target_file / f"{pascal_case(icon_name)}.kt"

    with open(target_file, "w") as file:
        file.write(file_contents.lstrip())

    print("✅ Done writing the file")