package dev.tonholo.s2c.error

open class ExitProgramException(
    val errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
) : Exception(message, throwable)
