package dev.tonholo.s2c.optimizer.svgo

internal val SvgoConfigContent = """
// A bit hacky here.
// We want to still convert shapes to path unless that shape
// has stroke-dasharray, but we don't want to implement the
// whole plugin again nor copy its code.
//
// The reason is that we don't support stroke-dasharray on Paths
// and it would be too much work for now to make that work.
//
// In that case, if we can import the default plugin from svgo
// module, we override the plugin with a custom implementation.
//
// It checks if the node has the stroke-dasharray attribute and
// if positive, we just ignore the shape to path conversion,
// otherwise, we delegate the conversion to the default plugin
// implementation.
import { createRequire } from "module";
const convertShapeToPathPluginName = "convertShapeToPath";
let overriddenPlugin = {
  name: convertShapeToPathPluginName,
  fn: () => {
    element: {
      enter: () => {
        // Ignore to avoid undesired results.
        return;
      };
    }
  },
};

try {
  const require = createRequire(import.meta.url);
  const pluginPath = `${'$'}{require.main.path}/../plugins/${'$'}{convertShapeToPathPluginName}.js`;
  const convertShapeToPath = require(pluginPath);
  overriddenPlugin = {
    name: convertShapeToPathPluginName,
    description:
      "Overrides the convertShapeToPath plugin by ignoring elements with stroke-dasharray",
    params: {
      convertArcs: true,
      floatPrecision: 2,
    },
    fn: (root, params) => {
      return {
        element: {
          enter: (node, parentNode) => {
            if (node.attributes["stroke-dasharray"]) {
              return;
            } else {
              const { element } = convertShapeToPath.fn(root, params);
              return element.enter(node, parentNode);
            }
          },
        },
      };
    },
  };
} catch {
  console.warn(
    "Could not import the default convertShapeToPath from svgo. " +
      "Removing it from the list of plugins to avoid undesired issues."
  );
}
const _shapes = ["rect", "circle", "line", "polygon", "polyline", "ellipse"];

export default {
  js2svg: { pretty: true },
  plugins: [
    {
      name: "preset-default",
      params: {
        overrides: {
          removeViewBox: false,
          convertShapeToPath: false,
        },
      },
    },
    {
      name: "convertPathData",
      params: {
        leadingZero: false,
        floatPrecision: 2,
      },
    },
    {
      name: "addDefaultFillToShapes",
      description: "Addes a default filling color for shapes",
      fn: () => {
        return {
          element: {
            enter: (node) => {
              const fill = node.attributes.fill;
              if (!fill && _shapes.indexOf(node.name) !== -1) {
                // by default, shapes has color black when not specified
                node.attributes.fill = "#000";
              }
            },
          },
        };
      },
    },
    overriddenPlugin,
  ],
};
""".trimIndent()
