package dev.tonholo.s2c.website.state.playground

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dev.tonholo.s2c.website.state.playground.PlaygroundState.Companion.samples
import dev.tonholo.s2c.website.state.playground.batch.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.batch.BatchPhase
import dev.tonholo.s2c.website.state.playground.batch.fileKey
import dev.tonholo.s2c.website.state.playground.batch.resultKey
import dev.tonholo.s2c.website.state.playground.reducer.PlaygroundReducer
import dev.tonholo.s2c.website.state.playground.reducer.Reducer
import dev.tonholo.s2c.website.worker.ConversionOutput
import dev.tonholo.s2c.website.worker.IconConvertWorker
import kotlinx.browser.window
import kotlinx.coroutines.await

/**
 * Thin coordinator between UI events and state. Delegates state
 * transitions to [PlaygroundReducer] and input construction to
 * [ConversionInputFactory].
 *
 * UI code dispatches [PlaygroundAction]s via [dispatch]. The only
 * methods beyond dispatch handle side-effect-producing operations
 * (worker I/O, batch pool management) that can't be pure reducers.
 */
internal class PlaygroundViewModel(
    private val reducer: Reducer<PlaygroundAction, PlaygroundState> = PlaygroundReducer(),
) : ViewModel() {
    var state by mutableStateOf(PlaygroundState())
        private set

    private var filesToConvert: List<UploadedFileInfo> = emptyList()
    val batchResults: SnapshotStateList<BatchConversionResult> = mutableStateListOf()
    private val _completedCountByFolder = mutableStateMapOf<String, Int>()
    val completedCountByFolder: Map<String, Int> get() = _completedCountByFolder
    private val _completedResultsByKey = mutableStateMapOf<String, BatchConversionResult>()
    val completedResultsByKey: Map<String, BatchConversionResult> get() = _completedResultsByKey

    private var singleWorker: IconConvertWorker? = null
    private var workerPool: WorkerPool? = null

    private val templateHelper = TemplatePersistenceHelper(
        readState = { state },
        dispatch = ::dispatch,
    )

    val selectedCountByFolder: Map<String, Int>
        get() = state.uploadedFiles
            .filter { it.fileKey() in state.selectedFiles }
            .groupingBy { it.relativePath }
            .eachCount()

    /**
     * Initialises the single-file worker and the batch worker pool.
     * Must be called once from a [DisposableEffect] in the
     * Composable that hosts this ViewModel.
     */
    fun initWorkers(poolSize: Int = WorkerPool.DEFAULT_POOL_SIZE) {
        if (singleWorker != null) return
        templateHelper.restorePersistedState()
        singleWorker = IconConvertWorker { output ->
            dispatch(PlaygroundAction.ConversionOutputReceived(output))
        }
        workerPool = WorkerPool(
            poolSize = poolSize,
            onOutput = ::handleBatchOutput,
            onDrained = ::onBatchDrained,
        )
    }

    /** Terminates all workers. Call from [DisposableEffect.onDispose]. */
    fun cleanupWorkers() {
        singleWorker?.terminate()
        singleWorker = null
        workerPool?.terminate()
        workerPool = null
    }

    fun dispatch(action: PlaygroundAction) {
        state = reducer.reduce(state, action)
        when (action) {
            is PlaygroundAction.UpdateTemplateToml,
            is PlaygroundAction.TemplateFileLoaded,
            -> {
                templateHelper.scheduleTemplateValidation()
                templateHelper.schedulePersistence()
            }

            is PlaygroundAction.ChangeOptions,
            is PlaygroundAction.ClearTemplate,
            -> {
                templateHelper.schedulePersistence()
            }

            else -> Unit
        }
    }

    suspend fun selectSample(index: Int) {
        dispatch(PlaygroundAction.SelectSample(index))
        val sample = samples[index]
        val response = window.fetch(sample.path).await()
        val svgCode = response.text().await()
        dispatch(
            PlaygroundAction.SampleLoaded(
                svgCode = svgCode,
                samplePath = sample.path,
                iconName = sample.name,
            ),
        )
    }

    /** Converts the current editor content (single-file mode). */
    fun convertSingle() {
        if (state.templateErrors.isNotEmpty()) return
        val worker = singleWorker ?: return
        dispatch(PlaygroundAction.ChangeTemplateExpanded(expanded = false))
        dispatch(PlaygroundAction.StartConversion)
        val input = ConversionInputFactory.fromState(state)
        worker.postInput(input)
    }

    fun startBatchConversion() {
        if (state.templateErrors.isNotEmpty()) return
        val pool = workerPool ?: return
        dispatch(PlaygroundAction.ChangeTemplateExpanded(expanded = false))
        filesToConvert = state.uploadedFiles.filter { file ->
            file.fileKey() in state.selectedFiles
        }
        batchResults.clear()
        _completedCountByFolder.clear()
        _completedResultsByKey.clear()
        dispatch(PlaygroundAction.StartBatchConversion)

        val templateToml = state.templateToml.takeIfUsableTemplate()
        val inputs = filesToConvert.mapIndexed { index, file ->
            index to ConversionInputFactory.fromUploadedFile(
                file,
                state.options,
                templateToml,
            )
        }
        pool.submitAll(inputs)
    }

    fun cancelBatch() {
        workerPool?.cancel()
        dispatch(PlaygroundAction.CancelBatch)
    }

    private fun handleBatchOutput(fileIndex: Int, output: ConversionOutput) {
        val batchFile = filesToConvert.getOrNull(fileIndex) ?: return
        when (output) {
            is ConversionOutput.Progress -> {
                dispatch(
                    PlaygroundAction.BatchFileProgress(
                        batchFile.name,
                        output.stage,
                    ),
                )
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
            }
        }
    }

    private fun onBatchDrained() {
        val phase = state.batchPhase
        if (phase is BatchPhase.Converting && phase.cancelling) {
            dispatch(PlaygroundAction.BatchCancelled(batchResults.toList()))
        } else {
            dispatch(PlaygroundAction.BatchCompleted(batchResults.toList()))
        }
    }

    private fun addResult(result: BatchConversionResult) {
        batchResults.add(result)
        val folder = result.relativePath
        _completedCountByFolder[folder] = (_completedCountByFolder[folder] ?: 0) + 1
        _completedResultsByKey[result.resultKey()] = result
        dispatch(PlaygroundAction.BatchFileCompleted(result))
    }
}
