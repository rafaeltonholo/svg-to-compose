package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dev.tonholo.s2c.website.state.playground.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.state.playground.ConversionInputFactory
import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundReducer
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.state.playground.fileKey
import dev.tonholo.s2c.website.worker.ConversionInput
import dev.tonholo.s2c.website.worker.ConversionOutput

/**
 * Thin coordinator between UI events and state. Delegates state
 * transitions to [PlaygroundReducer] and input construction to
 * [ConversionInputFactory].
 *
 * UI code dispatches [PlaygroundAction]s via [dispatch]. The only
 * methods beyond dispatch handle side-effect-producing operations
 * (worker I/O, batch index tracking) that can't be pure reducers.
 */
internal class PlaygroundViewModel : ViewModel() {
    var state by mutableStateOf(PlaygroundState())
        private set

    var batchConvertIndex by mutableStateOf(-1)
        private set

    var pendingBatchFile by mutableStateOf<UploadedFileInfo?>(null)
        private set

    private var filesToConvert: List<UploadedFileInfo> = emptyList()
    val batchResults: SnapshotStateList<BatchConversionResult> = mutableStateListOf()
    private val _completedCountByFolder = mutableStateMapOf<String, Int>()
    val completedCountByFolder: Map<String, Int> get() = _completedCountByFolder

    fun dispatch(action: PlaygroundAction) {
        state = PlaygroundReducer.reduce(state, action)
    }

    fun onWorkerOutput(output: ConversionOutput) {
        val batchFile = pendingBatchFile
        if (batchFile != null) {
            handleBatchOutput(output, batchFile)
        } else {
            dispatch(PlaygroundAction.ConversionOutputReceived(output))
        }
    }

    fun buildConvertInput(): ConversionInput {
        dispatch(PlaygroundAction.StartConversion)
        return ConversionInputFactory.fromState(state)
    }

    fun loadFiles(files: List<UploadedFileInfo>) {
        dispatch(PlaygroundAction.StartBatch(files))
    }

    fun startBatchConversion() {
        filesToConvert = state.uploadedFiles.filter { file ->
            file.fileKey() in state.selectedFiles
        }
        batchResults.clear()
        _completedCountByFolder.clear()
        dispatch(PlaygroundAction.StartBatchConversion)
        batchConvertIndex = 0
    }

    fun cancelBatch() {
        batchConvertIndex = -1
        dispatch(PlaygroundAction.CancelBatch)
    }

    /**
     * Called by a LaunchedEffect when [batchConvertIndex] changes.
     * Returns the [ConversionInput] for the next file, or null if
     * batch conversion is complete.
     */
    fun prepareNextBatchFile(): ConversionInput? {
        if (batchConvertIndex < 0) return null
        val nextFile = filesToConvert.getOrNull(batchConvertIndex)
        if (nextFile == null) {
            dispatch(PlaygroundAction.BatchCompleted(batchResults.toList()))
            batchConvertIndex = -1
            return null
        }
        pendingBatchFile = nextFile
        return ConversionInputFactory.fromUploadedFile(nextFile, state.options)
    }

    private fun handleBatchOutput(output: ConversionOutput, batchFile: UploadedFileInfo) {
        when (output) {
            is ConversionOutput.Progress -> {
                dispatch(PlaygroundAction.BatchFileProgress(batchFile.name, output.stage))
            }

            is ConversionOutput.Success -> {
                val result = BatchConversionResult(
                    fileName = batchFile.name,
                    originalContent = batchFile.content,
                    detectedExtension = batchFile.detectedExtension,
                    relativePath = batchFile.relativePath,
                    kotlinCode = output.kotlinCode,
                    iconFileContentsJson = output.iconFileContentsJson,
                )
                addResult(result)
                advanceOrCancel()
            }

            is ConversionOutput.Error -> {
                val result = BatchConversionResult(
                    fileName = batchFile.name,
                    originalContent = batchFile.content,
                    detectedExtension = batchFile.detectedExtension,
                    relativePath = batchFile.relativePath,
                    error = output.message,
                )
                addResult(result)
                advanceOrCancel()
            }
        }
    }

    private fun addResult(result: BatchConversionResult) {
        batchResults.add(result)
        val folder = result.relativePath
        _completedCountByFolder[folder] = (_completedCountByFolder[folder] ?: 0) + 1
        dispatch(PlaygroundAction.BatchFileCompleted(result))
    }

    private fun advanceOrCancel() {
        pendingBatchFile = null
        val phase = state.batchPhase
        if (phase is BatchPhase.Converting && phase.cancelling) {
            dispatch(PlaygroundAction.BatchCancelled(batchResults.toList()))
            batchConvertIndex = -1
        } else {
            batchConvertIndex++
        }
    }
}
