package dev.tonholo.s2c.website.state.playground

import dev.tonholo.s2c.website.worker.ConversionInput

/**
 * Creates [ConversionInput] instances from playground state, handling
 * per-file package computation for batch conversions.
 */
internal object ConversionInputFactory {
    fun fromState(state: PlaygroundState): ConversionInput {
        val iconName = state.outputFileName.substringBeforeLast(".")
        return create(
            svgContent = state.inputCode,
            iconName = iconName,
            fileType = state.extension,
            options = state.options,
            templateToml = state.templateToml.takeIfUsable(),
        )
    }

    fun fromUploadedFile(
        file: UploadedFileInfo,
        baseOptions: PlaygroundOptions,
        templateToml: String? = null,
    ): ConversionInput {
        val filePackage = computeFilePackage(file.relativePath, baseOptions.pkg)
        val fileOptions = baseOptions.copy(pkg = filePackage)
        return create(
            svgContent = file.content,
            iconName = file.name
                .substringBeforeLast(".")
                .replaceFirstChar { it.uppercase() },
            fileType = file.detectedExtension,
            options = fileOptions,
            templateToml = templateToml,
        )
    }

    private fun computeFilePackage(relativePath: String, basePackage: String): String {
        if (relativePath.isEmpty() || basePackage.isEmpty()) return basePackage
        val subPkg = relativePath
            .replace("/", ".")
            .replace("-", "_")
            .lowercase()
        return "$basePackage.$subPkg"
    }

    /**
     * Returns the TOML string only when it contains real configuration
     * beyond section headers and comments (the default placeholder).
     */
    fun String.takeIfUsable(): String? {
        val trimmed = trim()
        if (trimmed.isEmpty()) return null
        val hasContent = trimmed.lines().any { line ->
            val l = line.trim()
            l.isNotEmpty() && !l.startsWith("#") && !l.startsWith("[")
        }
        return if (hasContent) this else null
    }

    private fun create(
        svgContent: String,
        iconName: String,
        fileType: String,
        options: PlaygroundOptions,
        templateToml: String? = null,
    ): ConversionInput = ConversionInput(
        svgContent = svgContent,
        iconName = iconName,
        fileType = fileType,
        optimize = options.optimize,
        pkg = options.pkg,
        theme = options.theme,
        noPreview = options.noPreview,
        makeInternal = options.makeInternal,
        minified = options.minified,
        kmpPreview = options.kmpPreview,
        receiverType = options.receiverType,
        templateToml = templateToml,
    )
}
