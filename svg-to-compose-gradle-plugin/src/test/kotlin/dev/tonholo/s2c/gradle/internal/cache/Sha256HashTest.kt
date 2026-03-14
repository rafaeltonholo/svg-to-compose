package dev.tonholo.s2c.gradle.internal.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class Sha256HashTest {

    // -------------------------------------------------------------------------
    // Construction from String
    // -------------------------------------------------------------------------

    @Test
    fun `invoke(String) produces a 64-character hex string`() {
        val hash = Sha256Hash("hello")
        assertEquals(64, hash.length)
    }

    @Test
    fun `invoke(String) is deterministic for the same input`() {
        val hash1 = Sha256Hash("test-input")
        val hash2 = Sha256Hash("test-input")
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke(String) produces different hashes for different inputs`() {
        val hash1 = Sha256Hash("foo")
        val hash2 = Sha256Hash("bar")
        assertNotEquals(hash1.toString(), hash2.toString())
    }

    // -------------------------------------------------------------------------
    // Construction from ByteArray
    // -------------------------------------------------------------------------

    @Test
    fun `invoke(ByteArray) produces a 64-character hex string`() {
        val hash = Sha256Hash("hello".toByteArray())
        assertEquals(64, hash.length)
    }

    @Test
    fun `invoke(ByteArray) is deterministic for the same bytes`() {
        val bytes = "deterministic".toByteArray()
        val hash1 = Sha256Hash(bytes)
        val hash2 = Sha256Hash(bytes)
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke(String) and invoke(ByteArray) agree for the same content`() {
        val content = "same-content"
        val hashFromString = Sha256Hash(content)
        val hashFromBytes = Sha256Hash(content.toByteArray())
        assertEquals(hashFromString.toString(), hashFromBytes.toString())
    }

    // -------------------------------------------------------------------------
    // toString()
    // -------------------------------------------------------------------------

    @Test
    fun `toString returns the hex representation`() {
        val hash = Sha256Hash("hello")
        // Known SHA-256 of "hello" in hex:
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", hash.toString())
    }

    @Test
    fun `toString result contains only lowercase hex digits`() {
        val hash = Sha256Hash("test-hex-chars")
        assertTrue(hash.toString().matches(Regex("[0-9a-f]+")))
    }

    // -------------------------------------------------------------------------
    // CharSequence delegation
    // -------------------------------------------------------------------------

    @Test
    fun `length matches the hex string length`() {
        val hash = Sha256Hash("any input")
        assertEquals(64, hash.length)
    }

    @Test
    fun `get returns the correct character at index`() {
        val hash = Sha256Hash("hello")
        val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expected[0], hash[0])
        assertEquals(expected[3], hash[3])
    }

    @Test
    fun `subSequence returns the correct subsequence`() {
        val hash = Sha256Hash("hello")
        val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expected.subSequence(0, 8), hash.subSequence(0, 8))
    }

    // -------------------------------------------------------------------------
    // Extension functions
    // -------------------------------------------------------------------------

    @Test
    fun `String sha256 extension agrees with Sha256Hash invoke`() {
        val input = "extension-test"
        val viaExtension = input.sha256()
        val viaInvoke = Sha256Hash(input)
        assertEquals(viaInvoke.toString(), viaExtension.toString())
    }

    @Test
    fun `ByteArray sha256 extension agrees with Sha256Hash invoke`() {
        val bytes = "byte-extension".toByteArray()
        val viaExtension = bytes.sha256()
        val viaInvoke = Sha256Hash(bytes)
        assertEquals(viaInvoke.toString(), viaExtension.toString())
    }

    @Test
    fun `empty string produces a known SHA-256 hash`() {
        val hash = Sha256Hash("")
        // SHA-256 of empty string
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", hash.toString())
    }
}