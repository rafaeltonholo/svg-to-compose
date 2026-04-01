package dev.tonholo.s2c.extensions

import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FileSystemListRecursivelyTest {
    private val fs = FakeFileSystem()

    @AfterTest
    fun tearDown() {
        fs.checkNoOpenFiles()
    }

    private fun createTree() {
        // root/
        //   a.svg
        //   sub1/
        //     b.svg
        //     sub2/
        //       c.svg
        val root = "/root".toPath()
        fs.createDirectories(root / "sub1" / "sub2")
        fs.write(root / "a.svg") { writeUtf8("a") }
        fs.write(root / "sub1" / "b.svg") { writeUtf8("b") }
        fs.write(root / "sub1" / "sub2" / "c.svg") { writeUtf8("c") }
    }

    @Test
    fun `given directory tree when listing with no maxDepth then returns all paths`() {
        // Arrange
        createTree()
        val root = "/root".toPath()
        // Act
        val result = fs.listRecursively(root).map { it.name }.toSet()
        // Assert
        assertEquals(setOf("a.svg", "sub1", "b.svg", "sub2", "c.svg"), result)
    }

    @Test
    fun `given directory tree when listing with maxDepth 0 then returns only direct children`() {
        // Arrange
        createTree()
        val root = "/root".toPath()
        // Act
        val result = fs.listRecursively(root, maxDepth = 0).map { it.name }.toSet()
        // Assert
        assertEquals(setOf("a.svg", "sub1"), result)
    }

    @Test
    fun `given directory tree when listing with maxDepth 1 then returns up to depth 1`() {
        // Arrange
        createTree()
        val root = "/root".toPath()
        // Act
        val result = fs.listRecursively(root, maxDepth = 1).map { it.name }.toSet()
        // Assert
        assertEquals(setOf("a.svg", "sub1", "b.svg", "sub2"), result)
    }

    @Test
    fun `given empty directory when listing then returns empty`() {
        // Arrange
        val root = "/empty".toPath()
        fs.createDirectories(root)
        // Act
        val result = fs.listRecursively(root).toList()
        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `given non-existent directory when listing then throws on iteration`() {
        // Arrange
        val root = "/does-not-exist".toPath()
        // Act & Assert
        assertFailsWith<okio.IOException> {
            fs.listRecursively(root).toList()
        }
    }

    @Test
    fun `given deep directory tree when listing with no maxDepth then returns all levels`() {
        // Arrange
        // root/
        //   d1/
        //     d2/
        //       d3/
        //         d4/
        //           deep.svg
        val root = "/deep".toPath()
        fs.createDirectories(root / "d1" / "d2" / "d3" / "d4")
        fs.write(root / "d1" / "d2" / "d3" / "d4" / "deep.svg") { writeUtf8("deep") }
        // Act
        val result = fs.listRecursively(root).map { it.name }.toList()
        // Assert
        assertEquals(listOf("d1", "d2", "d3", "d4", "deep.svg"), result)
    }
}
