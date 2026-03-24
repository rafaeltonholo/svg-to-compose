package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.FilePickerInput
import dev.tonholo.s2c.website.components.layouts.SectionContainer
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.playground.BatchNavigationBar
import dev.tonholo.s2c.website.components.molecules.playground.FeedbackButtonRow
import dev.tonholo.s2c.website.components.molecules.playground.FileDropOverlay
import dev.tonholo.s2c.website.components.molecules.playground.MobileTabBar
import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.PlaygroundState.Companion.samples
import dev.tonholo.s2c.website.state.playground.PlaygroundViewModel
import dev.tonholo.s2c.website.state.playground.batch.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.batch.BatchPhase
import dev.tonholo.s2c.website.theme.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.util.handleDrop
import dev.tonholo.s2c.website.zip.downloadAsZip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.files.File

// region Styles

val PlaygroundHeadingContainerStyle = CssStyle.base {
    Modifier.margin(bottom = SiteTheme.dimensions.size.Lg)
}

val EditorPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .position(Position.Relative)
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().outlineVariant)
        .overflow(Overflow.Hidden)
}

val DesktopPanelsStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns {
                size(2.fr)
                size(3.fr)
            }
            .minWidth(0.px)
            .height(clamp(24.cssRem, 50.vh, 40.cssRem))
    }
    cssRule(" > *") {
        Modifier
            .overflow(Overflow.Auto)
            .minWidth(0.px)
            .minHeight(0.px)
    }
}

