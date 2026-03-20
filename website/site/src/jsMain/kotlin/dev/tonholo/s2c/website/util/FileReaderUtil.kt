package dev.tonholo.s2c.website.util

import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Reads the given browser [File] as UTF-8 text using the FileReader API.
 */
suspend fun readFileAsText(file: File): String = suspendCancellableCoroutine { cont ->
    val reader = FileReader()
    reader.onload = {
        cont.resume(reader.result as String)
    }
    reader.onerror = {
        cont.resumeWithException(
            IllegalStateException("Failed to read file: ${file.name}"),
        )
    }
    reader.readAsText(file)
    cont.invokeOnCancellation {
        reader.abort()
    }
}
