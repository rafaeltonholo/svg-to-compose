import subprocess

from pathlib import Path
from s2c import ERRORS, SVGO_OPTIMIZATION_ERROR, SV2_OPTIMIZATION_ERROR, AVOCADO_OPTIMIZATION_ERROR, isdebug

# TODO: future improvement, consider: https://github.com/mathandy/svgpathtools

def __write_svgo_config_file():
    svgo_config = Path("svgo-config.js")
    if not svgo_config.exists():
        print("⚙️ writing svgo config file")
        with open(svgo_config.name, "w") as file:
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
    except subprocess.CalledProcessError:
        raise Exception(ERRORS[error_code])

    print()
    print(f"✅ Finished {command_name}")

def __optmize_svg():
    print("🏎️  Optimizing SVG")
    __write_svgo_config_file()

    __run_optimization(
        command="svgo target.svg --config=svgo-config.js -o target.optimized.svg",
        error_code=SVGO_OPTIMIZATION_ERROR,
    )
    
    __run_optimization(
        command="s2v -p 2 -i target.optimized.svg -o target.xml",
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
        command="avocado target.xml",
        error_code=AVOCADO_OPTIMIZATION_ERROR,
    )