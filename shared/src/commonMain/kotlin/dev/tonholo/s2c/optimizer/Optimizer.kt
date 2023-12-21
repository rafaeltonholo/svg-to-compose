package dev.tonholo.s2c.optimizer

import com.kgit2.process.Command
import com.kgit2.process.Stdio
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.MissingDependencyException
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose

sealed interface Optimizer {
    val command: String
    val allowedExtension: String
    val errorMessage: String
        get() = "⚠️ $command is required. Use npm -g install $command to install."

    fun verifyDependency() =
        Command("command")
            .args("-v", command)
            .stdout(Stdio.Pipe)
            .spawn()
            .wait()
            .also {
                verbose("exit code = ${it.code}")
            }
            .code == 0

    data object SvgoOptimizer : Optimizer {
        override val command: String = "svgo"
        override val allowedExtension: String = ".svg"
    }

    data object S2vOptimizer : Optimizer {
        override val command: String = "s2v"
        override val allowedExtension: String = ".svg"
    }

    data object AvocadoOptimizer : Optimizer {
        override val command: String = "avocado"
        override val allowedExtension: String = ".xml"
    }

    companion object {
        private val svgOptimizers = setOf(
            SvgoOptimizer,
            S2vOptimizer,
        )
        private val xmlOptimizers = setOf(
            AvocadoOptimizer,
        )
        val optimizers = svgOptimizers + xmlOptimizers

        fun verifyDependency(
            hasXml: Boolean,
        ) {
            var hasMissingDependency = false
            fun showErrorLog(missingDependency: Boolean, optimizer: Optimizer) {
                if (missingDependency) {
                    printEmpty()
                    output(optimizer.errorMessage)
                    hasMissingDependency = true
                }
            }

            svgOptimizers.forEach {
                it.verifyDependency().also { hasDependency ->
                    showErrorLog(missingDependency = hasDependency.not(), optimizer = it)
                }
            }
            if (hasXml) {
                xmlOptimizers.forEach {
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
    }
}

