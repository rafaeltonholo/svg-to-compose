package dev.tonholo.s2c.optimizer

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.command.command
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.MissingDependencyException
import dev.tonholo.s2c.error.OptimizationException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.extensions.filename
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.optimizer.svgo.SvgoConfigContent
import okio.Path
import okio.Path.Companion.toPath

sealed class Optimizer {
    /**
     * Represents the external command that will be used
     * for optimization.
     */
    abstract val command: String

    /**
     * Represents the extension of file types that the
     * optimizer can process.
     */
    abstract val allowedExtension: String

    /**
     * Dynamically generates an error message in the case
     * that a required command is not installed.
     *
     * The error message instructs the user on how to install
     * the missing command.
     */
    private val errorMessage: String
        get() = "⚠️ $command is required. Use npm -g install $command to install."

    /**
     * Represents the version flag argument used to verify
     * the installation of the optimizers' software dependency
     * by running the command with this flag.
     *
     * By default, this flag is set to "--version".
     */
    protected open val versionFlag: String = "--version"

    /**
     * Checks if the required software for this optimizer is
     * installed on the current system.
     *
     * It runs the command for the optimizer with the [versionFlag]
     * as a argument and checks the exit code of the process.
     *
     * An exit code of `0` means that the command is available and
     * thus, the dependency is verified.
     *
     * @return A [Boolean], `true` if the required command is available
     * and `false` if it's not.
     */
    fun verifyDependency() =
        command(program = command) {
            args(versionFlag)
            showStdout = false
            showStderr = false
        }.also { (code, _) ->
            verbose("exit code = $code")
        }.exitCode == 0

    /**
     * Runs the optimization process on the given [file],
     * and returns a new [Path] of the optimized file.
     *
     * @param file The file to be optimized.
     * @return The path of the newly optimized file.
     */
    abstract fun run(file: Path): Path

    /**
     * Execute the optimization process by using external
     * software dependency.
     *
     * @param errorCode The error code used to identify
     * which optimizer failed to run.
     * @param args The optimizer arguments.
     * @throws OptimizationException when the optimization
     * fails to run.
     */
    protected fun runOptimization(
        errorCode: ErrorCode,
        vararg args: String,
    ) {
        verbose(
            """
                |Args:
                |   errorCode=$errorCode,
                |   args=${args.joinToString(" ")}
            """.trimMargin()
        )
        output("⏳ Running $command")
        try {
            command(program = command) {
                args(*args)
                trim = true
            }.also { (exitCode, output) ->
                if (exitCode != 0) {
                    throw OptimizationException(
                        errorCode,
                        message = "Optimization failed with exit code $exitCode: " +
                            (output.stdout ?: output.stderr ?: "No error message provided"),
                    )
                }
            }
        } catch (e: IllegalStateException) {
            throw OptimizationException(errorCode, throwable = e)
        }
        output("✅ Finished $command")
    }

    /**
     * Optimize SVG files by using an external CLI tool called SVGO.
     *
     * @see <a href="https://svgo.dev/">SVGO documentation</a>
     */
    data class SvgoOptimizer(
        val fileManager: FileManager,
    ) : Optimizer() {
        override val command: String = "svgo"
        override val allowedExtension: String = FileType.Svg.extension

        override fun run(file: Path): Path {
            val svgoConfigFilename = "svgo-config.mjs"
            val tempFolder = S2C_TEMP_FOLDER.toPath()
            val svgoConfigFile = tempFolder / svgoConfigFilename

            // Create temp directory in case of not having it yet.
            fileManager.createDirectory(tempFolder)

            if (!fileManager.exists(svgoConfigFile)) {
                output("⚙️ writing svgo config file")
                fileManager.write(svgoConfigFile) {
                    writeUtf8(SvgoConfigContent)
                }
            }

            val tempDir = requireNotNull(file.parent)
            val optimizedFile = tempDir / "${file.filename}.optimized.svg"

            runOptimization(
                errorCode = ErrorCode.SvgoOptimizationError,
                file.toString(),
                "--config=$S2C_TEMP_FOLDER/$svgoConfigFilename",
                "-o",
                optimizedFile.toString(),
            )

            return optimizedFile
        }
    }

    /**
     * Optimize AVG files by using an external CLI tool called Avocado.
     * Similar to SVGO, but for Android Vector Graphics files.
     *
     * @see <a href="https://github.com/alexjlockwood/avocado">Avocado documentation</a>
     */
    data object AvocadoOptimizer : Optimizer() {
        override val command: String = "avocado"
        override val allowedExtension: String = FileType.Avg.extension

        override fun run(file: Path): Path {
            runOptimization(
                errorCode = ErrorCode.AvocadoOptimizationError,
                file.toString(),
            )
            return file
        }
    }

    class Factory(
        fileManager: FileManager,
    ) {
        /**
         * Set of optimizers that will be used specifically for SVG files.
         */
        private val svgOptimizers: Set<Optimizer> = setOf(
            SvgoOptimizer(fileManager),
        )

        /**
         * Set of optimizers that will be used specifically for AVG files.
         */
        private val avgOptimizers: Set<Optimizer> = setOf(
            AvocadoOptimizer,
        )

        /**
         * Verify the availability of dependencies required by the optimizers.
         *
         * @param hasSvg [Boolean] flag indicating whether to check for SVG-related
         * dependencies.
         * @param hasAvg [Boolean] flag indicating whether to check for AVG-related
         * dependencies.
         * @throws MissingDependencyException when an optimizer dependency is missing
         */
        fun verifyDependency(hasSvg: Boolean, hasAvg: Boolean) {
            var hasMissingDependency = false
            fun showErrorLog(missingDependency: Boolean, optimizer: Optimizer) {
                if (missingDependency) {
                    printEmpty()
                    output(optimizer.errorMessage)
                    hasMissingDependency = true
                }
            }

            if (hasSvg) {
                svgOptimizers.forEach {
                    verbose("Verifying $it")
                    it.verifyDependency().also { hasDependency ->
                        showErrorLog(missingDependency = hasDependency.not(), optimizer = it)
                    }
                }
            }

            if (hasAvg) {
                avgOptimizers.forEach {
                    verbose("Verifying $it")
                    it.verifyDependency().also { hasDependency ->
                        showErrorLog(missingDependency = hasDependency.not(), optimizer = it)
                    }
                }
            }

            if (hasMissingDependency) {
                throw MissingDependencyException(
                    errorCode = ErrorCode.MissingCoreDependency,
                    message = "Missing core dependency to optimizer. " +
                        "Please install the dependency or use the CLI without the flag --optimizer",
                )
            }
        }

        /**
         * Checks the type of file (SVG or AVG) and invokes the correct
         * optimization process using the appropriate optimization tools.
         *
         * @param file A [Path] object that represents the file which
         * optimization process is to be performed upon.
         * @return The [Path] object of the optimized file.
         */
        fun optimize(file: Path): Path {
            output("🏎️  Optimizing ${file.extension}")
            printEmpty()
            return if (file.extension == FileType.Svg.extension) {
                svgOptimizers.fold(file) { currentFile, optimizer ->
                    optimizer.run(currentFile)
                }
            } else {
                avgOptimizers.fold(file) { currentFile, optimizer ->
                    optimizer.run(currentFile)
                }
            }.also {
                printEmpty()
            }
        }
    }
}
