package dev.tonholo.s2c.optimizer

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.command.command
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.MissingDependencyException
import dev.tonholo.s2c.error.OptimizationException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

sealed interface Optimizer {
    val command: String
    val installCommand: String
        get() = command
    val allowedExtension: String
    val errorMessage: String
        get() = "⚠️ $command is required. Use npm -g install $installCommand to install."

    fun verifyDependency() =
        command(program = command) {
            args("--version")
            showStdout = false
            showStderr = false
        }.also { (code, _) ->
            verbose("exit code = $code")
        }.exitCode == 0

    fun run(file: Path)
    fun runOptimization(
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
            }.also { (exitCode, _) ->
                if (exitCode != 0) {
                    throw OptimizationException(errorCode)
                }
            }
        } catch (e: IllegalStateException) {
            throw OptimizationException(errorCode, throwable = e)
        }
        output("✅ Finished $command")
    }

    data class SvgoOptimizer(
        val fileSystem: FileSystem,
    ) : Optimizer {
        override val command: String = "svgo"
        override val allowedExtension: String = ".svg"
        override fun run(file: Path) {
            val svgoConfigFilename = "svgo-config.js"
            val tempFolder = S2C_TEMP_FOLDER.toPath()
            val svgoConfigFile = tempFolder / svgoConfigFilename

            // Create temp directory in case of not having it yet.
            fileSystem.createDirectory(tempFolder)

            if (!fileSystem.exists(svgoConfigFile)) {
                output("⚙️ writing svgo config file")
                val svgoConfig = """
                    |module.exports = {
                    |    plugins: [
                    |        {
                    |            name: "addViewBoxWhenNotPresent",
                    |            description: "Add a viewBox to the SVG tag, when it is not present, to fix issue related to svg2vectordrawable.",
                    |            fn: () => {
                    |                return {
                    |                    element: {
                    |                        enter: (node, parentNode) => {
                    |                            // We are only adding the viewBox to the root element
                    |                            // to fix a bug on svg2vectordrawable.
                    |                            if (node.name !== "svg") {
                    |                                return;
                    |                            }
                    |
                    |                            if (
                    |                                (!node.attributes.width || !node.attributes.height) &&
                    |                                !node.attributes.viewBox
                    |                            ) {
                    |                                // Missing both width, height and viewBox. Nothing to do.
                    |                                return;
                    |                            }
                    |
                    |                            if (
                    |                                node.attributes.viewBox ||
                    |                                node.attributes.viewBox === ""
                    |                            ) {
                    |                                // The viewBox is present. Nothing to do.
                    |                                return;
                    |                            }
                    |
                    |                            const width = node.attributes.width.replace(/px${'$'}/, '')
                    |                            const height = node.attributes.height.replace(/px${'$'}/, '')
                    |                            node.attributes.viewBox = `0 0 ${'$'}{width} ${'$'}{height}`;
                    |                        }
                    |                    }
                    |                }
                    |            },
                    |        },
                    |        {
                    |            name: "bakeWidthAndHeightFromViewBox",
                    |            description: "Add a witdh and height to the SVG tag, when it is not present, extracting from viewBox.",
                    |            fn: () => {
                    |                return {
                    |                    element: {
                    |                        enter: (node, parentNode) => {
                    |                            // We are only adding the viewBox to the root element
                    |                            // to fix a bug on svg2vectordrawable.
                    |                            if (node.name !== "svg") {
                    |                                return;
                    |                            }
                    |                            if (!node.attributes.viewBox || node.attributes.viewBox === "") {
                    |                                // ViewBox is not present. Nothing to do.
                    |                                return;
                    |                            }
                    |
                    |                            if (node.attributes.width && node.attributes.height) {
                    |                                // Both width, height is present. Nothing to do.
                    |                                return;
                    |                            }
                    |
                    |                            const nums = node.attributes.viewBox.split(/[ ,]+/g);
                    |                            if (nums.length !== 4) {
                    |                                // Malformed viewbox. Nothing to do.
                    |                                return;
                    |                            }
                    |
                    |                            node.attributes.width = nums[2];
                    |                            node.attributes.height = nums[3];
                    |                        }
                    |                    }
                    |                }
                    |            },
                    |        },
                    |        {
                    |            name: "convertPathData",
                    |            params: {
                    |                leadingZero: false,
                    |                floatPrecision: 2,
                    |            }
                    |        }
                    |    ]
                    |}
                """.trimMargin()
                fileSystem.write(svgoConfigFile) {
                    writeUtf8(svgoConfig)
                }
            }

            runOptimization(
                errorCode = ErrorCode.SvgoOptimizationError,
                "$S2C_TEMP_FOLDER/target.svg",
                "--config=$S2C_TEMP_FOLDER/$svgoConfigFilename",
                "-o",
                "$S2C_TEMP_FOLDER/target.optimized.svg",
            )
        }
    }

    data object S2vOptimizer : Optimizer {
        override val command: String = "s2v"
        override val installCommand: String = "svg2vectordrawable"
        override val allowedExtension: String = ".svg"

        override fun run(file: Path) {
            runOptimization(
                errorCode = ErrorCode.S2vOptimizationError,
                "-p",
                "2",
                "-i",
                "$S2C_TEMP_FOLDER/target.optimized.svg",
                "-o",
                "$S2C_TEMP_FOLDER/target.xml",
            )
        }
    }

    data object AvocadoOptimizer : Optimizer {
        override val command: String = "avocado"
        override val allowedExtension: String = ".xml"

        override fun run(file: Path) {
            runOptimization(
                errorCode = ErrorCode.AvocadoOptimizationError,
                "$S2C_TEMP_FOLDER/target.xml"
            )
        }
    }

    class Factory(
        fileSystem: FileSystem,
    ) {
        private val svgOptimizers = setOf(
            SvgoOptimizer(fileSystem),
            S2vOptimizer,
        )
        private val xmlOptimizers = setOf(
            AvocadoOptimizer,
        )

        fun verifyDependency() {
            var hasMissingDependency = false
            fun showErrorLog(missingDependency: Boolean, optimizer: Optimizer) {
                if (missingDependency) {
                    printEmpty()
                    output(optimizer.errorMessage)
                    hasMissingDependency = true
                }
            }

            svgOptimizers.forEach {
                verbose("Verifying $it")
                it.verifyDependency().also { hasDependency ->
                    showErrorLog(missingDependency = hasDependency.not(), optimizer = it)
                }
            }
            xmlOptimizers.forEach {
                verbose("Verifying $it")
                it.verifyDependency().also { hasDependency ->
                    showErrorLog(missingDependency = hasDependency.not(), optimizer = it)
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

        fun optimize(file: Path) {
            if (file.extension == ".svg") {
                output("🏎️  Optimizing SVG")
                printEmpty()
                svgOptimizers.forEach {
                    it.run(file)
                    printEmpty()
                }
            } else {
                output("⚠️ XML detected, skipping SVG optimization")
            }

            output("🏎️  Optimizing XML")
            xmlOptimizers.forEach {
                printEmpty()
                it.run(file)
                printEmpty()
            }
        }
    }
}
