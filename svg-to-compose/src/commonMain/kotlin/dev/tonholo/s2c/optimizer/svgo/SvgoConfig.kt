package dev.tonholo.s2c.optimizer.svgo

/**
 * Renders this [SvgoPluginConfig] as an ESM module string suitable for
 * the `svgo` CLI's `--config` flag.
 *
 * The [SvgoPlugin.OverrideConvertShapeToPath] plugin requires Node.js
 * `createRequire` to dynamically load SVGO's built-in `convertShapeToPath`
 * at runtime and wrap it with a `stroke-dasharray` guard. This Node.js-specific
 * JS glue code is emitted as part of the module preamble.
 */
@Suppress("LongMethod")
fun SvgoPluginConfig.toEsmModuleContent(): String = buildString {
    val shapesPlugin = plugins.filterIsInstance<SvgoPlugin.AddDefaultFillToShapes>().first()
    val overridePlugin = plugins.filterIsInstance<SvgoPlugin.OverrideConvertShapeToPath>().firstOrNull()

    // Node.js preamble for the convertShapeToPath override plugin.
    if (overridePlugin != null) {
        appendLine(
            // language=js
            """
            |// A bit hacky here.
            |// We want to still convert shapes to path unless that shape
            |// has stroke-dasharray, but we don't want to implement the
            |// whole plugin again nor copy its code.
            |//
            |// The reason is that we don't support stroke-dasharray on Paths
            |// and it would be too much work for now to make that work.
            |//
            |// In that case, if we can import the default plugin from svgo
            |// module, we override the plugin with a custom implementation.
            |//
            |// It checks if the node has the stroke-dasharray attribute and
            |// if positive, we just ignore the shape to path conversion,
            |// otherwise, we delegate the conversion to the default plugin
            |// implementation.
            |import { createRequire } from "module";
            |const convertShapeToPathPluginName = "${overridePlugin.name}";
            |let overriddenPlugin = {
            |  name: convertShapeToPathPluginName,
            |  fn: () => {
            |    element: {
            |      enter: () => {
            |        // Ignore to avoid undesired results.
            |        return;
            |      };
            |    }
            |  },
            |};
            |
            |try {
            |  const require = createRequire(import.meta.url);
            """.trimMargin(),
        )
        @Suppress("MaxLineLength")
        appendLine(
            // language=js
            $$"""
            |  const pluginPath = `${require.main.path}/../plugins/${convertShapeToPathPluginName}.js`;
            """.trimMargin(),
        )
        appendLine(
            // language=js
            """
            |  const convertShapeToPath = require(pluginPath);
            |  overriddenPlugin = {
            |    name: convertShapeToPathPluginName,
            |    description:
            |      "${overridePlugin.description}",
            |    params: {
            |      convertArcs: ${overridePlugin.convertArcs},
            |      floatPrecision: ${overridePlugin.floatPrecision},
            |    },
            |    fn: (root, params) => {
            |      return {
            |        element: {
            |          enter: (node, parentNode) => {
            |            if (node.attributes["stroke-dasharray"]) {
            |              return;
            |            } else {
            |              const { element } = convertShapeToPath.fn(root, params);
            |              return element.enter(node, parentNode);
            |            }
            |          },
            |        },
            |      };
            |    },
            |  };
            |} catch {
            |  console.warn(
            |    "Could not import the default convertShapeToPath from svgo. " +
            |      "Removing it from the list of plugins to avoid undesired issues."
            |  );
            |}
            """.trimMargin(),
        )
    }

    // Shapes constant for the addDefaultFillToShapes plugin.
    val shapesArray = shapesPlugin.shapes.joinToString(", ") { "\"$it\"" }
    appendLine("const _shapes = [$shapesArray];")
    appendLine()

    // Export default config object.
    appendLine("export default {")
    appendLine("  js2svg: { pretty: ${js2svg.pretty} },")
    appendLine("  plugins: [")
    for (plugin in plugins) {
        emitPlugin(plugin)
    }
    appendLine("  ],")
    append("};")
}

private fun StringBuilder.emitPlugin(plugin: SvgoPlugin) {
    when (plugin) {
        is SvgoPlugin.PresetDefault -> {
            appendLine("    {")
            appendLine("      name: \"${plugin.name}\",")
            appendLine("      params: {")
            appendLine("        overrides: {")
            for ((key, value) in plugin.overrides) {
                appendLine("          $key: $value,")
            }
            appendLine("        },")
            appendLine("      },")
            appendLine("    },")
        }

        is SvgoPlugin.ConvertPathData -> {
            appendLine(
                """
                |    {
                |      name: "${plugin.name}",
                |      params: {
                |        leadingZero: ${plugin.leadingZero},
                |        floatPrecision: ${plugin.floatPrecision},
                |      },
                |    },
                """.trimMargin(),
            )
        }

        is SvgoPlugin.AddDefaultFillToShapes -> {
            appendLine(
                """
                |    {
                |      name: "${plugin.name}",
                |      description: "${plugin.description}",
                |      fn: () => {
                |        return {
                |          element: {
                |            enter: (node) => {
                |              const fill = node.attributes.fill;
                |              if (!fill && _shapes.indexOf(node.name) !== -1) {
                |                // by default, shapes has color black when not specified
                |                node.attributes.fill = "#000";
                |              }
                |            },
                |          },
                |        };
                |      },
                |    },
                """.trimMargin(),
            )
        }

        is SvgoPlugin.OverrideConvertShapeToPath -> {
            appendLine("    overriddenPlugin,")
        }
    }
}

internal val SvgoConfigContent = DefaultSvgoConfig.toEsmModuleContent()
