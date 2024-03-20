package dev.tonholo.s2c.domain

/**
 * This enum class is used to specify different vector file formats.
 * Each enum represents a file type and holds related information consisting
 * of a tag and an extension.
 *
 * These represent file types, and their associated tag and extension values
 * can be accessed as properties of the enum.
 */
enum class FileType(val tag: String, val extension: String) {
    /**
     * Android Vector Graphic (AVG) file representation
     */
    Avg(tag = "vector", extension = ".xml"),

    /**
     * Scalable Vector Graphics (SVG) file representation
     */
    Svg(tag = "svg", extension = ".svg"),
}
