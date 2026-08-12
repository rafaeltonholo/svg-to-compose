package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ErrorReportWriterTest {

    private val fileSystem = FakeFileSystem()
    private val workingDir = "/work".toPath()

    private val parserConfig = ParserConfig(
        pkg = "com.example.icons",
        theme = "AppTheme",
        optimize = true,
        receiverType = null,
        addToMaterial = false,
        kmpPreview = false,
        noPreview = false,
        makeInternal = false,
        minified = false,
    )

    private val runConfig = RunConfig(
        inputPath = "./icons",
        outputPath = "./generated",
        parserConfig = parserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 0,
        recursive = false,
    )

    private fun writer(nowEpochMillis: () -> Long = { FIXED_EPOCH_MILLIS }) = ErrorReportWriter(
        fileSystem = fileSystem,
        workingDir = workingDir,
        nowEpochMillis = nowEpochMillis,
        platformInfoProvider = { FIXED_PLATFORM },
    )

    @AfterTest
    fun tearDown() {
        fileSystem.checkNoOpenFiles()
    }

    @Test
    fun `given no failed files - when write is called - then returns null and writes no file`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 10,
            succeeded = 10,
            failedFiles = emptyList(),
            stackTraceEnabled = false,
        )

        // Assert
        assertEquals(expected = null, actual = reportPath)
        val listing = fileSystem.list(dir = workingDir)
        assertTrue(
            actual = listing.none { it.name.startsWith("s2c-errors-") },
            message = "expected no error report file, found: $listing",
        )
    }

    @Test
    fun `given failed files - when write is called - then returns path to timestamped file in working dir`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 847,
            succeeded = 844,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "ic_broken_gradient.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Unsupported gradient type: mesh-gradient",
                ),
            ),
            stackTraceEnabled = false,
        )

        // Assert
        assertTrue(actual = reportPath != null, message = "expected a report path")
        val path = requireNotNull(reportPath)
        assertTrue(
            actual = path.name.startsWith("s2c-errors-"),
            message = "expected timestamped report file, got: ${path.name}",
        )
        assertTrue(actual = path.name.endsWith(".log"), message = path.name)
        assertTrue(actual = fileSystem.exists(path), message = "report file should exist")
    }

    @Test
    fun `given failed files - when write is called - then contents include version platform and config header`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "a.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad",
                ),
            ),
            stackTraceEnabled = false,
        )

        // Assert
        val path = requireNotNull(reportPath)
        val contents = fileSystem.read(path) { readUtf8() }
        assertTrue(actual = contents.contains("svg-to-compose v2.2.0"), message = contents)
        assertTrue(actual = contents.contains(FIXED_PLATFORM), message = contents)
        assertTrue(actual = contents.contains("Input: ./icons"), message = contents)
        assertTrue(actual = contents.contains("Output: ./generated"), message = contents)
        assertTrue(actual = contents.contains("Optimize: on"), message = contents)
    }

    @Test
    fun `given multiple failed files - when write is called - then contents list each file with error code and message`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 3,
            succeeded = 0,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "a.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Invalid path data",
                ),
                BugReportFailure(
                    fileName = "b.svg",
                    errorCode = ErrorCode.SvgoOptimizationError,
                    message = "svgo exit code 1",
                ),
            ),
            stackTraceEnabled = false,
        )

        // Assert
        val contents = fileSystem.read(requireNotNull(reportPath)) { readUtf8() }
        assertTrue(actual = contents.contains("a.svg"), message = contents)
        assertTrue(actual = contents.contains("b.svg"), message = contents)
        assertTrue(actual = contents.contains("ParseSvgError"), message = contents)
        assertTrue(actual = contents.contains("SvgoOptimizationError"), message = contents)
        assertTrue(actual = contents.contains("Invalid path data"), message = contents)
        assertTrue(actual = contents.contains("svgo exit code 1"), message = contents)
    }

    @Test
    fun `given stackTraceEnabled - when stack trace is present - then it is included in report contents`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "a.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad",
                    stackTrace = "java.lang.RuntimeException: boom\n  at foo.Bar.baz(Bar.kt:42)",
                ),
            ),
            stackTraceEnabled = true,
        )

        // Assert
        val contents = fileSystem.read(requireNotNull(reportPath)) { readUtf8() }
        assertTrue(
            actual = contents.contains("java.lang.RuntimeException: boom"),
            message = contents,
        )
        assertTrue(actual = contents.contains("at foo.Bar.baz(Bar.kt:42)"), message = contents)
    }

    @Test
    fun `given stackTraceEnabled false - when stack trace is present - then it is omitted from report contents`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "a.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad",
                    stackTrace = "java.lang.RuntimeException: boom",
                ),
            ),
            stackTraceEnabled = false,
        )

        // Assert
        val contents = fileSystem.read(requireNotNull(reportPath)) { readUtf8() }
        assertFalse(actual = contents.contains("java.lang.RuntimeException"), message = contents)
    }

    @Test
    fun `given a fixed clock - when write is called - then filename replaces the timestamp colons with hyphens`() {
        // Arrange
        fileSystem.createDirectories(workingDir)
        val reportWriter = writer()

        // Act
        val reportPath = reportWriter.write(
            version = "2.2.0",
            config = runConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(
                BugReportFailure(
                    fileName = "ic_broken_gradient.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Unsupported gradient type: mesh-gradient",
                ),
            ),
            stackTraceEnabled = false,
        )

        // Assert
        assertEquals(
            expected = workingDir / "s2c-errors-2026-04-07T14-50-00.log",
            actual = reportPath,
        )
    }

    companion object {
        private const val FIXED_PLATFORM = "macOS arm64"
        private const val FIXED_EPOCH_MILLIS = 1_775_573_400_000L
    }
}
