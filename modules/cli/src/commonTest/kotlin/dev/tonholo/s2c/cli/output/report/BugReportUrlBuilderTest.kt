package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BugReportUrlBuilderTest {

    private val defaultParserConfig = ParserConfig(
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

    private val defaultRunConfig = RunConfig(
        inputPath = "./icons",
        outputPath = "./generated",
        parserConfig = defaultParserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 0,
        recursive = false,
    )

    private fun failed(
        fileName: String,
        errorCode: ErrorCode = ErrorCode.ParseSvgError,
        message: String = "bad",
    ) = BugReportFailure(fileName = fileName, errorCode = errorCode, message = message)

    @Test
    fun `given version and platform and failures - when build is called - then url starts with issues new endpoint`() {
        // Arrange
        val builder = BugReportUrlBuilder()

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 10,
            succeeded = 9,
            failedFiles = listOf(failed(fileName = "a.svg")),
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        assertTrue(
            actual = url.startsWith("https://github.com/rafaeltonholo/svg-to-compose/issues/new?"),
            message = "url: $url",
        )
    }

    @Test
    fun `given multiple error codes - when build is called - then title lists unique codes separated by commas`() {
        // Arrange
        val builder = BugReportUrlBuilder()
        val failures = listOf(
            failed(fileName = "a.svg", errorCode = ErrorCode.ParseSvgError),
            failed(fileName = "b.svg", errorCode = ErrorCode.ParseSvgError),
            failed(fileName = "c.svg", errorCode = ErrorCode.SvgoOptimizationError),
        )

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 3,
            succeeded = 0,
            failedFiles = failures,
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        val decodedTitle = decodeQueryParam(url = url, key = "title")
        assertEquals(
            expected = "[Bug]: Conversion failed: ParseSvgError, SvgoOptimizationError",
            actual = decodedTitle,
        )
    }

    @Test
    fun `given a space in platform - when build is called - then the space is percent-encoded in the URL`() {
        // Arrange
        val builder = BugReportUrlBuilder()

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(failed(fileName = "a.svg")),
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        assertTrue(
            actual = url.contains("macOS%20arm64"),
            message = "expected URL-encoded platform in: $url",
        )
    }

    @Test
    fun `given a failed conversion - when build is called - then issue form fields carry description environment and context`() {
        // Arrange
        val builder = BugReportUrlBuilder()

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(
                failed(
                    fileName = "ic_broken_gradient.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Unsupported gradient type: mesh-gradient",
                ),
            ),
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        val description = decodeQueryParam(url = url, key = "bug-description")
        assertTrue(actual = description.contains("ParseSvgError"), message = description)
        assertTrue(actual = description.contains("ic_broken_gradient.svg"), message = description)
        val environment = decodeQueryParam(url = url, key = "environment")
        assertTrue(actual = environment.contains("- OS: macOS arm64"), message = environment)
        assertTrue(actual = environment.contains("- svg-to-compose version: 2.2.0"), message = environment)
        val additionalContext = decodeQueryParam(url = url, key = "additional-context")
        assertTrue(actual = additionalContext.contains("./s2c-errors-1.log"), message = additionalContext)
    }

    @Test
    fun `given many failed files - when url would exceed limit - then description is truncated to codes and log pointer`() {
        // Arrange
        val builder = BugReportUrlBuilder(maxUrlLength = 600)
        val failures = List(size = 200) { index ->
            failed(
                fileName = "ic_icon_$index.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Some very long message describing the failure of icon $index",
            )
        }

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 200,
            succeeded = 0,
            failedFiles = failures,
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        assertTrue(
            actual = url.length <= 600,
            message = "URL length ${url.length} exceeded the configured limit, url: $url",
        )
        val description = decodeQueryParam(url = url, key = "bug-description")
        assertTrue(actual = description.contains("Error codes: ParseSvgError"), message = description)
        assertTrue(actual = description.contains("See the saved log file"), message = description)
        assertTrue(actual = description.contains("./s2c-errors-1.log"), message = description)
        val environment = decodeQueryParam(url = url, key = "environment")
        assertTrue(actual = environment.contains("- svg-to-compose version: 2.2.0"), message = environment)
        assertTrue(
            actual = !url.contains("additional-context="),
            message = "truncated url must drop additional-context to save length: $url",
        )
    }

    @Test
    fun `given special characters in paths - when build is called - then they are properly percent-encoded`() {
        // Arrange
        val builder = BugReportUrlBuilder()
        val config = defaultRunConfig.copy(inputPath = "./my icons & stuff")

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = config,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(failed(fileName = "a.svg")),
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        assertTrue(
            actual = url.contains("%26"),
            message = "expected '&' to be percent-encoded as %26 in: $url",
        )
        // Raw space must not appear inside the query string
        assertTrue(actual = !url.contains(" "), message = "raw space found in: $url")
    }

    @Test
    fun `given builder - when build is called - then template targets the bug report issue form`() {
        // Arrange
        val builder = BugReportUrlBuilder()

        // Act
        val url = builder.build(
            version = "2.2.0",
            platform = "macOS arm64",
            config = defaultRunConfig,
            totalFiles = 1,
            succeeded = 0,
            failedFiles = listOf(failed(fileName = "a.svg")),
            reportPath = "./s2c-errors-1.log",
        )

        // Assert
        assertEquals(
            expected = "bug_report.yml",
            actual = decodeQueryParam(url = url, key = "template"),
        )
    }

    /**
     * Decodes a single query parameter value from a URL.
     *
     * Implemented locally to avoid pulling in a URL helper; we only need the
     * inverse of the percent-encoding scheme the builder produces.
     */
    private fun decodeQueryParam(url: String, key: String): String {
        val query = url.substringAfter(delimiter = '?')
        val pair = query.split('&').firstOrNull { it.startsWith("$key=") }
            ?: error("query parameter '$key' not found in $url")
        val raw = pair.substringAfter(delimiter = '=')
        return percentDecode(raw = raw)
    }

    private fun percentDecode(raw: String): String {
        val bytes = mutableListOf<Byte>()
        var index = 0
        while (index < raw.length) {
            when (val char = raw[index]) {
                '%' -> {
                    val hex = raw.substring(startIndex = index + 1, endIndex = index + 3)
                    bytes.add(hex.toInt(radix = 16).toByte())
                    index += 3
                }

                '+' -> {
                    bytes.add(' '.code.toByte())
                    index += 1
                }

                else -> {
                    for (byte in char.toString().encodeToByteArray()) bytes.add(byte)
                    index += 1
                }
            }
        }
        return bytes.toByteArray().decodeToString()
    }
}
