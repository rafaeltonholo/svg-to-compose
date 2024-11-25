package dev.tonholo.s2c.error

enum class ErrorCode(val code: Int) {
    FileNotFoundError(code = 1),
    SvgoOptimizationError(code = 2),
    AvocadoOptimizationError(code = 4),
    NotSupportedFileError(code = 8),
    OutputNotDirectoryError(code = 9),
    ParseSvgError(code = 10),
    MissingCoreDependency(code = 1000),
    FailedToParseIconError(code = 2000),
    GradlePluginError(code = 3000),
}
