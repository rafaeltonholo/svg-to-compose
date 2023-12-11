from pathlib import Path

def write(
    icon_name: str,
    file_contents: str,
    output: Path,
):
    print("📝 Writing icon file")

    with open("temp.kt", "w") as file:
        file.write(file_contents.lstrip())

    print("✅ Done writing the file")