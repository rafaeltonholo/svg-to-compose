package dev.tonholo.s2c.error

class ParserException(
    val errorCode: ErrorCode,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
