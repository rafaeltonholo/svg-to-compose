package dev.tonholo.s2c.extensions

import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FileSystemDeleteRecursivelyCompatTest {
    private val fs = FakeFileSystem()

    @AfterTest
    fun tearDown() {
        fs.checkNoOpenFiles()
    }

    @Test
    fun `given directory tree when deleteRecursivelyCompat then all files and dirs are removed`() {
        // Arrange
        val root = "/root".toPath()
        fs.createDirectories(root / "sub1" / "sub2")
        fs.write(root / "a.txt") { writeUtf8("a") }
        fs.write(root / "sub1" / "b.txt") { writeUtf8("b") }
        fs.write(root / "sub1" / "sub2" / "c.txt") { writeUtf8("c") }
        // Act
        fs.deleteRecursivelyCompat(root)
        // Assert
        assertTrue(!fs.exists(root))
    }

    @Test
    fun `given non-existent path with mustExist false when deleteRecursivelyCompat then no error`() {
        // Arrange
        val path = "/does-not-exist".toPath()
        // Act & Assert - should not throw
        fs.deleteRecursivelyCompat(path, mustExist = false)
    }

    @Test
    fun `given non-existent path with mustExist true when deleteRecursivelyCompat then throws`() {
        // Arrange
        val path = "/does-not-exist".toPath()
        // Act & Assert
        assertFailsWith<okio.IOException> {
            fs.deleteRecursivelyCompat(path, mustExist = true)
        }
    }

    @Test
    fun `given single file when deleteRecursivelyCompat then file is removed`() {
        // Arrange
        val file = "/single.txt".toPath()
        fs.write(file) { writeUtf8("content") }
        // Act
        fs.deleteRecursivelyCompat(file)
        // Assert
        assertTrue(!fs.exists(file))
    }

    @Test
    fun `given empty directory when deleteRecursivelyCompat then directory is removed`() {
        // Arrange
        val dir = "/empty-dir".toPath()
        fs.createDirectories(dir)
        // Act
        fs.deleteRecursivelyCompat(dir)
        // Assert
        assertTrue(!fs.exists(dir))
    }
}
