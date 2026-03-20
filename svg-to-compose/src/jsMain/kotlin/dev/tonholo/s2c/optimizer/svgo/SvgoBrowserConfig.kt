package dev.tonholo.s2c.optimizer.svgo

/**
 * Renders this [SvgoPluginConfig] as a browser-compatible dynamic JS object
 * for the `svgo/browser` API.
 *
 * [SvgoPlugin.OverrideConvertShapeToPath] is skipped because it requires
 * Node.js `createRequire` which is unavailable in browser environments.
 */
fun SvgoPluginConfig.toBrowserConfig(): dynamic {
    val config = js("{}")
    config.js2svg = js("{}")
    config.js2svg.pretty = js2svg.pretty

    val pluginArray = js("[]")
    for (plugin in plugins) {
        val jsPlugin = plugin.toBrowserPlugin() ?: continue
        pluginArray.push(jsPlugin)
    }
    config.plugins = pluginArray
    return config
}

/** Converts a single [SvgoPlugin] to a browser-compatible dynamic JS object, or `null` if unsupported. */
private fun SvgoPlugin.toBrowserPlugin(): dynamic? = when (this) {
    is SvgoPlugin.PresetDefault -> buildPresetDefault()
    is SvgoPlugin.ConvertPathData -> buildConvertPathData()
    is SvgoPlugin.AddDefaultFillToShapes -> buildAddDefaultFill()
    is SvgoPlugin.OverrideConvertShapeToPath -> null // requires Node.js createRequire
}

/** Builds the `preset-default` plugin configuration as a dynamic JS object. */
private fun SvgoPlugin.PresetDefault.buildPresetDefault(): dynamic {
    val plugin = js("{}")
    plugin.name = name
    val params = js("{}")
    val overridesObj = js("{}")
    for ((key, value) in overrides) {
        overridesObj[key] = value
    }
    params.overrides = overridesObj
    plugin.params = params
    return plugin
}

/** Builds the `convertPathData` plugin configuration as a dynamic JS object. */
private fun SvgoPlugin.ConvertPathData.buildConvertPathData(): dynamic {
    val plugin = js("{}")
    plugin.name = name
    val params = js("{}")
    params.leadingZero = leadingZero
    params.floatPrecision = floatPrecision
    plugin.params = params
    return plugin
}

/** Builds the `addDefaultFill` custom plugin configuration as a dynamic JS object. */
private fun SvgoPlugin.AddDefaultFillToShapes.buildAddDefaultFill(): dynamic {
    val targetShapes = shapes.toTypedArray()
    val plugin = js("{}")
    plugin.name = name
    plugin.description = description
    plugin.fn = fun(): dynamic {
        val result = js("{}")
        val element = js("{}")
        element.enter = fun(node: dynamic) {
            val fill = node.attributes.fill
            if (fill == null || fill == js("undefined")) {
                val nodeName = node.name as? String ?: return
                if (nodeName in targetShapes) {
                    node.attributes.fill = "#000"
                }
            }
        }
        result.element = element
        return result
    }
    return plugin
}
