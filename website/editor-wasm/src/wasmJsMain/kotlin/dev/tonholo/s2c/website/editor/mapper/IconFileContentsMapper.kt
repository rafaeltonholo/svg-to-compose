package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode

/**
 * Converts the parsed [IconFileContents] domain model into a Compose [ImageVector]
 * that can be rendered on a WASM canvas.
 */
fun IconFileContents.toImageVector(): ImageVector {
    val builder = ImageVector.Builder(
        defaultWidth = width.dp,
        defaultHeight = height.dp,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight,
    )
    nodes.forEach { node -> builder.addNode(node) }
    return builder.build()
}

/** Dispatches a single [ImageVectorNode] to the appropriate builder method. */
private fun ImageVector.Builder.addNode(node: ImageVectorNode) {
    when (node) {
        is ImageVectorNode.Path -> addPath(node)
        is ImageVectorNode.Group -> addGroup(node)
        is ImageVectorNode.ChunkFunction -> node.nodes.forEach { addNode(it) }
    }
}

/** Adds a vector path with its fill, stroke, and path data to the builder. */
private fun ImageVector.Builder.addPath(node: ImageVectorNode.Path) {
    val params = node.params
    path(
        fill = params.fill?.toBrush(),
        fillAlpha = params.fillAlpha ?: 1.0f,
        stroke = params.stroke?.toBrush(),
        strokeAlpha = params.strokeAlpha ?: 1.0f,
        strokeLineWidth = params.strokeLineWidth ?: 0.0f,
        strokeLineCap = params.strokeLineCap?.toStrokeCap() ?: StrokeCap.Butt,
        strokeLineJoin = params.strokeLineJoin?.toStrokeJoin() ?: StrokeJoin.Miter,
        strokeLineMiter = params.strokeMiterLimit ?: 4.0f,
        pathFillType = params.pathFillType?.toPathFillType() ?: PathFillType.NonZero,
    ) {
        node.wrapper.nodes.forEach { pathNode -> applyPathNode(pathNode) }
    }
}

/** Adds a vector group with transform parameters and nested children to the builder. */
private fun ImageVector.Builder.addGroup(node: ImageVectorNode.Group) {
    val params = node.params
    group(
        rotate = params.rotate ?: 0.0f,
        pivotX = params.pivotX ?: 0.0f,
        pivotY = params.pivotY ?: 0.0f,
        scaleX = params.scaleX ?: 1.0f,
        scaleY = params.scaleY ?: 1.0f,
        translationX = params.translationX ?: 0.0f,
        translationY = params.translationY ?: 0.0f,
        clipPathData = params.clipPath?.let { clipWrapper ->
            val clipBuilder = PathBuilder()
            clipWrapper.nodes.forEach { clipBuilder.applyPathNode(it) }
            clipBuilder.nodes
        } ?: emptyList(),
    ) {
        node.commands.forEach { addNode(it) }
    }
}
