package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.topBottom
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.IntersectionObserverTrigger
import dev.tonholo.s2c.website.components.molecules.playground.FileGroupHeader
import dev.tonholo.s2c.website.components.molecules.playground.FileRow
import dev.tonholo.s2c.website.state.playground.batch.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.batch.BatchPhase
import dev.tonholo.s2c.website.state.playground.batch.FileGroup
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.state.playground.batch.BatchFileListState
import dev.tonholo.s2c.website.state.playground.batch.fileKey
import dev.tonholo.s2c.website.state.playground.folder.FileGroupHeaderState
import dev.tonholo.s2c.website.state.playground.folder.FolderGroupState
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

private const val INITIAL_VISIBLE_FILES = 50
private const val LOAD_MORE_CHUNK_SIZE = 100

val BatchPanelStyle = CssStyle.base {
    Modifier.fillMaxWidth()
}

val BatchPanelFileListStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .overflow(Overflow.Auto)
        .maxHeight(60.vh)
        .borderTop {
            width(1.px)
            style(LineStyle.Solid)
            color(colorMode.toSitePalette().outline)
        }
}

/**
 * Main batch panel that composes the sticky [BatchPhaseHeader] and a scrollable
 * file list grouped by folder.
 *
 * Root-level files (those whose [FileGroup.folderPath] is empty) are rendered
 * directly as [FileRow]s without a folder header. Files inside named folders are
 * rendered under a collapsible [FileGroupHeader], with their rows visible only
 * when the folder is expanded via [PlaygroundState.expandedFolders].
 */
@Composable
internal fun BatchPanel(
    state: PlaygroundState,
    completedCountByFolder: Map<String, Int>,
    completedResultsByKey: Map<String, BatchConversionResult>,
    selectedCountByFolder: Map<String, Int>,
    modifier: Modifier = Modifier,
    onToggleSelectAll: () -> Unit = {},
    onToggleFileSelection: (String) -> Unit = {},
    onToggleFolderSelection: (String) -> Unit = {},
    onSetFoldersSelection: (folderPaths: List<String>, selected: Boolean) -> Unit = { _, _ -> },
    onToggleFolderExpand: (String) -> Unit = {},
    onStartConversion: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDownload: () -> Unit = {},
    onRestart: () -> Unit = {},
    onClear: () -> Unit = {},
    onInspect: (BatchConversionResult) -> Unit = {},
) {
    val phase = state.batchPhase ?: return

    val totalFiles = state.uploadedFiles.size
    val selectedCount = state.selectedFiles.size
    val allSelected = totalFiles > 0 && selectedCount == totalFiles

    // Shift+click range selection for folders
    var lastClickedFolderIndex by remember { mutableStateOf(-1) }
    val folderPaths = remember(state.fileGroups) {
        state.fileGroups
            .filter { it.folderPath.isNotEmpty() }
            .map { it.folderPath }
    }

    Column(modifier = BatchPanelStyle.toModifier().then(modifier)) {
        BatchPhaseHeader(
            phase = phase,
            totalFiles = totalFiles,
            selectedCount = selectedCount,
            allSelected = allSelected,
            onToggleSelectAll = onToggleSelectAll,
            onStartConversion = onStartConversion,
            onCancel = onCancel,
            onDownload = onDownload,
            onRestart = onRestart,
            onClear = onClear,
        )

        BatchFileList(
            state = BatchFileListState(
                playgroundState = state,
                phase = phase,
                completedResultsByKey = completedResultsByKey,
                completedCountByFolder = completedCountByFolder,
                selectedCountByFolder = selectedCountByFolder,
                folderPaths = folderPaths,
                lastClickedFolderIndex = lastClickedFolderIndex,
            ),
            onLastClickedFolderIndexChange = { lastClickedFolderIndex = it },
            onToggleFileSelection = onToggleFileSelection,
            onToggleFolderExpand = onToggleFolderExpand,
            onToggleFolderSelection = onToggleFolderSelection,
            onSetFoldersSelection = onSetFoldersSelection,
            onInspect = onInspect,
        )
    }
}

