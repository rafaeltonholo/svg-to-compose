package dev.tonholo.s2c.error

class ParserException(
    errorCode: ErrorCode,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
