package dev.tonholo.s2c.website.zip

import dev.tonholo.s2c.website.state.playground.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.state.playground.detectExtension
import kotlinx.coroutines.await
import org.w3c.files.File

/** Maximum zip file size (in bytes) before a warning is shown. */
private const val MAX_ZIP_SIZE_BYTES = 50 * 1024 * 1024 // 50 MB

/** Maximum number of matching files before a warning is shown. */
private const val MAX_FILE_COUNT = 200

/** Result of checking zip soft limits before extraction. */
internal sealed interface ZipLimitCheck {
    /** No limits exceeded; proceed with extraction. */
    data object Ok : ZipLimitCheck

    /** Zip file exceeds the 50 MB soft limit. */
    data class SizeWarning(val sizeBytes: Double) : ZipLimitCheck

    /** Matching file count exceeds the 200 file soft limit. */
    data class FileCountWarning(val count: Int) : ZipLimitCheck
}

/**
 * Checks whether the zip [file] exceeds the size soft limit.
 */
internal fun checkZipSizeLimit(file: File): ZipLimitCheck = if (file.size.toDouble() > MAX_ZIP_SIZE_BYTES) {
    ZipLimitCheck.SizeWarning(sizeBytes = file.size.toDouble())
} else {
    ZipLimitCheck.Ok
}

/**
 * Holds a parsed zip with its matching entries, ready for the
 * file-count check before full content extraction.
 */
internal class ParsedZip(val matchingEntries: List<ZipEntry>) {
    internal class ZipEntry(val relativePath: String, val extension: String, val zipObject: JSZipObject)
}

/**
 * Parses the zip and filters to `.svg` and `.xml` entries (excluding
 * directories). Does NOT read file contents yet — that happens in
 * [extractFilesFromParsedZip].
 *
 * `loadAsync` accepts `File` directly (File extends Blob).
 *
 * @throws IllegalStateException if the zip is corrupt or cannot be read.
 */
internal suspend fun parseZipEntries(file: File): ParsedZip {
    val zip = try {
        JSZip().loadAsync(file).await()
    } catch (
        @Suppress("TooGenericExceptionCaught") e: Throwable,
    ) {
        console.error("JSZip loadAsync failed:", e)
        error(
            "The zip file could not be read. " +
                "It may be corrupted or password-protected.",
        )
    }

    val entries = mutableListOf<ParsedZip.ZipEntry>()
    zip.forEach { relativePath, zipObject ->
        if (zipObject.dir) return@forEach
        val lowerName = relativePath.lowercase()
        val extension = when {
            lowerName.endsWith(".svg") -> "svg"
            lowerName.endsWith(".xml") -> "xml"
            else -> return@forEach
        }
        entries.add(
            ParsedZip.ZipEntry(relativePath, extension, zipObject),
        )
    }
    return ParsedZip(entries)
}

/**
 * Checks whether the [parsedZip] exceeds the file-count soft limit.
 */
internal fun checkZipFileCountLimit(parsedZip: ParsedZip): ZipLimitCheck =
    if (parsedZip.matchingEntries.size > MAX_FILE_COUNT) {
        ZipLimitCheck.FileCountWarning(
            count = parsedZip.matchingEntries.size,
        )
    } else {
        ZipLimitCheck.Ok
    }

/**
 * Successfully parsed files plus error entries for files that
 * failed to read from the zip.
 */
internal class ZipExtractionResult(val files: List<UploadedFileInfo>, val errors: List<BatchConversionResult>)

/**
 * Reads matching entries from a [parsedZip] and produces
 * [UploadedFileInfo] objects ready for batch conversion.
 *
 * `.xml` files are validated via [detectExtension] — only entries whose
 * root element is `<vector>` (AVG) are included. Entries that fail to
 * read are returned as error [BatchConversionResult] entries, consistent
 * with how batch conversion handles per-file errors.
 */
internal suspend fun extractFilesFromParsedZip(parsedZip: ParsedZip): ZipExtractionResult {
    val files = mutableListOf<UploadedFileInfo>()
    val errors = mutableListOf<BatchConversionResult>()

    for (entry in parsedZip.matchingEntries) {
        val pathParts = entry.relativePath.split("/")
        val fileName = pathParts.last()
        val relativePath = if (pathParts.size > 1) {
            pathParts.dropLast(1).joinToString("/")
        } else {
            ""
        }

        val content = try {
            entry.zipObject.async<String>("string").await()
        } catch (_: Throwable) {
            errors.add(
                BatchConversionResult(
                    fileName = fileName,
                    originalContent = "",
                    detectedExtension = entry.extension,
                    relativePath = relativePath,
                    error = "Failed to read entry from zip archive.",
                ),
            )
            continue
        }

        val detectedExt = when (entry.extension) {
            "svg" -> detectExtension(content) ?: "svg"

            "xml" -> {
                val detected = detectExtension(content)
                if (detected != "avg") continue
                detected
            }

            else -> continue
        }

        files.add(
            UploadedFileInfo(
                name = fileName,
                content = content,
                detectedExtension = detectedExt,
                relativePath = relativePath,
            ),
        )
    }
    return ZipExtractionResult(files, errors)
}
