package dev.tonholo.s2c.emitter.editorconfig

import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.IndentStyle
import dev.tonholo.s2c.io.FileManager
import okio.BufferedSink
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals

class EditorConfigReaderTest {
    @Test
    fun `resolve returns defaults when no editorconfig found`() {
        val fm = fakeFileManager(files = emptyMap())
        val reader = DefaultEditorConfigReader(fm)
        val defaults = FormatConfig(indentSize = 7)
        val result = reader.resolve("/project/src".toPath(), defaults)
        assertEquals(defaults, result)
    }

    @Test
    fun `resolve reads editorconfig from output directory`() {
        val fm = fakeFileManager(
            files = mapOf(
                "/project/.editorconfig" to """
                    root = true
                    [*.kt]
                    indent_size = 2
                    indent_style = space
                """.trimIndent(),
            ),
            directories = setOf("/project/src", "/project"),
        )
        val reader = DefaultEditorConfigReader(fm)
        val result = reader.resolve("/project/src".toPath(), defaults = FormatConfig())
        assertEquals(2, result.indentSize)
        assertEquals(IndentStyle.SPACE, result.indentStyle)
    }

    @Test
    fun `resolve walks up and merges parent configs`() {
        val fm = fakeFileManager(
            files = mapOf(
                "/project/.editorconfig" to """
                    root = true
                    [*.kt]
                    indent_size = 2
                    max_line_length = 80
                """.trimIndent(),
                "/project/module/.editorconfig" to """
                    [*.kt]
                    indent_size = 4
                """.trimIndent(),
            ),
            directories = setOf("/project/module/src", "/project/module", "/project"),
        )
        val reader = DefaultEditorConfigReader(fm)
        val result = reader.resolve("/project/module/src".toPath(), defaults = FormatConfig())
        // child overrides parent
        assertEquals(4, result.indentSize)
        // parent value preserved
        assertEquals(80, result.maxLineLength)
    }

    @Test
    fun `resolve stops at root = true`() {
        val fm = fakeFileManager(
            files = mapOf(
                "/.editorconfig" to """
                    [*.kt]
                    indent_size = 8
                """.trimIndent(),
                "/project/.editorconfig" to """
                    root = true
                    [*.kt]
                    indent_size = 2
                """.trimIndent(),
            ),
            directories = setOf("/project/src", "/project", "/"),
        )
        val reader = DefaultEditorConfigReader(fm)
        val result = reader.resolve("/project/src".toPath(), defaults = FormatConfig())
        // Should stop at /project and not read /
        assertEquals(2, result.indentSize)
    }

    @Test
    fun `resolve treats file path by using its parent`() {
        val fm = fakeFileManager(
            files = mapOf(
                "/project/.editorconfig" to """
                    root = true
                    [*.kt]
                    indent_style = tab
                """.trimIndent(),
            ),
            directories = setOf("/project"),
        )
        val reader = DefaultEditorConfigReader(fm)
        val result = reader.resolve("/project/output.kt".toPath(), defaults = FormatConfig())
        assertEquals(IndentStyle.TAB, result.indentStyle)
    }

    private fun fakeFileManager(
        files: Map<String, String>,
        directories: Set<String> = emptySet(),
    ): FileManager = object : FileManager {
        override fun isDirectory(path: Path): Boolean = path.toString() in directories

        override fun createDirectory(dir: Path) = Unit
        override fun createDirectories(dir: Path, mustCreate: Boolean) = Unit

        override fun exists(path: Path): Boolean = path.toString() in files

        override fun readContent(file: Path): String =
            files[file.toString()] ?: throw IOException("File not found: $file")

        override fun readBytes(file: Path): ByteArray = readContent(file).encodeToByteArray()

        override fun write(
            file: Path,
            mustCreate: Boolean,
            writerAction: BufferedSink.() -> Unit,
        ) = Unit

        override fun findFilesToProcess(
            from: Path,
            recursive: Boolean,
            maxDepth: Int?,
            exclude: Regex?,
        ): List<Path> = emptyList()

        override fun copy(source: Path, target: Path) = Unit
        override fun deleteRecursively(fileOrDirectory: Path, mustExist: Boolean) = Unit
        override fun delete(fileOrDirectory: Path, mustExist: Boolean) = Unit
    }
}
