package dev.tonholo.s2c.error

class OptimizationException(
    val errorCode: ErrorCode,
    message: String = errorCode.name,
    throwable: Throwable? = null,
) : Exception(message, throwable)
