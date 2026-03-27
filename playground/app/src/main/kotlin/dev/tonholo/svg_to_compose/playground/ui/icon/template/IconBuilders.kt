package dev.tonholo.svg_to_compose.playground.ui.icon.template

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.DefaultGroupName
import androidx.compose.ui.graphics.vector.DefaultPivotX
import androidx.compose.ui.graphics.vector.DefaultPivotY
import androidx.compose.ui.graphics.vector.DefaultRotation
import androidx.compose.ui.graphics.vector.DefaultScaleX
import androidx.compose.ui.graphics.vector.DefaultScaleY
import androidx.compose.ui.graphics.vector.DefaultTranslationX
import androidx.compose.ui.graphics.vector.DefaultTranslationY
import androidx.compose.ui.graphics.vector.EmptyPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.group
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

fun ImageVector.Builder.iconGroup(
    rotate: Float = DefaultRotation,
    pivotX: Float = DefaultPivotX,
    pivotY: Float = DefaultPivotY,
    scaleX: Float = DefaultScaleX,
    scaleY: Float = DefaultScaleY,
    translationX: Float = DefaultTranslationX,
    translationY: Float = DefaultTranslationY,
    clipPathData: List<PathNode> = EmptyPath,
    block: ImageVector.Builder.() -> Unit,
) = group(
    rotate = rotate,
    pivotX = pivotX,
    pivotY = pivotY,
    scaleX = scaleX,
    scaleY = scaleY,
    translationX = translationX,
    translationY = translationY,
    clipPathData = clipPathData,
    block = block,
)
