import argparse
import subprocess

from pathlib import Path

from config import __version__, MISSING_CORE_DEPENDENCY_ERROR, OUTPUT_NOT_DIRECTORY_ERROR, set_debug
from parser import parser
from writer import write_file

def build_args():
    parser = argparse.ArgumentParser(
        prog="s2c",
        usage="s2c [arguments] [file *.svg | *.xml | directory]",
        description="Convert specific .svg or an Android Vector Drawable (.xml) file to Jetpack Compose icon file",
    )

    parser.add_argument(
        "path",
        help="file *.svg | *.xml | directory"
    )
    parser.add_argument(
        "-p", 
        "--package", 
        required=True,
        help="Specify icons's package. This will replace package at the top of the icon file",
    )
    parser.add_argument(
        "-t", 
        "--theme", 
        required=True,
        help="Specify project's theme name. This will take place in the Icon Preview composable function and in the ImageVector Builder's names.",
    )
    parser.add_argument(
        "-o", 
        "--output", 
        required=True,
        help="output filename; if no .kt extension specified, it will be automatically added. In case of the input is a directory, output MUST also be a directory.",
    )
    parser.add_argument(
        "-opt", 
        "--optimize",
        choices=["true", "false"],
        help="Enable svg optimization before parsing to Jetpack Compose icon. The optimization process uses the following programs: svgo, svg2vectordrawable, avocado from NPM Registry",
        default="true",
    )
    parser.add_argument(
        "-v", 
        "--version",
        action="version",
        help="Show this script version",
        version=f"SVG to Compose version: {__version__}",
    )
    parser.add_argument(
        "-cp", 
        "--context-provider",
        help="""Adds a custom context provider to the Icon definition. 
        E.g.: s2c <args> -o MyIcon.kt -cp Icons.Filled my-icon.svg will creates the Compose Icon: 
        val Icons.Filled.MyIcon: ImageVector.""",
    )
    parser.add_argument(
        "--add-to-material", 
        action="store_true",
        help="Add the icon to the Material Icons context provider.",
    )
    parser.add_argument(
        "--debug",
        action="store_true",
        help="enable debug log",
    )

    args = parser.parse_args()

    # print(args)
    return args

def verify_optimization_dependencies():
    try:
        subprocess.run("command -v svgo > /dev/null", shell=True, capture_output=True, check=True)
    except subprocess.CalledProcessError:
        print("svgo is required. Use npm -g install svgo to install.")
        exit(MISSING_CORE_DEPENDENCY_ERROR)
    
    try:
        subprocess.run("command -v s2v > /dev/null", shell=True, capture_output=True, check=True)
    except subprocess.CalledProcessError:
        print("s2v is required. Use npm -g install svgo to install.")
        exit(MISSING_CORE_DEPENDENCY_ERROR)
    
    try:
        subprocess.run("command -v avocado > /dev/null", shell=True, capture_output=True, check=True)
    except subprocess.CalledProcessError:
        print("avocado is required. Use npm -g install svgo to install.")
        exit(MISSING_CORE_DEPENDENCY_ERROR)

def process_file(
    file: Path,
    optimize: bool,
    package: str,
    theme: str,
    output: Path,
    context_provider: str,
    add_to_material: bool,
):
    print(f"⏳ Processing {file.name}")
    file_contents = parser.parse(
        file=file,
        optimize=optimize,
        package=package,
        theme=theme,
        context_provider=context_provider,
        add_to_material=add_to_material,
    )
    icon_name = file.name.removesuffix(".svg").removesuffix(".xml")
    write_file.write(
        icon_name=icon_name,
        file_contents=file_contents,
        output=output,
    )

def app():
    args = build_args()
    set_debug(args.debug)

    optimize = True if args.optimize == "true" else False

    if (optimize):
        verify_optimization_dependencies()

    path = Path(args.path)
    output = Path(args.output)
    
    files = []
    if path.is_dir():
        print("🔍 Directory detected")
        if (not output.is_dir()):
            print()
            print("❌ ERROR: when the input is a directory, the output MUST be directory too.\nIf you pointed to a directory path, make sure the output directory already exists.")
            exit(OUTPUT_NOT_DIRECTORY_ERROR)

        for entry in path.iterdir():
            if entry.name.endswith(".svg") or entry.name.endswith(".xml"):
                files.append(entry)
    else:
        print("🔍 File detected")
        files.append(path)
    
    errors = []
    for file in files:
        try:
            process_file(
                file, 
                optimize,
                package=args.package,
                theme=args.theme,
                output=output,
                context_provider=args.context_provider,
                add_to_material=args.add_to_material,
            )
            print()
        except Exception as e:
            errors.append(f"Failed to parse {file} to Jetpack Compose Icon. Error message: {str(e)}")
    
    if len(errors) == 0:
        print("🎉 SVG/Android Vector Drawable parsed to Jetpack Compose icon with success 🎉")
    else:
        print("❌ Failure to parse SVG/Android Vector Drawable to Jetpack Compose.")
        print("Please see the logs for more information.")
        print()
        for error in errors:
            print(error)    