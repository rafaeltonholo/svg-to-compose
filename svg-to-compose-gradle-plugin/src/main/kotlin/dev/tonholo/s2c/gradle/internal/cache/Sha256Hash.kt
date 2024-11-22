package dev.tonholo.s2c.gradle.internal.cache

import okio.ByteString.Companion.toByteString
import java.io.Serializable

@JvmInline
value class Sha256Hash private constructor(private val value: String): CharSequence by value, Serializable {
    override fun toString(): String = value

    companion object {
        private const val serialVersionUID = 1L
        operator fun invoke(raw: String): Sha256Hash =
            Sha256Hash(raw.toByteArray())

        operator fun invoke(bytes: ByteArray): Sha256Hash =
            Sha256Hash(bytes.toByteString().sha256().hex())
    }
}

internal fun String.sha256(): Sha256Hash = Sha256Hash(raw = this)
internal fun ByteArray.sha256(): Sha256Hash = Sha256Hash(bytes = this)
