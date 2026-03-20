package dev.tonholo.s2c.website.zip

import org.w3c.files.Blob
import kotlin.js.Promise

/**
 * Kotlin/JS external declarations for the JSZip library.
 *
 * Only the API surface needed by this project is declared:
 * - [loadAsync]: read a zip from a [Blob] (or [File][org.w3c.files.File],
 *   which extends [Blob])
 * - [file]: add a text entry for zip creation
 * - [generateAsync]: produce the zip as a [Blob] for download
 * - [forEach]: iterate over entries after loading
 *
 * `@JsModule` is on the class (not file-level) because JSZip's UMD
 * export IS the constructor — `require("jszip")` returns the class
 * directly, not a namespace object.
 *
 * @see <a href="https://stuk.github.io/jszip/">JSZip documentation</a>
 */
@JsModule("jszip")
@JsNonModule
external class JSZip {
    /**
     * Reads an existing zip and populates this instance with its entries.
     *
     * Accepts [Blob], [org.w3c.files.File], `ArrayBuffer`, or `Uint8Array`.
     * Since [org.w3c.files.File] extends [Blob], callers can pass a `File`
     * directly.
     */
    fun loadAsync(data: Blob): Promise<JSZip>

    /**
     * Adds a text file entry to the archive.
     * @param name File path within the archive (e.g. `"icons/Heart.svg"`).
     * @param content The file content as a string.
     */
    fun file(name: String, content: String): JSZip

    /**
     * Generates the complete zip archive.
     *
     * Pass `js("({type:'blob'})")` as [options] to get a [Blob] back.
     * Compression defaults to `STORE` (no compression), which is optimal
     * for download speed since the generated Kotlin files are small text.
     */
    fun generateAsync(options: dynamic): Promise<Blob>

    /**
     * Iterates over every entry (files and directories) in this archive.
     * @param callback Receives the relative path and [JSZipObject] for
     *   each entry.
     */
    fun forEach(callback: (relativePath: String, file: JSZipObject) -> Unit)
}

/**
 * A single entry (file or directory) inside a [JSZip] archive.
 */
external interface JSZipObject {
    /** `true` if this entry is a directory. */
    val dir: Boolean

    /**
     * Reads the entry content asynchronously.
     * @param type Output format: `"string"`, `"blob"`, `"arraybuffer"`,
     *   `"uint8array"`, etc.
     */
    fun <T> async(type: String): Promise<T>
}
