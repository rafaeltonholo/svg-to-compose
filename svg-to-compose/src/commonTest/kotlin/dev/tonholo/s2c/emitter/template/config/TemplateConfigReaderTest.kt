package dev.tonholo.s2c.emitter.template.config

import dev.tonholo.s2c.io.FileManager
import okio.BufferedSink
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TemplateConfigReaderTest {
    @Test
    fun `resolve parses template from explicit path`() {
        val toml = """
            [templates]
            icon_template = "val ${'$'}{icon:name}: ImageVector = TODO()"
        """.trimIndent()
        val fm = fakeFileManager(files = mapOf("/project/s2c.template.toml" to toml))
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.resolve("/project/s2c.template.toml".toPath())
        assertNotNull(config.templates.iconTemplate)
    }

    @Test
    fun `discover finds template in output directory`() {
        val toml = """
            [templates]
            icon_template = "val ${'$'}{icon:name}: ImageVector = TODO()"
        """.trimIndent()
        val fm = fakeFileManager(
            files = mapOf("/project/s2c.template.toml" to toml),
            directories = setOf("/project/output", "/project"),
        )
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.discover("/project/output".toPath())
        assertNotNull(config)
        assertNotNull(config.templates.iconTemplate)
    }

    @Test
    fun `discover walks up directories to find template`() {
        val toml = """
            [definitions.imports]
            builder = "com.example.icons.builder"
        """.trimIndent()
        val fm = fakeFileManager(
            files = mapOf("/root/s2c.template.toml" to toml),
            directories = setOf("/root/project/src/output", "/root/project/src", "/root/project", "/root"),
        )
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.discover("/root/project/src/output".toPath())
        assertNotNull(config)
        assertEquals("com.example.icons.builder", config.definitions.imports["builder"])
    }

    @Test
    fun `discover returns null when no template file exists`() {
        val fm = fakeFileManager(
            files = emptyMap(),
            directories = setOf("/project/output", "/project", "/"),
        )
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.discover("/project/output".toPath())
        assertNull(config)
    }

    @Test
    fun `discover treats file path by using its parent`() {
        val toml = """
            [templates]
            icon_template = "val ${'$'}{icon:name}: ImageVector = TODO()"
        """.trimIndent()
        val fm = fakeFileManager(
            files = mapOf("/project/s2c.template.toml" to toml),
            directories = setOf("/project"),
        )
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.discover("/project/output/Icon.kt".toPath())
        assertNotNull(config)
    }

    @Test
    fun `discover picks closest template when multiple exist`() {
        val parentToml = """
            [definitions.imports]
            builder = "com.parent.builder"
        """.trimIndent()
        val childToml = """
            [definitions.imports]
            builder = "com.child.builder"
        """.trimIndent()
        val fm = fakeFileManager(
            files = mapOf(
                "/root/s2c.template.toml" to parentToml,
                "/root/project/s2c.template.toml" to childToml,
            ),
            directories = setOf("/root/project/src", "/root/project", "/root"),
        )
        val reader = DefaultTemplateConfigReader(fm)

        val config = reader.discover("/root/project/src".toPath())
        assertNotNull(config)
        assertEquals("com.child.builder", config.definitions.imports["builder"])
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
            excludeDir: Regex?,
        ): List<Path> = emptyList()

        override fun copy(source: Path, target: Path) = Unit
        override fun deleteRecursively(fileOrDirectory: Path, mustExist: Boolean) = Unit
        override fun delete(fileOrDirectory: Path, mustExist: Boolean) = Unit
    }
}
