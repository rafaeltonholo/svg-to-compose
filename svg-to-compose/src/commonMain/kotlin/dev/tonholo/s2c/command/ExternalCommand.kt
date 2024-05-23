package dev.tonholo.s2c.command

data class CommandOutput(
    val stdout: String?,
    val stderr: String? = null,
)

data class CommandResult(
    val exitCode: Int,
    val output: CommandOutput,
)

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
class CommandBuilder(
    private var program: String,
) {
    private var args: MutableList<String>? = null
    var showStdout: Boolean = true
    var showStderr: Boolean = true
    var trim: Boolean = false

    fun args(vararg args: String) {
        if (this.args == null) this.args = mutableListOf()

        this.args!!.addAll(args)
    }

    fun execute(): CommandResult = executeCommand(
        command = Command(
            program = program,
            args = args,
            showStdout = showStdout,
            showStderr = showStderr,
            trim = trim,
        )
    )
}

fun command(program: String, builder: CommandBuilder.() -> Unit): CommandResult =
    CommandBuilder(program = program).apply(builder).execute()

internal expect fun executeCommand(
    command: Command,
): CommandResult