@Composable
private fun BatchFileList(
    state: BatchFileListState,
    onLastClickedFolderIndexChange: (Int) -> Unit = {},
    onToggleFileSelection: (String) -> Unit = {},
    onToggleFolderExpand: (String) -> Unit = {},
    onToggleFolderSelection: (String) -> Unit = {},
    onSetFoldersSelection: (List<String>, Boolean) -> Unit = { _, _ -> },
    onInspect: (BatchConversionResult) -> Unit = {},
) {
    Column(modifier = BatchPanelFileListStyle.toModifier()) {
        for (group in state.playgroundState.fileGroups) {
            if (group.folderPath.isEmpty()) {
                RootFileRows(
                    group = group,
                    phase = state.phase,
                    selectedFiles = state.playgroundState.selectedFiles,
                    completedResultsByKey = state.completedResultsByKey,
                    onToggleFileSelection = onToggleFileSelection,
                    onInspect = onInspect,
                )
            } else {
                val folderIndex = state.folderPaths.indexOf(group.folderPath)
                FolderGroup(
                    state = FolderGroupState(
                        group = group,
                        phase = state.phase,
                        selectedFiles = state.playgroundState.selectedFiles,
                        expandedFolders = state.playgroundState.expandedFolders,
                        completedResultsByKey = state.completedResultsByKey,
                        completedCountByFolder = state.completedCountByFolder,
                        selectedCountForFolder = state.selectedCountByFolder[group.folderPath] ?: 0,
                    ),
                    onToggleFolderExpand = onToggleFolderExpand,
                    onToggleFolderSelection = { shiftKey ->
                        handleFolderSelectionClick(
                            shiftKey = shiftKey,
                            folderIndex = folderIndex,
                            lastClickedIndex = state.lastClickedFolderIndex,
                            folderPaths = state.folderPaths,
                            selectedFiles = state.playgroundState.selectedFiles,
                            uploadedFiles = state.playgroundState.uploadedFiles,
                            onToggle = onToggleFolderSelection,
                            onSetRange = onSetFoldersSelection,
                        )
                        onLastClickedFolderIndexChange(folderIndex)
                    },
                    onToggleFileSelection = onToggleFileSelection,
                    onInspect = onInspect,
                )
            }
        }
    }
}

@Composable
private fun RootFileRows(
    group: FileGroup,
    phase: BatchPhase,
    selectedFiles: Set<String>,
    completedResultsByKey: Map<String, BatchConversionResult>,
    onToggleFileSelection: (String) -> Unit = {},
    onInspect: (BatchConversionResult) -> Unit = {},
) {
    for (file in group.files) {
        val result = completedResultsByKey[file.fileKey()]
        FileRow(
            file = file,
            phase = phase,
            isSelected = file.fileKey() in selectedFiles,
            conversionResult = result,
            isCurrentlyConverting = false,
            onToggleSelection = { onToggleFileSelection(file.fileKey()) },
            onInspect = onInspect,
        )
    }
}

@Composable
private fun FolderGroup(
    state: FolderGroupState,
    onToggleFolderExpand: (String) -> Unit = {},
    onToggleFolderSelection: (shiftKey: Boolean) -> Unit = {},
    onToggleFileSelection: (String) -> Unit = {},
    onInspect: (BatchConversionResult) -> Unit = {},
) {
    val isExpanded = state.group.folderPath in state.expandedFolders
    val groupCompletedCount = when (state.phase) {
        is BatchPhase.Converting,
        is BatchPhase.Results,
        -> state.completedCountByFolder[state.group.folderPath] ?: 0

        else -> 0
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        FileGroupHeader(
            state = FileGroupHeaderState(
                groupState = state,
                isExpanded = isExpanded,
                selectedCount = state.selectedCountForFolder,
                completedCount = groupCompletedCount,
            ),
            onToggleExpand = { onToggleFolderExpand(state.group.folderPath) },
            onToggleSelection = onToggleFolderSelection,
        )

        if (isExpanded) {
            ExpandedFolderFiles(
                group = state.group,
                phase = state.phase,
                selectedFiles = state.selectedFiles,
                completedResultsByKey = state.completedResultsByKey,
                onToggleFileSelection = onToggleFileSelection,
                onInspect = onInspect,
            )
        }
    }
}

