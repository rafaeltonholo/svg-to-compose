package dev.tonholo.s2c.command

import dev.tonholo.s2c.logger.Logger

data class CommandOutput(val stdout: String?, val stderr: String? = null)

data class CommandResult(val exitCode: Int, val output: CommandOutput)

data class Command(
    val program: String,
    val args: List<String>? = null,
    val showStdout: Boolean = true,
    val showStderr: Boolean = true,
    val trim: Boolean = false,
) {
    val commandToExecute: String
        get() {
            val args = if (args.isNullOrEmpty()) "" else " ${args.joinToString(" ")}"
            return "${program}$args"
        }
}

@DslMarker
annotation class CommandDsl

@CommandDsl
class CommandBuilder(private val logger: Logger, private var program: String) {
    private val args: MutableList<String> = mutableListOf()
    var showStdout: Boolean = true
    var showStderr: Boolean = true
    var trim: Boolean = false

    fun args(vararg args: String) {
        this.args.addAll(args.toList())
    }

    fun execute(): CommandResult = with(logger) {
        executeCommand(
            command = Command(
                program = program,
                args = args.ifEmpty { null },
                showStdout = showStdout,
                showStderr = showStderr,
                trim = trim,
            ),
        )
    }
}

context(logger: Logger)
fun command(program: String, builder: CommandBuilder.() -> Unit): CommandResult =
    CommandBuilder(logger = logger, program = program).apply(builder).execute()

context(logger: Logger)
internal expect fun executeCommand(command: Command): CommandResult
