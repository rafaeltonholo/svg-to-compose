import subprocess

from pathlib import Path
from config import ERRORS, ROOT_PATH, SVGO_OPTIMIZATION_ERROR, SV2_OPTIMIZATION_ERROR, AVOCADO_OPTIMIZATION_ERROR, isdebug

# TODO: future improvement, consider: https://github.com/mathandy/svgpathtools

SVGO_CONFIG_FILE = f"{ROOT_PATH}/svgo-config.js"

def __write_svgo_config_file():
    svgo_config = Path(SVGO_CONFIG_FILE)
    if not svgo_config.exists():
        print("⚙️ writing svgo config file")
        with open(svgo_config, "w") as file:
            file.write(
                """
module.exports = {
  plugins: [
      {
          name: "convertPathData",
          params: {
              leadingZero: false,
              floatPrecision: 2,
          }
      }
  ]
}                
""".lstrip(),
            )

def __run_optimization(command: str, error_code: int):
    command_name = command[0:command.index(" ")]
    print()
    print(f"⏳ Running {command_name}")
    try:
        subprocess.run(
            command,
            shell=True, 
            capture_output=True, 
            check=True,
        )
    except subprocess.CalledProcessError as error:
        if isdebug():
            print(str(error))

        raise Exception(ERRORS[error_code])

    print()
    print(f"✅ Finished {command_name}")

def __optmize_svg():
    print("🏎️  Optimizing SVG")
    __write_svgo_config_file()

    __run_optimization(
        command=f"svgo {ROOT_PATH}/target.svg --config={SVGO_CONFIG_FILE} -o {ROOT_PATH}/target.optimized.svg",
        error_code=SVGO_OPTIMIZATION_ERROR,
    )
    
    __run_optimization(
        command=f"s2v -p 2 -i {ROOT_PATH}/target.optimized.svg -o {ROOT_PATH}/target.xml",
        error_code=SV2_OPTIMIZATION_ERROR,
    )

def optimize(file: Path):
    if isdebug():
        print()
        print("========================= Start optimization =========================")
        print()

    if file.name.endswith(".svg"):
        __optmize_svg()
    else:
        print("⚠️ XML deteced, skipping SVG optimization")
        print()
        print("🏎️  Optimizing XML")
    
    __run_optimization(
        command=f"avocado {ROOT_PATH}/target.xml",
        error_code=AVOCADO_OPTIMIZATION_ERROR,
    )

def delete_svgo_config():
    if isdebug():
        print()
        print("========================= Deleting SVGO config ========================")
        print()
    Path(SVGO_CONFIG_FILE).unlink(missing_ok = True)
    if isdebug():
        print("Deleted.")
        print()
        print("=======================================================================")
        print()