@Composable
private fun ExpandedFolderFiles(
    group: FileGroup,
    phase: BatchPhase,
    selectedFiles: Set<String>,
    completedResultsByKey: Map<String, BatchConversionResult>,
    onToggleFileSelection: (String) -> Unit = {},
    onInspect: (BatchConversionResult) -> Unit = {},
) {
    var visibleCount by remember(group.folderPath) { mutableStateOf(INITIAL_VISIBLE_FILES) }
    val filesToShow = if (group.files.size <= visibleCount) {
        group.files
    } else {
        group.files.take(visibleCount)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        for (file in filesToShow) {
            val result = completedResultsByKey[file.fileKey()]
            FileRow(
                file = file,
                phase = phase,
                isSelected = file.fileKey() in selectedFiles,
                conversionResult = result,
                isCurrentlyConverting = false,
                onToggleSelection = { onToggleFileSelection(file.fileKey()) },
                onInspect = onInspect,
                modifier = Modifier.padding {
                    left(1.5.cssRem)
                },
            )
        }
        if (visibleCount < group.files.size) {
            LoadMoreTrigger(
                visibleCount = visibleCount,
                totalCount = group.files.size,
                onLoadMore = {
                    visibleCount = minOf(visibleCount + LOAD_MORE_CHUNK_SIZE, group.files.size)
                },
            )
        }
    }
}

@Composable
private fun LoadMoreTrigger(visibleCount: Int, totalCount: Int, onLoadMore: () -> Unit) {
    IntersectionObserverTrigger(onVisible = onLoadMore)
    SpanText(
        "Show more ($visibleCount/$totalCount)",
        modifier = Modifier
            .padding {
                topBottom(0.375.cssRem)
                left(1.5.cssRem)
            }
            .fontSize(0.75.cssRem)
            .color(ColorMode.current.toSitePalette().primary)
            .cursor(Cursor.Pointer)
            .onClick { onLoadMore() },
    )
}

/**
 * Handles folder checkbox click with optional Shift+click range selection.
 *
 * - Normal click: toggle the single folder
 * - Shift+click: select or deselect the range based on the clicked
 *   folder's current state (determines intent: if it's selected,
 *   the intent is to deselect the range, and vice versa)
 */
private fun handleFolderSelectionClick(
    shiftKey: Boolean,
    folderIndex: Int,
    lastClickedIndex: Int,
    folderPaths: List<String>,
    selectedFiles: Set<String>,
    uploadedFiles: List<UploadedFileInfo> = emptyList(),
    onToggle: (String) -> Unit = {},
    onSetRange: (paths: List<String>, selected: Boolean) -> Unit = { _, _ -> },
) {
    if (!shiftKey) {
        onToggle(folderPaths[folderIndex])
        return
    }
    val startIndex = if (lastClickedIndex >= 0) lastClickedIndex else 0
    val from = minOf(startIndex, folderIndex)
    val to = maxOf(startIndex, folderIndex)
    val rangePaths = folderPaths.subList(from, to + 1)

    // Determine intent from the clicked folder's current state
    val clickedPath = folderPaths[folderIndex]
    val clickedFolderKeys = uploadedFiles
        .filter { it.relativePath == clickedPath }
        .map { it.fileKey() }
    val isCurrentlySelected = clickedFolderKeys.all { it in selectedFiles }
    // If currently selected → intent is to deselect range; otherwise select
    onSetRange(rangePaths, !isCurrentlySelected)
}
