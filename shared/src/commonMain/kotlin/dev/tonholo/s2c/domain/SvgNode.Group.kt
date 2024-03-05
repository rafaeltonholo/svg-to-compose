package dev.tonholo.s2c.domain

fun SvgNode.Group.asNode(
    width: Float,
    height: Float,
    masks: List<SvgNode.Mask>,
    minified: Boolean = false,
): ImageVectorNode.Group {
    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val clipPath = masks.firstOrNull {
        it.id == maskId?.removePrefix("url(#")?.removeSuffix(")")
    }?.paths?.first()?.d?.asNodeWrapper(minified)

    return ImageVectorNode.Group(
        clipPath = clipPath,
        commands = commands.mapNotNull { it.asNode(width, height, masks, minified) },
        minified = minified,
    )
}
