package dev.tonholo.s2c.website.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.dom.events.Event
import org.w3c.files.File
import kotlin.coroutines.resume

/**
 * Processes a native drop [Event], collecting files and directory entries
 * synchronously (before DataTransfer is cleared), then reading directory
 * contents asynchronously.
 */
fun handleDrop(event: Event, scope: CoroutineScope, onSelectFiles: (List<File>, Map<String, String>) -> Unit) {
    val (entries, files) = collectDroppedItems(event)
    dispatchDroppedItems(entries, files, scope, onSelectFiles)
}

private data class DroppedItems(val entries: List<dynamic>, val files: List<File>)

private fun collectDroppedItems(event: Event): DroppedItems {
    val dt = event.asDynamic().dataTransfer
    val entries = mutableListOf<dynamic>()
    val files = mutableListOf<File>()
    val items = dt?.items
    if (items != null) {
        collectFromItems(items, entries, files)
    } else {
        collectFromFileList(dt?.files, files)
    }
    return DroppedItems(entries, files)
}

private fun collectFromItems(items: dynamic, entries: MutableList<dynamic>, files: MutableList<File>) {
    for (i in 0 until (items.length as Int)) {
        val item = items[i]
        val entry: dynamic = try {
            item.webkitGetAsEntry()
        } catch (_: Throwable) {
            null
        }
        if (entry != null) {
            entries.add(entry)
        } else {
            (item.getAsFile() as? File)?.let { files.add(it) }
        }
    }
}

private fun collectFromFileList(fileList: dynamic, files: MutableList<File>) {
    fileList ?: return
    for (i in 0 until (fileList.length as Int)) {
        (fileList[i] as? File)?.let { files.add(it) }
    }
}

private fun dispatchDroppedItems(
    entries: List<dynamic>,
    files: List<File>,
    scope: CoroutineScope,
    onSelectFiles: (List<File>, Map<String, String>) -> Unit,
) {
    if (entries.isNotEmpty()) {
        val firstFullPath = entries[0].fullPath as? String ?: ""
        val rootPath = if (entries[0].isDirectory as Boolean) {
            firstFullPath
        } else {
            firstFullPath.substringBeforeLast("/", "")
        }
        scope.launch {
            val allFiles = files.toMutableList()
            val filePaths = mutableMapOf<String, String>()
            for (entry in entries) readEntry(entry, allFiles, filePaths, rootPath)
            if (allFiles.isNotEmpty()) onSelectFiles(allFiles, filePaths)
        }
    } else if (files.isNotEmpty()) {
        onSelectFiles(files, emptyMap())
    }
}

/**
 * Recursively reads a FileSystemEntry, collecting files and their
 * relative paths from the drop root.
 */
suspend fun readEntry(entry: dynamic, files: MutableList<File>, paths: MutableMap<String, String>, rootPath: String) {
    if (entry.isFile as Boolean) {
        val file = suspendCancellableCoroutine<File?> { cont ->
            entry.file({ f: File -> cont.resume(f) })
        }
        if (file != null) {
            files.add(file)
            val fullPath = entry.fullPath as? String ?: ""
            val dir = fullPath.substringBeforeLast("/", "")
                .removePrefix(rootPath)
                .removePrefix("/")
            paths[file.name] = dir
        }
    } else if (entry.isDirectory as Boolean) {
        val reader = entry.createReader()
        val dirEntries = suspendCancellableCoroutine<Array<dynamic>> { cont ->
            reader.readEntries({ results: Array<dynamic> -> cont.resume(results) })
        }
        for (child in dirEntries) readEntry(child, files, paths, rootPath)
    }
}
