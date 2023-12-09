from pathlib import Path
from shutil import copy

from s2c import ERRORS, NOT_SUPPORTED_FILE_ERROR
from s2c.optimizer import optimizer

def parse(
    file: Path,
    optimize: bool,
    package: str,
    theme: str,
    output: Path,
    context_provider: str,
    add_to_material: bool,
):
    extension = file.suffix
    print(f"👓 Parsing the {extension} file")

    if extension != ".svg" and extension != ".xml":
        print(ERRORS[NOT_SUPPORTED_FILE_ERROR])
        exit(NOT_SUPPORTED_FILE_ERROR)

    # Copy file to work it locally
    copy(file, f"target{extension}")

    optimizer.optimize(file)

