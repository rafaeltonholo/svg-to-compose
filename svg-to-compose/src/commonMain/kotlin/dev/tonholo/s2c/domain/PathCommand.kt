package dev.tonholo.s2c.domain

import dev.tonholo.s2c.serializer.domain.PathCommandSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PathCommandSerializer::class)
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
    Close(
        value = 'z',
        size = 0,
        createNode = { _, _, _ ->
            error("Not expected command creation.")
        },
    ),
    ;

    fun uppercaseChar(): Char = value.uppercaseChar()
    override fun toString(): String = value.toString()

    companion object {
        const val ARC_TO_LARGE_ARC_POSITION = 3
        const val ARC_TO_SWEEP_FLAG_POSITION = 4
    }
}

/**
 * Removes the specified PathCommand's character value as a suffix from this
 * string.
 *
 * @param pathCommand The PathCommand whose character value will be removed
 *  from the end of this string if present
 * @return A new string with the PathCommand's character value removed from
 *  the end, or the original string if it doesn't end with that character
 */
internal fun String.removeSuffix(pathCommand: PathCommand): String = removeSuffix(pathCommand.value.toString())

/**
 * Converts a Char to its corresponding PathCommand enum value.
 *
 * This extension function maps lowercase path command characters to their
 * corresponding PathCommand enum values. The mapping includes all standard
 * SVG path commands such as move, line, curve, arc, and close operations.
 *
 * @return The PathCommand enum value corresponding to this character, or null
 * if the character does not match any known path command value.
 */
internal fun Char.toPathCommand(): PathCommand? = when (this) {
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
