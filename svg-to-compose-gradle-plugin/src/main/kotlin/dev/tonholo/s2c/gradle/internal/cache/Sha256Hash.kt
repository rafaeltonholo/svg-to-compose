package dev.tonholo.s2c.gradle.internal.cache

import okio.ByteString.Companion.toByteString
import java.io.Serializable

/**
 * Represents a SHA-256 hash value.
 * This class ensures type-safety when handling SHA-256 hashes and provides convenient methods
 * for creating hashes from strings and byte arrays.
 *
 * @property value The hexadecimal string representation of the SHA-256 hash
 */
@JvmInline
value class Sha256Hash private constructor(private val value: String) : CharSequence by value, Serializable {
    override fun toString(): String = value

    companion object {
        private const val serialVersionUID = 1L
        operator fun invoke(raw: String): Sha256Hash =
            Sha256Hash(raw.toByteArray())

        operator fun invoke(bytes: ByteArray): Sha256Hash =
            Sha256Hash(bytes.toByteString().sha256().hex())
    }
}

/**
 * Calculates the SHA-256 hash of the string.
 */
internal fun String.sha256(): Sha256Hash = Sha256Hash(raw = this)

/**
 * Calculates the SHA-256 hash of the byte array.
 */
internal fun ByteArray.sha256(): Sha256Hash = Sha256Hash(bytes = this)