val MobilePanelContentStyle = CssStyle.base {
    Modifier
        .height(50.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

// endregion

/**
 * Full-width playground section containing the code editor, live preview,
 * output panel, sample selector, and conversion options.
 */
@Composable
fun PlaygroundSection(modifier: Modifier = Modifier) {
    SectionContainer(
        id = "playground",
        modifier = modifier,
        contentModifier = Modifier,
    ) {
        val vm = remember { PlaygroundViewModel() }
        val scope = rememberCoroutineScope()
        var isDragOver by remember { mutableStateOf(false) }
        var fileInputRef by remember { mutableStateOf<HTMLInputElement?>(null) }
        var folderInputRef by remember { mutableStateOf<HTMLInputElement?>(null) }

        DisposableEffect(vm) {
            vm.initWorkers()
            onDispose { vm.cleanupWorkers() }
        }

        val onSelectFiles = rememberFileSelectedHandler(scope, vm)

        Div(attrs = PlaygroundHeadingContainerStyle.toModifier().toAttrs()) {
            SpanText(
                "Try it",
                modifier = LabelTextStyle.toModifier()
                    .color(SiteTheme.palette.onSurfaceVariant),
            )
        }

        HiddenFilePickers(
            onFileInputRef = { fileInputRef = it },
            onFolderInputRef = { folderInputRef = it },
            onSelectFiles = onSelectFiles,
        )

        PlaygroundEditorPanel(
            state = vm.state,
            dispatch = vm::dispatch,
            scope = scope,
            completedCountByFolder = vm.completedCountByFolder,
            completedResultsByKey = vm.completedResultsByKey,
            selectedCountByFolder = vm.selectedCountByFolder,
            isDragOver = isDragOver,
            onSampleSelect = { scope.launch { vm.selectSample(it) } },
            onConvert = { vm.convertSingle() },
            onStartBatchConversion = vm::startBatchConversion,
            onCancelBatch = vm::cancelBatch,
            fileInputRef = fileInputRef,
            folderInputRef = folderInputRef,
            onDragOverChange = { isDragOver = it },
            onSelectFiles = onSelectFiles,
        )
        FeedbackButtonRow(state = vm.state)
    }
}

@Composable
private fun HiddenFilePickers(
    onFileInputRef: (HTMLInputElement?) -> Unit,
    onFolderInputRef: (HTMLInputElement?) -> Unit,
    onSelectFiles: (List<File>, Map<String, String>) -> Unit,
) {
    FilePickerInput(
        directory = false,
        accept = ".svg,.xml,.zip",
        inputRef = onFileInputRef,
        onSelectFiles = { files -> onSelectFiles(files, emptyMap()) },
    )
    FilePickerInput(
        directory = true,
        inputRef = onFolderInputRef,
        onSelectFiles = { files ->
            val paths = mutableMapOf<String, String>()
            files.forEach { file ->
                val relPath = file.asDynamic().webkitRelativePath as? String ?: ""
                val parts = relPath.split("/")
                if (parts.size > 2) {
                    paths[file.name] = parts.drop(1).dropLast(1).joinToString("/")
                }
            }
            onSelectFiles(files, paths)
        },
    )
}

@Composable
@Suppress("LongParameterList")
private fun PlaygroundEditorPanel(
    state: PlaygroundState,
    dispatch: (PlaygroundAction) -> Unit,
    scope: CoroutineScope,
    completedCountByFolder: Map<String, Int>,
    completedResultsByKey: Map<String, BatchConversionResult>,
    selectedCountByFolder: Map<String, Int>,
    isDragOver: Boolean,
    onSampleSelect: (Int) -> Unit,
    onConvert: () -> Unit,
    onStartBatchConversion: () -> Unit,
    onCancelBatch: () -> Unit,
    fileInputRef: HTMLInputElement?,
    folderInputRef: HTMLInputElement?,
    onDragOverChange: (Boolean) -> Unit,
    onSelectFiles: (List<File>, Map<String, String>) -> Unit,
) {
    var editorPanelElement by remember { mutableStateOf<HTMLElement?>(null) }

    Div(
        attrs = EditorPanelStyle.toModifier().toAttrs {
            ref { element ->
                editorPanelElement = element
                onDispose { editorPanelElement = null }
            }
        },
    ) {
        DragAndDropEffect(editorPanelElement, scope, onSelectFiles, onDragOverChange)

        if (isDragOver) FileDropOverlay()

        PlaygroundToolbar(
            inputMode = state.inputMode,
            isConverting = state.isConverting,
            sampleNames = samples.map { it.name },
            selectedSample = state.selectedSample,
            onSampleSelect = onSampleSelect,
            onInputModeChange = { dispatch(PlaygroundAction.ChangeInputMode(it)) },
            onConvert = onConvert,
        )

        CollapsibleSection(title = "Options") {
            OptionsContent(
                options = state.options,
                onOptionsChange = { dispatch(PlaygroundAction.ChangeOptions(it)) },
            )
        }

        PreviewSection(state, dispatch)
        BatchOrCodePanels(
            state = state,
            dispatch = dispatch,
            scope = scope,
            completedCountByFolder = completedCountByFolder,
            completedResultsByKey = completedResultsByKey,
            selectedCountByFolder = selectedCountByFolder,
            onStartBatchConversion = onStartBatchConversion,
            onCancelBatch = onCancelBatch,
            fileInputRef = fileInputRef,
            folderInputRef = folderInputRef,
        )
    }
}

@Composable
private fun PreviewSection(state: PlaygroundState, dispatch: (PlaygroundAction) -> Unit) {
    CollapsibleSection(
        title = "Preview",
        expanded = state.previewExpanded,
        onExpandedChange = { dispatch(PlaygroundAction.ChangePreviewExpanded(it)) },
    ) {
        ComparisonStrip(
            svgCode = state.inputCode,
            extension = state.extension,
            iconFileContentsJson = state.iconFileContentsJson,
            zoomLevel = state.zoomLevel,
            onZoomChange = { dispatch(PlaygroundAction.ChangeZoom(it)) },
        )
    }
}

@Composable
@Suppress("LongParameterList")
private fun BatchOrCodePanels(
    state: PlaygroundState,
    dispatch: (PlaygroundAction) -> Unit,
    scope: CoroutineScope,
    completedCountByFolder: Map<String, Int>,
    completedResultsByKey: Map<String, BatchConversionResult>,
    selectedCountByFolder: Map<String, Int>,
    onStartBatchConversion: () -> Unit,
    onCancelBatch: () -> Unit,
    fileInputRef: HTMLInputElement?,
    folderInputRef: HTMLInputElement?,
) {
    val results = (state.batchPhase as? BatchPhase.Results)?.completed

    if (state.viewingBatchResult && results != null) {
        BatchNavigationBar(
            viewingIndex = state.viewingBatchIndex,
            totalResults = results.size,
            onBack = { dispatch(PlaygroundAction.BackToBatchList) },
            onPrev = { dispatch(PlaygroundAction.NavigatePrev) },
            onNext = { dispatch(PlaygroundAction.NavigateNext) },
        )
    }

    val showBatchPanel = state.batchPhase != null && !state.viewingBatchResult

    if (showBatchPanel) {
        BatchPanel(
            state = state,
            completedCountByFolder = completedCountByFolder,
            completedResultsByKey = completedResultsByKey,
            selectedCountByFolder = selectedCountByFolder,
            onToggleSelectAll = { dispatch(PlaygroundAction.ToggleSelectAll) },
            onToggleFileSelection = { dispatch(PlaygroundAction.ToggleFileSelection(it)) },
            onToggleFolderSelection = { dispatch(PlaygroundAction.ToggleFolderSelection(it)) },
            onSetFoldersSelection = { paths, selected ->
                dispatch(PlaygroundAction.SetFoldersSelection(paths, selected))
            },
            onToggleFolderExpand = { dispatch(PlaygroundAction.ToggleFolderExpanded(it)) },
            onStartConversion = onStartBatchConversion,
            onCancel = onCancelBatch,
            onDownload = {
                val downloadResults = (state.batchPhase as? BatchPhase.Results)?.completed
                if (downloadResults != null) {
                    scope.launch { downloadAsZip(downloadResults) }
                }
            },
            onRestart = { dispatch(PlaygroundAction.RestartBatch) },
            onClear = { dispatch(PlaygroundAction.ClearBatchResults) },
            onInspect = { dispatch(PlaygroundAction.InspectBatchResult(it)) },
        )
    } else {
        DesktopPanels(state, dispatch, fileInputRef, folderInputRef)
        MobilePanels(state, dispatch, fileInputRef, folderInputRef)
    }
}

@Composable
private fun DesktopPanels(
    state: PlaygroundState,
    dispatch: (PlaygroundAction) -> Unit,
    fileInputRef: HTMLInputElement?,
    folderInputRef: HTMLInputElement?,
) {
    Div(
        attrs = DesktopPanelsStyle.toModifier()
            .displayIfAtLeast(Breakpoint.MD)
            .toAttrs(),
    ) {
        InputOrUploadPanel(state, dispatch, fileInputRef, folderInputRef)
        OutputPanel(
            kotlinCode = state.convertedKotlinCode,
            isConverting = state.isConverting,
            conversionProgress = state.conversionProgress,
            conversionError = state.conversionError,
            fileName = state.outputFileName,
        )
    }
}

@Composable
private fun MobilePanels(
    state: PlaygroundState,
    dispatch: (PlaygroundAction) -> Unit,
    fileInputRef: HTMLInputElement?,
    folderInputRef: HTMLInputElement?,
) {
    Div(attrs = Modifier.displayUntil(Breakpoint.MD).toAttrs()) {
        MobileTabBar(
            tabs = listOf("Input", "Output"),
            activePanel = state.activePanel,
            onPanelSelect = { dispatch(PlaygroundAction.SelectMobilePanel(it)) },
        )
        Div(
            attrs = MobilePanelContentStyle.toModifier().toAttrs {
                attr("role", "tabpanel")
            },
        ) {
            when (state.activePanel) {
                0 -> InputOrUploadPanel(state, dispatch, fileInputRef, folderInputRef)

                1 -> OutputPanel(
                    kotlinCode = state.convertedKotlinCode,
                    isConverting = state.isConverting,
                    conversionProgress = state.conversionProgress,
                    conversionError = state.conversionError,
                    fileName = state.outputFileName,
                )
            }
        }
    }
}

@Composable
private fun InputOrUploadPanel(
    state: PlaygroundState,
    dispatch: (PlaygroundAction) -> Unit,
    fileInputRef: HTMLInputElement?,
    folderInputRef: HTMLInputElement?,
) {
    if (state.inputMode == "upload") {
        UploadPanel(
            onFilePickerClick = { fileInputRef?.click() },
            onFolderPickerClick = { folderInputRef?.click() },
        )
    } else {
        InputPanel(
            state.inputCode,
            onInputChange = { dispatch(PlaygroundAction.UpdateInputCode(it)) },
            onPaste = { dispatch(PlaygroundAction.LoadContent(it)) },
            fileName = state.inputFileName,
        )
    }
}

// region Drag and drop

@Composable
private fun DragAndDropEffect(
    element: HTMLElement?,
    scope: CoroutineScope,
    onSelectFiles: (List<File>, Map<String, String>) -> Unit,
    onDragOverChange: (Boolean) -> Unit,
) {
    val currentOnSelectFiles by rememberUpdatedState(onSelectFiles)
    val currentOnDragOverChange by rememberUpdatedState(onDragOverChange)

    DisposableEffect(element) {
        val el = element ?: return@DisposableEffect onDispose { }
        var counter = 0

        val onDragEnter: (Event) -> Unit = { e ->
            e.preventDefault()
            e.stopPropagation()
            counter++
            currentOnDragOverChange(true)
        }
        val onDragOver: (Event) -> Unit = { e -> e.preventDefault() }
        val onDragLeave: (Event) -> Unit = { e ->
            e.preventDefault()
            e.stopPropagation()
            counter--
            if (counter <= 0) {
                counter = 0
                currentOnDragOverChange(false)
            }
        }
        val onDrop: (Event) -> Unit = { e ->
            e.preventDefault()
            e.stopPropagation()
            currentOnDragOverChange(false)
            counter = 0
            handleDrop(e, scope, currentOnSelectFiles)
        }

        el.addEventListener("dragenter", onDragEnter)
        el.addEventListener("dragover", onDragOver)
        el.addEventListener("dragleave", onDragLeave)
        el.addEventListener("drop", onDrop)
        onDispose {
            el.removeEventListener("dragenter", onDragEnter)
            el.removeEventListener("dragover", onDragOver)
            el.removeEventListener("dragleave", onDragLeave)
            el.removeEventListener("drop", onDrop)
        }
    }
}

// endregion
