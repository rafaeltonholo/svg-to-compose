package dev.tonholo.s2c.website.worker

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.optimizer.ContentOptimizer
import dev.tonholo.s2c.optimizer.svgo.DefaultSvgoConfig
import dev.tonholo.s2c.optimizer.svgo.toBrowserConfig

@JsModule("svgo/browser")
@JsNonModule
private external object SvgoBrowser {
    @Suppress("UnusedParameter")
    fun optimize(input: String, config: dynamic): dynamic
}

/** [ContentOptimizer] that delegates SVG optimisation to SVGO's browser API. */
internal object JsContentOptimizer : ContentOptimizer {
    override suspend fun optimize(content: String, fileType: FileType): String = when (fileType) {
        FileType.Svg -> optimizeSvg(content)
        FileType.Avg -> content
    }

    private fun optimizeSvg(svgContent: String): String {
        val config = DefaultSvgoConfig.toBrowserConfig()
        val result = SvgoBrowser.optimize(svgContent, config)
        return result.data as String
    }
}
