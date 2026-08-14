package dev.tonholo.s2c.remote.source

/**
 * A remote input that can be resolved into convertible icons.
 */
sealed interface RemoteSource {
    /**
     * Single SVG file at [url].
     */
    data class Url(val url: String) : RemoteSource

    /**
     * ZIP archive at [url] containing SVG/XML files.
     */
    data class Archive(val url: String) : RemoteSource

    /**
     * Font icon library with a glyph-to-name mapping.
     */
    sealed interface Font : RemoteSource {
        /**
         * CSS file at [cssUrl] containing `@font-face` and glyph class rules.
         */
        data class Css(val cssUrl: String) : Font

        /**
         * Font file at [fontUrl] plus a separate mapping file at [mappingUrl].
         */
        data class FileWithMapping(
            val fontUrl: String,
            val mappingUrl: String,
        ) : Font
    }
}
