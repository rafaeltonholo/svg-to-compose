package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundViewModel
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.state.playground.detectExtension
import dev.tonholo.s2c.website.util.readFileAsText
import dev.tonholo.s2c.website.zip.ZipLimitCheck
import dev.tonholo.s2c.website.zip.checkZipFileCountLimit
import dev.tonholo.s2c.website.zip.checkZipSizeLimit
import dev.tonholo.s2c.website.zip.extractFilesFromParsedZip
import dev.tonholo.s2c.website.zip.parseZipEntries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.w3c.files.File

@Composable
internal fun rememberFileSelectedHandler(
    scope: CoroutineScope,
    vm: PlaygroundViewModel,
): (List<File>, Map<String, String>) -> Unit = remember(scope, vm) {
    { files: List<File>, paths: Map<String, String> ->
        val zipFile = files.firstOrNull { file ->
            file.name.endsWith(".zip", ignoreCase = true)
        }
        if (zipFile != null) {
            scope.launch {
                when (val result = processZipUpload(zipFile)) {
                    is ZipUploadResult.Success ->
                        vm.loadFiles(result.files)

                    is ZipUploadResult.Error ->
                        vm.dispatch(PlaygroundAction.ZipUploadError(result.message))
                }
            }
        } else {
            val validFiles = files.filter { file ->
                file.name.endsWith(".svg", ignoreCase = true) ||
                    file.name.endsWith(".xml", ignoreCase = true)
            }
            when {
                validFiles.size == 1 -> scope.launch {
                    val file = validFiles.first()
                    val content = readFileAsText(file)
                    vm.dispatch(
                        PlaygroundAction.SingleFileLoaded(file.name, content),
                    )
                }

                validFiles.size > 1 -> scope.launch {
                    val uploadedInfos = validFiles.map { file ->
                        val content = readFileAsText(file)
                        UploadedFileInfo(
                            name = file.name,
                            content = content,
                            detectedExtension = detectExtension(content)
                                ?: "svg",
                            relativePath = paths[file.name] ?: "",
                        )
                    }
                    vm.loadFiles(uploadedInfos)
                }
            }
        }
    }
}

internal sealed interface ZipUploadResult {
    data class Success(val files: List<UploadedFileInfo>) : ZipUploadResult
    data class Error(val message: String) : ZipUploadResult
}

internal suspend fun processZipUpload(file: File): ZipUploadResult {
    val sizeCheck = checkZipSizeLimit(file)
    if (sizeCheck is ZipLimitCheck.SizeWarning) {
        val sizeMb = sizeCheck.sizeBytes / (1024 * 1024)
        console.warn(
            "Zip file is ${sizeMb.toInt()} MB, " +
                "which exceeds the 50 MB soft limit.",
        )
    }

    val parsedZip = try {
        parseZipEntries(file)
    } catch (e: IllegalStateException) {
        console.error(e.message)
        return ZipUploadResult.Error(
            e.message ?: "Failed to read the zip file.",
        )
    }

    val countCheck = checkZipFileCountLimit(parsedZip)
    if (countCheck is ZipLimitCheck.FileCountWarning) {
        console.warn(
            "Zip contains ${countCheck.count} matching files, " +
                "which exceeds the 200 file soft limit.",
        )
    }

    val result = extractFilesFromParsedZip(parsedZip)
    if (result.files.isEmpty() && result.errors.isEmpty()) {
        return ZipUploadResult.Error(
            "No SVG or Android Vector Drawable files found in the zip.",
        )
    }
    return ZipUploadResult.Success(result.files)
}
