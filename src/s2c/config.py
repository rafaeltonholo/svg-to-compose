"""Top-level package for Svg to Compose."""
# src/__init__.py

__app_name__ = "s2c"
__version__ = "1.0.0-alpha01"

(
    FILE_NOT_FOUND_ERROR,
    SVGO_OPTIMIZATION_ERROR,
    SV2_OPTIMIZATION_ERROR,
    AVOCADO_OPTIMIZATION_ERROR,
    MISSING_PACKAGE_ERROR,
    MISSING_THEME_ERROR,
    MISSING_OUTPUT_ERROR,
    NOT_SUPPORTED_FILE_ERROR,
    OUTPUT_NOT_DIRECTORY_ERROR,
) = range(9)

MISSING_CORE_DEPENDENCY_ERROR = 1000

ERRORS = {
    FILE_NOT_FOUND_ERROR: "the file MUST be specified.",
    SVGO_OPTIMIZATION_ERROR: "Error running svgo optimization",
    SV2_OPTIMIZATION_ERROR: "Error running s2v optimization",
    AVOCADO_OPTIMIZATION_ERROR: "Error running avocado optimization",
    MISSING_PACKAGE_ERROR: "the parameter --package or -p should be specified",
    MISSING_THEME_ERROR: "the parameter --theme or -t should be specified",
    MISSING_OUTPUT_ERROR: "the parameter --output or -o should be specified",
    NOT_SUPPORTED_FILE_ERROR: "the provided file is not supported. Expeceted *.svg or *.xml",
    OUTPUT_NOT_DIRECTORY_ERROR: """
    when the input is a directory, the output MUST be directory too.
    If you pointed to a directory path, make sure the output directory
    already exists.
    """,
    # MISSING_CORE_DEPENDENCY_ERROR: "xpath is required.",
}

__DEBUG__ = False

def isdebug():
    return __DEBUG__

def set_debug(isDebug: bool):
    global __DEBUG__
    __DEBUG__ = isDebug
