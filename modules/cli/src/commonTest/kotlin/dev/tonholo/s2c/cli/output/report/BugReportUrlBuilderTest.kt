package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.error.ErrorCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BugReportUrlBuilderTest {

    private fun failed(
        fileName: String,
        errorCode: ErrorCode = ErrorCode.ParseSvgError,
        message: String = "bad",
    ) = BugReportFailure(fileName = fileName, errorCode = errorCode, message = message)

    private fun build(
        builder: BugReportUrlBuilder = BugReportUrlBuilder(),
        commandLine: String = "s2c -p com.example.icons -o ./generated ./icons",
        totalFiles: Int = 1,
        succeeded: Int = 0,
        failedFiles: List<BugReportFailure> = listOf(failed(fileName = "a.svg")),
    ): String = builder.build(
        version = "2.2.0",
        platform = "macOS arm64",
        commandLine = commandLine,
        totalFiles = totalFiles,
        succeeded = succeeded,
        failedFiles = failedFiles,
    )

    @Test
    fun `given a failed run - when build is called - then url starts with issues new endpoint`() {
        // Arrange & Act
        val url = build()

        // Assert
        assertTrue(
            actual = url.startsWith("https://github.com/rafaeltonholo/svg-to-compose/issues/new?"),
            message = "url: $url",
        )
    }

    @Test
    fun `given builder - when build is called - then template targets the bug report issue form`() {
        // Arrange & Act
        val url = build()

        // Assert
        assertEquals(
            expected = "bug_report.yml",
            actual = decodeQueryParam(url = url, key = "template"),
        )
    }

    @Test
    fun `given multiple error codes - when build is called - then title lists unique codes separated by commas`() {
        // Arrange
        val failures = listOf(
            failed(fileName = "a.svg", errorCode = ErrorCode.ParseSvgError),
            failed(fileName = "b.svg", errorCode = ErrorCode.ParseSvgError),
            failed(fileName = "c.svg", errorCode = ErrorCode.SvgoOptimizationError),
        )

        // Act
        val url = build(totalFiles = 3, failedFiles = failures)

        // Assert
        assertEquals(
            expected = "[Bug]: Conversion failed: ParseSvgError, SvgoOptimizationError",
            actual = decodeQueryParam(url = url, key = "title"),
        )
    }

    @Test
    fun `given a failed run - when build is called - then description is a summary without per-file output`() {
        // Arrange
        val failures = listOf(
            failed(
                fileName = "ic_broken_gradient.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
        )

        // Act
        val url = build(failedFiles = failures)

        // Assert
        val description = decodeQueryParam(url = url, key = "bug-description")
        assertTrue(actual = description.contains("failed to convert 1 of 1"), message = description)
        assertTrue(actual = description.contains("ParseSvgError"), message = description)
        assertTrue(
            actual = !description.contains("ic_broken_gradient.svg"),
            message = "per-file output belongs in input-output, not the description: $description",
        )
    }

    @Test
    fun `given a command line - when build is called - then reproduction steps carry the executed command`() {
        // Arrange
        val commandLine = "s2c --optimize false -p com.example.icons -r ./assets/icons.zip"

        // Act
        val url = build(commandLine = commandLine)

        // Assert
        val steps = decodeQueryParam(url = url, key = "reproduction-steps")
        assertTrue(actual = steps.contains("`$commandLine`"), message = steps)
    }

    @Test
    fun `given a failed run - when build is called - then environment lists os and version only`() {
        // Arrange & Act
        val url = build()

        // Assert
        val environment = decodeQueryParam(url = url, key = "environment")
        assertTrue(actual = environment.contains("- OS: macOS arm64"), message = environment)
        assertTrue(actual = environment.contains("- svg-to-compose version: 2.2.0"), message = environment)
        assertTrue(
            actual = !environment.contains("input="),
            message = "environment must not carry reconstructed config: $environment",
        )
    }

    @Test
    fun `given failures - when build is called - then input-output carries the error output and attach request`() {
        // Arrange
        val failures = listOf(
            failed(
                fileName = "ic_broken_gradient.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
        )

        // Act
        val url = build(failedFiles = failures)

        // Assert
        val inputOutput = decodeQueryParam(url = url, key = "input-output")
        val expectedErrorLine = "- ParseSvgError: ic_broken_gradient.svg - Unsupported gradient type: mesh-gradient"
        assertTrue(actual = inputOutput.contains(expectedErrorLine), message = inputOutput)
        assertTrue(actual = inputOutput.contains("attach a minimal reproducing SVG"), message = inputOutput)
    }

    @Test
    fun `given a failed run - when build is called - then no additional-context or local path is in the url`() {
        // Arrange & Act
        val url = build()

        // Assert
        assertTrue(
            actual = !url.contains("additional-context="),
            message = "local report paths are useless to issue readers: $url",
        )
        assertTrue(actual = !url.contains("s2c-errors"), message = url)
    }

    @Test
    fun `given many failed files - when url would exceed limit - then file list is dropped and url stays within cap`() {
        // Arrange
        val builder = BugReportUrlBuilder(maxUrlLength = 700)
        val failures = List(size = 200) { index ->
            failed(
                fileName = "ic_icon_$index.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Some very long message describing the failure of icon $index",
            )
        }

        // Act
        val url = build(builder = builder, totalFiles = 200, failedFiles = failures)

        // Assert
        assertTrue(
            actual = url.length <= 700,
            message = "URL length ${url.length} exceeded the configured limit, url: $url",
        )
        val description = decodeQueryParam(url = url, key = "bug-description")
        assertTrue(actual = description.contains("Error codes: ParseSvgError"), message = description)
        assertTrue(actual = description.contains("saved error report"), message = description)
        assertTrue(
            actual = !url.contains("input-output="),
            message = "truncated url must drop the file list: $url",
        )
        val steps = decodeQueryParam(url = url, key = "reproduction-steps")
        assertTrue(actual = steps.contains("s2c "), message = steps)
    }

    @Test
    fun `given a non-ascii value - when percentEncode is called - then utf8 bytes emit uppercase octets`() {
        // Arrange
        val value = "ícone č.svg"

        // Act
        val encoded = percentEncode(value = value)

        // Assert
        assertEquals(expected = "%C3%ADcone%20%C4%8D.svg", actual = encoded)
    }

    @Test
    fun `given special characters in the command - when build is called - then they are percent-encoded`() {
        // Arrange & Act
        val url = build(commandLine = "s2c -o \"./my icons & stuff\" input.svg")

        // Assert
        assertTrue(
            actual = url.contains("%26"),
            message = "expected '&' to be percent-encoded as %26 in: $url",
        )
        assertTrue(actual = !url.contains(" "), message = "raw space found in: $url")
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
