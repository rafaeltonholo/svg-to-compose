package dev.tonholo.s2c.domain

enum class PathCommand(
    val value: Char,
    val size: Int,
    val createNode: (
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) -> PathNodes,
) {
    ArcTo(value = 'a', size = 7, createNode = PathNodes::ArcTo),
    CurveTo(value = 'c', size = 6, createNode = PathNodes::CurveTo),
    HorizontalLineTo(value = 'h', size = 1, createNode = PathNodes::HorizontalLineTo),
    LineTo(value = 'l', size = 2, createNode = PathNodes::LineTo),
    MoveTo(value = 'm', size = 2, createNode = PathNodes::MoveTo),
    QuadTo(value = 'q', size = 4, createNode = PathNodes::QuadTo),
    ReflectiveCurveTo(value = 's', size = 4, createNode = PathNodes::ReflectiveCurveTo),
    ReflectiveQuadTo(value = 't', size = 2, createNode = PathNodes::ReflectiveQuadTo),
    VerticalLineTo(value = 'v', size = 1, createNode = PathNodes::VerticalLineTo),
    Close(value = 'z', size = 0, createNode = { _, _, _ ->
        error("Not expected command creation.")
    }),
    ;

    fun uppercaseChar(): Char = value.uppercaseChar()
    override fun toString(): String = value.toString()

    companion object {
        const val ARC_TO_LARGE_ARC_POSITION = 3
        const val ARC_TO_SWEEP_FLAG_POSITION = 4
    }
}

inline fun String.removeSuffix(pathCommand: PathCommand): String =
    removeSuffix(pathCommand.value.toString())

inline fun Char.toPathCommand(): PathCommand? {
    return when (this) {
        PathCommand.ArcTo.value -> PathCommand.ArcTo
        PathCommand.CurveTo.value -> PathCommand.CurveTo
        PathCommand.HorizontalLineTo.value -> PathCommand.HorizontalLineTo
        PathCommand.LineTo.value -> PathCommand.LineTo
        PathCommand.MoveTo.value -> PathCommand.MoveTo
        PathCommand.QuadTo.value -> PathCommand.QuadTo
        PathCommand.ReflectiveCurveTo.value -> PathCommand.ReflectiveCurveTo
        PathCommand.ReflectiveQuadTo.value -> PathCommand.ReflectiveQuadTo
        PathCommand.VerticalLineTo.value -> PathCommand.VerticalLineTo
        PathCommand.Close.value -> PathCommand.Close
        else -> null
    }
}
