package dev.tonholo.s2c.gradle.internal.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class Sha256HashTest {

    // Known SHA-256 of "hello" = 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
    private val helloHex = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"

    // -------------------------------------------------------------------------
    // invoke(String)
    // -------------------------------------------------------------------------

    @Test
    fun `invoke(String) produces the expected SHA-256 hex for a known value`() {
        val hash = Sha256Hash("hello")
        assertEquals(helloHex, hash.toString())
    }

    @Test
    fun `invoke(String) is deterministic for the same input`() {
        val hash1 = Sha256Hash("deterministic")
        val hash2 = Sha256Hash("deterministic")
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke(String) produces different hashes for different inputs`() {
        val hash1 = Sha256Hash("foo")
        val hash2 = Sha256Hash("bar")
        assertNotEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke(String) produces a 64-character hex string`() {
        val hash = Sha256Hash("any input")
        assertEquals(64, hash.toString().length)
        assertTrue(hash.toString().all { it in '0'..'9' || it in 'a'..'f' })
    }

    // -------------------------------------------------------------------------
    // invoke(ByteArray)
    // -------------------------------------------------------------------------

    @Test
    fun `invoke(ByteArray) produces the expected SHA-256 hex for a known value`() {
        val hash = Sha256Hash("hello".toByteArray())
        assertEquals(helloHex, hash.toString())
    }

    @Test
    fun `invoke(ByteArray) is deterministic`() {
        val bytes = "same".toByteArray()
        val hash1 = Sha256Hash(bytes)
        val hash2 = Sha256Hash(bytes)
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke(ByteArray) produces different hashes for different byte arrays`() {
        val hash1 = Sha256Hash("abc".toByteArray())
        val hash2 = Sha256Hash("xyz".toByteArray())
        assertNotEquals(hash1.toString(), hash2.toString())
    }

    // -------------------------------------------------------------------------
    // String and ByteArray produce the same result
    // -------------------------------------------------------------------------

    @Test
    fun `invoke(String) and invoke(ByteArray) give the same hash for equivalent input`() {
        val input = "equivalent"
        val hashFromString = Sha256Hash(input)
        val hashFromBytes = Sha256Hash(input.toByteArray())
        assertEquals(hashFromString.toString(), hashFromBytes.toString())
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Test
    fun `toString returns the hex string representation`() {
        val hash = Sha256Hash("hello")
        assertEquals(helloHex, hash.toString())
    }

    // -------------------------------------------------------------------------
    // CharSequence delegation
    // -------------------------------------------------------------------------

    @Test
    fun `length returns the number of hex characters (64)`() {
        val hash = Sha256Hash("test")
        assertEquals(64, hash.length)
    }

    @Test
    fun `get returns individual hex characters`() {
        val hash = Sha256Hash("hello")
        val expected = helloHex
        for (i in expected.indices) {
            assertEquals(expected[i], hash[i], "Character at index $i differs")
        }
    }

    @Test
    fun `subSequence returns a subsequence of the hex string`() {
        val hash = Sha256Hash("hello")
        val sub = hash.subSequence(0, 8)
        assertEquals(helloHex.substring(0, 8), sub.toString())
    }

    // -------------------------------------------------------------------------
    // Extension functions
    // -------------------------------------------------------------------------

    @Test
    fun `String sha256 extension produces same result as invoke(String)`() {
        val input = "extension test"
        val viaExtension = input.sha256()
        val viaInvoke = Sha256Hash(input)
        assertEquals(viaInvoke.toString(), viaExtension.toString())
    }

    @Test
    fun `ByteArray sha256 extension produces same result as invoke(ByteArray)`() {
        val bytes = "byte extension".toByteArray()
        val viaExtension = bytes.sha256()
        val viaInvoke = Sha256Hash(bytes)
        assertEquals(viaInvoke.toString(), viaExtension.toString())
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    fun `empty string produces a consistent SHA-256 hash`() {
        // SHA-256 of "" is e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
        val emptyHex = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        val hash = Sha256Hash("")
        assertEquals(emptyHex, hash.toString())
    }

    @Test
    fun `empty byte array produces same hash as empty string`() {
        val hashFromString = Sha256Hash("")
        val hashFromBytes = Sha256Hash(ByteArray(0))
        assertEquals(hashFromString.toString(), hashFromBytes.toString())
    }
}