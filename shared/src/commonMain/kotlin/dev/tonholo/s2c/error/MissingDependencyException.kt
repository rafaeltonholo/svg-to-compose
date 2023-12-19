package dev.tonholo.s2c.error

class MissingDependencyException(
    errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
) : ExitProgramException(errorCode, message, throwable)