package dev.tonholo.s2c.gradle.tasks.worker

import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkActionResult.Status
import org.gradle.api.provider.Property
import java.io.File
import java.util.Properties

/**
 * Represents the result of an individual icon parsing operation
 * performed by a Gradle worker.
 *
 * This class encapsulates the outcome, including the status
 * (success or error), paths of the original and generated files,
 * and any relevant messages. It is designed to be serializable
 * to a properties file, allowing results to be persisted and
 * read across different processes.
 *
 * @property origin The absolute path of the original source icon
 *  file (e.g., an SVG or XML).
 * @property output The absolute path of the generated output file
 *  (e.g., a Kotlin file). This may be empty if the operation failed.
 * @property status The outcome of the parsing operation. See [Status].
 * @property message An accompanying message, typically used to provide
 *  details about an error.
 */
class IconParsingWorkActionResult(
    val origin: String,
    val output: String,
    val status: Status = Status.Unknown,
    val message: String,
) {
    enum class Status { Ok, Error, Unknown }

    private fun toProperties(): Properties = Properties().apply {
        setProperty(::origin.name, origin)
        setProperty(::output.name, output)
        setProperty(::status.name, status.name)
        setProperty(::message.name, message)
    }

    fun store(resultFilePath: Property<String>) {
        val resultProps = toProperties()
        val resultFile = File(resultFilePath.get())
        resultFile.parentFile?.mkdirs()
        resultFile.outputStream().use { out ->
            resultProps.store(out, null)
        }
    }

    companion object {
        fun success(origin: String, output: String) = IconParsingWorkActionResult(
            origin = origin,
            output = output,
            status = Status.Ok,
            message = "",
        )

        fun error(origin: String, message: String) = IconParsingWorkActionResult(
            origin = origin,
            output = "",
            status = Status.Error,
            message = message,
        )
    }
}

fun Properties.toResult(): IconParsingWorkActionResult = IconParsingWorkActionResult(
    origin = getProperty(IconParsingWorkActionResult::origin.name)
        ?: error("Missing 'origin' property in result file"),
    output = getProperty(IconParsingWorkActionResult::output.name)
        ?: error("Missing 'output' property in result file"),
    status = getProperty(IconParsingWorkActionResult::status.name)?.let { statusStr ->
        runCatching { Status.valueOf(statusStr) }
            .getOrElse { error("Invalid status value: $statusStr") }
    } ?: error("Missing 'status' property in result file"),
    message = getProperty(IconParsingWorkActionResult::message.name)
        ?: error("Missing 'message' property in result file"),
)
