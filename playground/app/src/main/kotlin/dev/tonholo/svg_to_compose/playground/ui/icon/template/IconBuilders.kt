package dev.tonholo.svg_to_compose.playground.ui.icon.template

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Builds an [ImageVector] with the given metadata.
 *
 * This is a thin wrapper around [ImageVector.Builder] that demonstrates
 * how a design system can provide its own icon builder DSL via the
 * template system.
 */
fun icon(
    name: String,
    viewportWidth: Float,
    viewportHeight: Float,
    block: ImageVector.Builder.() -> Unit,
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = viewportWidth.dp,
    defaultHeight = viewportHeight.dp,
    viewportWidth = viewportWidth,
    viewportHeight = viewportHeight,
).apply(block).build()

/**
 * Adds a path to the current [ImageVector.Builder].
 *
 * Wraps the standard [path] function, demonstrating how a design system
 * can provide a custom path builder with project-specific defaults.
 */
fun ImageVector.Builder.iconPath(
    fill: Brush? = null,
    fillAlpha: Float? = null,
    strokeAlpha: Float? = null,
    pathFillType: PathFillType? = null,
    block: PathBuilder.() -> Unit,
) {
    path(
        fill = fill,
        fillAlpha = fillAlpha ?: 1.0f,
        strokeAlpha = strokeAlpha ?: 1.0f,
        pathFillType = pathFillType ?: PathFillType.NonZero,
        pathBuilder = block,
    )
}
