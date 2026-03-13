package dev.tonholo.s2c.emitter.template

/**
 * Shared constants for the template placeholder system.
 *
 * These define the contract between `s2c.template.toml` schema
 * and the Kotlin resolution/emission code.
 *
 * Usage: `TemplateConstants.Namespace.ICON`, `TemplateConstants.Fragment.PATH_BUILDER`, etc.
 */
object TemplateConstants {
    /** Placeholder namespace identifiers used in `${namespace:key}` syntax. */
    object Namespace {
        const val ICON = "icon"
        const val PATH = "path"
        const val GROUP = "group"
        const val TEMPLATE = "template"
        const val DEFINITIONS = "def"
        const val CHUNK = "chunk"
    }

    /** Well-known fragment names referenced in template configs. */
    object Fragment {
        const val PATH_BUILDER = "path_builder"
        const val GROUP_BUILDER = "group_builder"
        const val CHUNK_FUNCTION_NAME = "chunk_function_name"
        const val CHUNK_FUNCTION_DEFINITION = "chunk_function_definition"
    }

    /** Variable keys in the `${icon:*}` namespace. */
    object IconVar {
        const val NAME = "name"
        const val RECEIVER = "receiver"
        const val THEME = "theme"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val VIEWPORT_WIDTH = "viewport_width"
        const val VIEWPORT_HEIGHT = "viewport_height"
        const val BODY = "body"
        const val PACKAGE = "package"
        const val PROPERTY_NAME = "property_name"
        const val VISIBILITY = "visibility"
    }

    /** Variable keys in the `${path:*}` namespace. */
    object PathVar {
        const val FILL = "fill"
        const val FILL_ALPHA = "fill_alpha"
        const val FILL_TYPE = "fill_type"
        const val STROKE = "stroke"
        const val STROKE_ALPHA = "stroke_alpha"
        const val STROKE_LINE_CAP = "stroke_line_cap"
        const val STROKE_LINE_JOIN = "stroke_line_join"
        const val STROKE_MITER_LIMIT = "stroke_miter_limit"
        const val STROKE_LINE_WIDTH = "stroke_line_width"
    }

    /** Variable keys in the `${group:*}` namespace. */
    object GroupVar {
        const val ROTATE = "rotate"
        const val PIVOT_X = "pivot_x"
        const val PIVOT_Y = "pivot_y"
        const val SCALE_X = "scale_x"
        const val SCALE_Y = "scale_y"
        const val TRANSLATION_X = "translation_x"
        const val TRANSLATION_Y = "translation_y"
    }

    /** Variable keys used in the `${chunk:*}` namespace. */
    object ChunkVar {
        const val NAME = "name"
        const val BODY = "body"
        const val INDEX = "index"
    }
}
