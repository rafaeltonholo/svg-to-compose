package dev.tonholo.s2c.gradle.internal.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class CacheDataTest {

    private val sampleHash = Sha256Hash("sample-content")

    // -------------------------------------------------------------------------
    // IconCacheData
    // -------------------------------------------------------------------------

    @Test
    fun `IconCacheData output defaults to null`() {
        val data = IconCacheData(origin = "/icons/home.svg", hash = sampleHash)
        assertNull(data.output)
    }

    @Test
    fun `IconCacheData with explicit output`() {
        val data = IconCacheData(
            origin = "/icons/home.svg",
            hash = sampleHash,
            output = "/generated/Home.kt",
        )
        assertEquals("/generated/Home.kt", data.output)
    }

    @Test
    fun `IconCacheData equality based on all fields`() {
        val data1 = IconCacheData(origin = "/a.svg", hash = sampleHash, output = "/A.kt")
        val data2 = IconCacheData(origin = "/a.svg", hash = sampleHash, output = "/A.kt")
        assertEquals(data1, data2)
    }

    @Test
    fun `IconCacheData inequality when origin differs`() {
        val data1 = IconCacheData(origin = "/a.svg", hash = sampleHash)
        val data2 = IconCacheData(origin = "/b.svg", hash = sampleHash)
        assertNotEquals(data1, data2)
    }

    @Test
    fun `IconCacheData inequality when hash differs`() {
        val data1 = IconCacheData(origin = "/a.svg", hash = Sha256Hash("content-a"))
        val data2 = IconCacheData(origin = "/a.svg", hash = Sha256Hash("content-b"))
        assertNotEquals(data1, data2)
    }

    @Test
    fun `IconCacheData inequality when output differs`() {
        val data1 = IconCacheData(origin = "/a.svg", hash = sampleHash, output = "/A.kt")
        val data2 = IconCacheData(origin = "/a.svg", hash = sampleHash, output = null)
        assertNotEquals(data1, data2)
    }

    @Test
    fun `IconCacheData copy preserves unchanged fields`() {
        val original = IconCacheData(origin = "/a.svg", hash = sampleHash, output = "/A.kt")
        val copied = original.copy(output = "/B.kt")
        assertEquals("/a.svg", copied.origin)
        assertEquals(sampleHash.toString(), copied.hash.toString())
        assertEquals("/B.kt", copied.output)
    }

    // -------------------------------------------------------------------------
    // CacheData
    // -------------------------------------------------------------------------

    @Test
    fun `CacheData with empty files and empty extension configuration`() {
        val cacheData = CacheData(
            files = emptyList(),
            extensionConfiguration = emptyMap(),
        )
        assertEquals(0, cacheData.files.size)
        assertEquals(0, cacheData.extensionConfiguration.size)
    }

    @Test
    fun `CacheData holds provided files and configuration`() {
        val iconData = IconCacheData(origin = "/icons/star.svg", hash = sampleHash)
        val configHash = Sha256Hash("config-content")
        val cacheData = CacheData(
            files = listOf(iconData),
            extensionConfiguration = mapOf("svg" to configHash),
        )
        assertEquals(1, cacheData.files.size)
        assertEquals(iconData, cacheData.files[0])
        assertEquals(configHash.toString(), cacheData.extensionConfiguration["svg"].toString())
    }

    @Test
    fun `CacheData equality based on files and extensionConfiguration`() {
        val iconData = IconCacheData(origin = "/a.svg", hash = sampleHash)
        val configHash = Sha256Hash("cfg")
        val c1 = CacheData(
            files = listOf(iconData),
            extensionConfiguration = mapOf("svg" to configHash),
        )
        val c2 = CacheData(
            files = listOf(iconData),
            extensionConfiguration = mapOf("svg" to configHash),
        )
        assertEquals(c1, c2)
    }

    @Test
    fun `CacheData inequality when files differ`() {
        val d1 = IconCacheData(origin = "/a.svg", hash = sampleHash)
        val d2 = IconCacheData(origin = "/b.svg", hash = sampleHash)
        val c1 = CacheData(files = listOf(d1), extensionConfiguration = emptyMap())
        val c2 = CacheData(files = listOf(d2), extensionConfiguration = emptyMap())
        assertNotEquals(c1, c2)
    }

    @Test
    fun `CacheData copy works correctly`() {
        val original = CacheData(files = emptyList(), extensionConfiguration = emptyMap())
        val newIcon = IconCacheData(origin = "/new.svg", hash = sampleHash)
        val copied = original.copy(files = listOf(newIcon))
        assertEquals(1, copied.files.size)
        assertEquals("/new.svg", copied.files[0].origin)
        assertEquals(0, copied.extensionConfiguration.size)
    }

    @Test
    fun `CacheData with multiple files preserves order`() {
        val icons = listOf(
            IconCacheData(origin = "/a.svg", hash = Sha256Hash("a")),
            IconCacheData(origin = "/b.svg", hash = Sha256Hash("b")),
            IconCacheData(origin = "/c.svg", hash = Sha256Hash("c")),
        )
        val cacheData = CacheData(files = icons, extensionConfiguration = emptyMap())
        assertEquals(3, cacheData.files.size)
        assertEquals("/a.svg", cacheData.files[0].origin)
        assertEquals("/b.svg", cacheData.files[1].origin)
        assertEquals("/c.svg", cacheData.files[2].origin)
    }
}