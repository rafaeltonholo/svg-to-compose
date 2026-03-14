package dev.tonholo.s2c.gradle.internal.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class Sha256HashTest {

    // -------------------------------------------------------------------------
    // Known reference hashes
    // -------------------------------------------------------------------------

    // SHA-256("hello") = 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
    private val helloSha256 = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"

    // SHA-256("") = e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
    private val emptySha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

    // -------------------------------------------------------------------------
    // invoke(String)
    // -------------------------------------------------------------------------

    @Test
    fun `invoke with a known string produces the correct SHA-256 hex digest`() {
        val hash = Sha256Hash("hello")
        assertEquals(helloSha256, hash.toString())
    }

    @Test
    fun `invoke with empty string produces the SHA-256 of zero bytes`() {
        val hash = Sha256Hash("")
        assertEquals(emptySha256, hash.toString())
    }

    @Test
    fun `invoke with the same string twice returns equal hashes`() {
        val hash1 = Sha256Hash("consistent-input")
        val hash2 = Sha256Hash("consistent-input")
        assertEquals(hash1.toString(), hash2.toString())
    }

    @Test
    fun `invoke with different strings produces different hashes`() {
        val hash1 = Sha256Hash("hello")
        val hash2 = Sha256Hash("world")
        assertNotEquals(hash1.toString(), hash2.toString())
    }

    // -------------------------------------------------------------------------
    // invoke(ByteArray)
    // -------------------------------------------------------------------------

    @Test
    fun `invoke with a byte array produces the same hash as the equivalent string`() {
        val fromString = Sha256Hash("hello")
        val fromBytes = Sha256Hash("hello".toByteArray())
        assertEquals(fromString.toString(), fromBytes.toString())
    }

    @Test
    fun `invoke with empty byte array produces the SHA-256 of zero bytes`() {
        val hash = Sha256Hash(ByteArray(0))
        assertEquals(emptySha256, hash.toString())
    }

    @Test
    fun `invoke with raw bytes produces the correct SHA-256 hex digest`() {
        val hash = Sha256Hash("hello".toByteArray())
        assertEquals(helloSha256, hash.toString())
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Test
    fun `toString returns the hexadecimal string representation`() {
        val hash = Sha256Hash("hello")
        // SHA-256 output is always 64 hex characters long
        assertEquals(64, hash.toString().length)
        assertTrue(hash.toString().all { it in '0'..'9' || it in 'a'..'f' })
    }

    // -------------------------------------------------------------------------
    // CharSequence delegation
    // -------------------------------------------------------------------------

    @Test
    fun `length delegates to the underlying hex string`() {
        val hash = Sha256Hash("any-input")
        assertEquals(64, hash.length)
    }

    @Test
    fun `get delegates to the underlying hex string`() {
        val hash = Sha256Hash("hello")
        // First char of helloSha256 is '2'
        assertEquals('2', hash[0])
    }

    @Test
    fun `subSequence delegates to the underlying hex string`() {
        val hash = Sha256Hash("hello")
        val sub = hash.subSequence(0, 8).toString()
        assertEquals(helloSha256.substring(0, 8), sub)
    }

    // -------------------------------------------------------------------------
    // Boundary / negative cases
    // -------------------------------------------------------------------------

    @Test
    fun `hash output is always lowercase hex`() {
        // Ensures no uppercase characters slip through
        val hash = Sha256Hash("MixedCase")
        val hex = hash.toString()
        assertEquals(hex, hex.lowercase())
    }

    @Test
    fun `hashes for byte-equivalent inputs are identical regardless of construction path`() {
        val input = "regression-check"
        val viaString = Sha256Hash(input)
        val viaBytes = Sha256Hash(input.toByteArray(Charsets.UTF_8))
        assertEquals(viaString.toString(), viaBytes.toString())
    }
}