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
        )
    }

    fun fromUploadedFile(file: UploadedFileInfo, baseOptions: PlaygroundOptions): ConversionInput {
        val filePackage = computeFilePackage(file.relativePath, baseOptions.pkg)
        val fileOptions = baseOptions.copy(pkg = filePackage)
        return create(
            svgContent = file.content,
            iconName = file.name
                .substringBeforeLast(".")
                .replaceFirstChar { it.uppercase() },
            fileType = file.detectedExtension,
            options = fileOptions,
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

    private fun create(
        svgContent: String,
        iconName: String,
        fileType: String,
        options: PlaygroundOptions,
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
    )
}
