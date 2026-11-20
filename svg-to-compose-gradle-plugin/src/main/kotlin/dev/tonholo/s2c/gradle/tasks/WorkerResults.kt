package dev.tonholo.s2c.gradle.tasks

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkActionResult.Status
import dev.tonholo.s2c.gradle.tasks.worker.toResult
import okio.Path
import okio.Path.Companion.toPath
import java.io.File
import java.util.Properties

/**
 * Validates and reads the result files written by the workers of one processor configuration.
 *
 * @param configurationName Name of the processor configuration being parsed.
 * @param expectedResultCount Number of files submitted to the workers.
 * @param resultsDir Directory where workers wrote their result files.
 * @param errors Destination for the origin/error pairs found in the results.
 * @param outputFiles Destination for the origin/generated file pairs found in the results.
 * @throws ExitProgramException When the results are incomplete.
 */
internal fun processWorkerResults(
    configurationName: String,
    expectedResultCount: Int,
    resultsDir: File,
    errors: MutableMap<Path, Throwable>,
    outputFiles: MutableMap<Path, Path>,
) {
    checkWorkerResults(
        configurationName = configurationName,
        expectedResultCount = expectedResultCount,
        resultsDir = resultsDir,
    )
    collectWorkerResults(resultsDir = resultsDir, errors = errors, outputFiles = outputFiles)
}

/**
 * Verifies that every submitted work item reported back before the results are collected.
 *
 * @param configurationName Name of the processor configuration being parsed.
 * @param expectedResultCount Number of files submitted to the workers.
 * @param resultsDir Directory where workers write their result files.
 * @throws ExitProgramException When the amount of result files differs from
 * [expectedResultCount], meaning at least one worker did not report its outcome.
 */
private fun checkWorkerResults(
    configurationName: String,
    expectedResultCount: Int,
    resultsDir: File,
) {
    val actualResultCount = resultsDir.listFiles()?.size ?: 0
    if (actualResultCount != expectedResultCount) {
        throw ExitProgramException(
            errorCode = ErrorCode.GradlePluginError,
            message = "Icon parsing for configuration '$configurationName' is incomplete. " +
                "Expected $expectedResultCount worker result file(s) but found $actualResultCount. " +
                "Some workers may have failed silently. Run with --info or --debug for more details.",
        )
    }
}

/**
 * Reads the worker result files and splits them into successful outputs and errors.
 *
 * @param resultsDir Directory where workers wrote their result files.
 * @param errors Destination for the origin/error pairs found in the results.
 * @param outputFiles Destination for the origin/generated file pairs found in the results.
 */
private fun collectWorkerResults(
    resultsDir: File,
    errors: MutableMap<Path, Throwable>,
    outputFiles: MutableMap<Path, Path>,
) {
    resultsDir.listFiles()?.forEach { file ->
        val props = Properties()
        file.inputStream().use { props.load(it) }
        val result = props.toResult()
        val origin = result.origin.toPath()
        when (result.status) {
            Status.Ok -> outputFiles[origin] = result.output.toPath()
            Status.Error -> errors[origin] = RuntimeException(result.message)
            Status.Unknown -> errors[origin] = RuntimeException("Unexpected error while processing $file")
        }
    }
}
