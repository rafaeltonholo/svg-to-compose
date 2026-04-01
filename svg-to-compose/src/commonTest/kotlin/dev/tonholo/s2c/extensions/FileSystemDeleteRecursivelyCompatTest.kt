package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.ForwardingFileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
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

    @Test
    fun `given directory tree when deleteRecursivelyCompat then deletes children before parents`() {
        // Arrange
        val root = "/ordering".toPath()
        fs.createDirectories(root / "a" / "b")
        fs.write(root / "a" / "b" / "file.txt") { writeUtf8("x") }
        fs.write(root / "a" / "sibling.txt") { writeUtf8("y") }
        val recorder = DeleteRecorderFileSystem(fs)
        // Act
        recorder.deleteRecursivelyCompat(root)
        // Assert - every parent must appear after all its children
        val deletions = recorder.deletedPaths
        for (i in deletions.indices) {
            for (j in i + 1 until deletions.size) {
                val earlier = deletions[i]
                val later = deletions[j]
                // If 'earlier' is a parent of 'later', the parent was
                // deleted before the child, which is wrong.
                assertFalse(
                    actual = later.toString().startsWith("$earlier/"),
                    message = "Parent $earlier was deleted at index $i before child $later at index $j",
                )
            }
        }
    }

    @Test
    fun `given directory tree when deleteRecursivelyCompat then does not hold all paths in memory at once`() {
        // Arrange
        // Build a wide, shallow tree: 500 files across 10 directories.
        val root = "/wide".toPath()
        for (d in 0 until 10) {
            val dir = root / "dir$d"
            fs.createDirectories(dir)
            for (f in 0 until 50) {
                fs.write(dir / "file$f.txt") { writeUtf8("$d-$f") }
            }
        }
        val recorder = DeleteRecorderFileSystem(fs)
        // Act
        recorder.deleteRecursivelyCompat(root)
        // Assert - all paths deleted and nothing remains
        assertTrue(!fs.exists(root))
        // Each file should have been deleted individually (500 files + 10 dirs + root = 511).
        val expectedDeletions = 500 + 10 + 1
        assertEquals(
            expected = recorder.deletedPaths.size,
            actual = expectedDeletions,
            message = "Expected $expectedDeletions deletions but got ${recorder.deletedPaths.size}"
        )
        // The peak live set during deletion should be bounded by tree depth,
        // not total node count. We verify this indirectly: if the
        // implementation materializes all paths first, listOrNull is called
        // for every node before any delete. With streaming deletion,
        // deletes are interleaved with listing.
        // Find the index of the first delete. If all lists happen before any
        // delete, the implementation is materializing everything upfront.
        val firstDeleteTimestamp = recorder.operationLog.indexOfFirst { it.first == "delete" }
        val lastListTimestamp = recorder.operationLog.indexOfLast { it.first == "listOrNull" }
        assertTrue(
            firstDeleteTimestamp < lastListTimestamp,
            "All listOrNull calls completed before any delete, " +
                "indicating full materialization. " +
                "First delete at index $firstDeleteTimestamp, " +
                "last listOrNull at index $lastListTimestamp.",
        )
    }
}

/**
 * A [ForwardingFileSystem] that records [delete] and [listOrNull] calls
 * in order, allowing tests to verify deletion ordering and whether
 * the implementation streams or materializes the full tree.
 */
private class DeleteRecorderFileSystem(delegate: FileSystem) : ForwardingFileSystem(delegate) {
    private val _deletedPaths = mutableListOf<Path>()
    val deletedPaths: List<Path> get() = _deletedPaths

    private val _listOrNullCalls = mutableListOf<Path>()
    val listOrNullCalls: List<Path> get() = _listOrNullCalls

    /** Pairs of ("delete"|"listOrNull", path) in call order. */
    private val _operationLog = mutableListOf<Pair<String, Path>>()
    val operationLog: List<Pair<String, Path>> get() = _operationLog

    override fun delete(path: Path, mustExist: Boolean) {
        _deletedPaths.add(path)
        _operationLog.add("delete" to path)
        super.delete(path, mustExist)
    }

    override fun listOrNull(dir: Path): List<Path>? {
        _listOrNullCalls.add(dir)
        _operationLog.add("listOrNull" to dir)
        return super.listOrNull(dir)
    }
}
