package dev.tonholo.s2c.remote.http

internal object HttpTimeouts {
    const val CONNECT_TIMEOUT_MS = 10_000L
    const val SOCKET_TIMEOUT_MS = 30_000L
}
