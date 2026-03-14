package dev.tonholo.s2c.gradle.internal.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class Sha256HashTest {

    @Test
    fun `invoke with same string produces same hash`() {
        val hash1 = Sha256Hash("hello world")
        val hash2 = Sha256Hash("hello world")
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke with string and equivalent bytes produces same hash`() {
        val input = "test input"
        val hashFromString = Sha256Hash(input)
        val hashFromBytes = Sha256Hash(input.toByteArray())
        assertEquals(hashFromString.toString(), hashFromBytes.toString())
    }

    @Test
    fun `invoke with different strings produces different hashes`() {
        val hash1 = Sha256Hash("input one")
        val hash2 = Sha256Hash("input two")
        assertNotEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `SHA-256 hash is always 64 hex characters`() {
        val hash = Sha256Hash("any string content")
        assertEquals(64, hash.toString().length)
    }

    @Test
    fun `toString returns the hex hash string`() {
        val input = "deterministic"
        val hash = Sha256Hash(input)
        val result = hash.toString()
        assertTrue(result.all { it.isDigit() || it in 'a'..'f' }, "Hash should be lowercase hex: $result")
    }

    @Test
    fun `CharSequence length delegates to underlying hash string`() {
        val hash = Sha256Hash("test")
        assertEquals(64, hash.length)
    }

    @Test
    fun `CharSequence get delegates to underlying hash string`() {
        val input = "known value"
        val hash = Sha256Hash(input)
        val asString = hash.toString()
        assertEquals(asString[0], hash[0])
        assertEquals(asString[63], hash[63])
    }

    @Test
    fun `CharSequence subSequence delegates to underlying hash string`() {
        val hash = Sha256Hash("subsequence test")
        val asString = hash.toString()
        assertEquals(asString.subSequence(0, 8), hash.subSequence(0, 8))
    }

    @Test
    fun `invoke with empty string produces a consistent hash`() {
        val hash1 = Sha256Hash("")
        val hash2 = Sha256Hash("")
        assertEquals(hash1.toString(), hash2.toString())
        assertEquals(64, hash1.toString().length)
    }

    @Test
    fun `invoke with empty byte array produces same hash as empty string`() {
        val hashFromString = Sha256Hash("")
        val hashFromBytes = Sha256Hash(ByteArray(0))
        assertEquals(hashFromString.toString(), hashFromBytes.toString())
    }

    @Test
    fun `sha256 extension on String produces same result as direct invoke`() {
        val input = "extension function test"
        val directHash = Sha256Hash(input)
        val extensionHash = input.sha256()
        assertEquals(directHash.toString(), extensionHash.toString())
    }

    @Test
    fun `sha256 extension on ByteArray produces same result as direct invoke`() {
        val bytes = "byte array test".toByteArray()
        val directHash = Sha256Hash(bytes)
        val extensionHash = bytes.sha256()
        assertEquals(directHash.toString(), extensionHash.toString())
    }

    @Test
    fun `hash output is lowercase hexadecimal`() {
        val hash = Sha256Hash("uppercase check")
        val result = hash.toString()
        assertEquals(result, result.lowercase())
    }
}