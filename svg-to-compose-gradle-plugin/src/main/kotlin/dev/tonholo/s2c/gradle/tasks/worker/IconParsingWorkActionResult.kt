package dev.tonholo.s2c.gradle.tasks.worker

import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkActionResult.Status
import java.io.File
import java.util.Properties
import org.gradle.api.provider.Property

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

    /**
     * Convert this result into a Properties object keyed by the property names.
     *
     * @return A Properties where keys are "origin", "output", "status", and "message" with their corresponding string values; the status value is the enum name.
     */
    private fun toProperties(): Properties = Properties().apply {
        setProperty(::origin.name, origin)
        setProperty(::output.name, output)
        setProperty(::status.name, status.name)
        setProperty(::message.name, message)
    }

    /**
     * Persists this result to a Java properties file at the given filesystem path.
     *
     * Ensures the parent directory exists before writing the properties file.
     *
     * @param resultFilePath Filesystem path (absolute or relative) where the properties file will be created.
     */
    fun store(resultFilePath: Property<String>) {
        val resultProps = toProperties()
        val resultFile = File(resultFilePath.get())
        resultFile.parentFile?.mkdirs()
        resultFile.outputStream().use { out ->
            resultProps.store(out, null)
        }
    }

    companion object {
        /**
         * Create a result representing a successful icon parsing operation.
         *
         * @param origin Absolute path of the original source icon file.
         * @param output Absolute path of the generated output file.
         * @return An IconParsingWorkActionResult with status Ok and an empty message.
         */
        fun success(origin: String, output: String) = IconParsingWorkActionResult(
            origin = origin,
            output = output,
            status = Status.Ok,
            message = "",
        )

        /**
         * Create a result representing a failed icon parsing operation.
         *
         * @param origin Absolute path of the original source icon file.
         * @param message Error message describing the failure.
         * @return An IconParsingWorkActionResult with status `Error`, empty `output`, and the provided `message`.
         */
        fun error(origin: String, message: String) = IconParsingWorkActionResult(
            origin = origin,
            output = "",
            status = Status.Error,
            message = message,
        )
    }
}

/**
 * Creates an IconParsingWorkActionResult from this Properties instance.
 *
 * @receiver Properties containing the keys "origin", "output", "status", and "message".
 * @return An IconParsingWorkActionResult built from the corresponding property values; the "status" value is parsed into the Status enum.
 */
fun Properties.toResult(): IconParsingWorkActionResult = IconParsingWorkActionResult(
    origin = getProperty(IconParsingWorkActionResult::origin.name),
    output = getProperty(IconParsingWorkActionResult::output.name),
    status = Status.valueOf(getProperty(IconParsingWorkActionResult::status.name)),
    message = getProperty(IconParsingWorkActionResult::message.name),
)