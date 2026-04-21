package dev.tonholo.s2c.cli.output.tui

import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig

internal object TuiTestFixtures {
    val defaultParserConfig = ParserConfig(
        pkg = "com.example.icons",
        theme = "AppTheme",
        optimize = true,
        receiverType = null,
        addToMaterial = false,
        kmpPreview = false,
        noPreview = false,
        makeInternal = false,
        minified = false,
    )

    val defaultRunConfig = RunConfig(
        inputPath = "./ic_home.svg",
        outputPath = "./IcHome.kt",
        parserConfig = defaultParserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 1,
        recursive = false,
    )
}
