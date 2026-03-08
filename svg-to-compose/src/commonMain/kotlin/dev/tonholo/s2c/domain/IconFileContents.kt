package dev.tonholo.s2c.domain

import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.logger.NoOpLogger

val defaultImports = setOf(
    "androidx.compose.ui.graphics.vector.ImageVector",
    "androidx.compose.ui.unit.dp",
)

val previewComponentsImports = setOf(
    "androidx.compose.foundation.Image",
    "androidx.compose.foundation.layout.Arrangement",
    "androidx.compose.foundation.layout.Column",
    "androidx.compose.foundation.layout.height",
    "androidx.compose.foundation.layout.width",
    "androidx.compose.runtime.Composable",
    "androidx.compose.ui.Alignment",
    "androidx.compose.ui.Modifier",
)
val androidPreviewImports = buildSet {
    addAll(previewComponentsImports)
    add("androidx.compose.ui.tooling.preview.Preview")
}
val kmpPreviewImports = buildSet {
    addAll(previewComponentsImports)
    add("org.jetbrains.compose.ui.tooling.preview.Preview")
}

val materialReceiverTypeImport = setOf(
    "androidx.compose.material.icons.Icons"
)

/**
 * Represents the contents of an icon file, including its package, icon name,
 * theme, dimensions, nodes, and other attributes.
 *
 * @property pkg The package name of the icon.
 * @property iconName The name of the icon.
 * @property theme The theme of the Compose application.
 * @property width The width of the icon in density-independent pixels.
 * @property height The height of the icon in density-independent pixels.
 * @property viewportWidth The width of the viewport in floating-point units.
 * @property viewportHeight The height of the viewport in floating-point units.
 * @property nodes The list of nodes that make up the icon.
 * @property receiverType The type of the receiver object for the icon, if any.
 * @property addToMaterial Whether to add the icon to the `Icons.Filled` object.
 * @property noPreview Whether to disable the preview for the icon.
 * @property makeInternal Whether to make the icon internal.
 * @property imports The set of imports to include in the generated code.
 */
data class IconFileContents(
    val pkg: String,
    val iconName: String,
    val theme: String,
    val width: Float,
    val height: Float,
    val viewportWidth: Float = width,
    val viewportHeight: Float = height,
    val nodes: List<ImageVectorNode>,
    val receiverType: String? = null,
    val addToMaterial: Boolean = false,
    val noPreview: Boolean = false,
    val makeInternal: Boolean = false,
    val imports: Set<String> = defaultImports,
) {
    /**
     * Generates the Kotlin code for the icon.
     *
     * @return The generated Kotlin code.
     */
    @Deprecated(
        message = "Use CodeEmitter.emit() instead.",
        replaceWith = ReplaceWith(
            expression = "ImageVectorEmitter(logger).emit(this)",
            imports = ["dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter"],
        ),
    )
    @Suppress("DeprecatedCallableAddReplaceWith")
    fun materialize(): String = ImageVectorEmitter(NoOpLogger).emit(this)
}
