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
import dev.tonholo.s2c.website.components.molecules.playground.BatchPhaseHeader
import dev.tonholo.s2c.website.components.molecules.playground.FileGroupHeader
import dev.tonholo.s2c.website.components.molecules.playground.FileRow
import dev.tonholo.s2c.website.state.playground.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.state.playground.FileGroup
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.state.playground.fileKey
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

private const val MAX_VISIBLE_FILES = 100

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
 * when the folder is expanded via [state.expandedFolders].
 */
@Composable
internal fun BatchPanel(
    state: PlaygroundState,
    completedCountByFolder: Map<String, Int>,
    onToggleSelectAll: () -> Unit,
    onToggleFileSelection: (String) -> Unit,
    onToggleFolderSelection: (String) -> Unit,
    onSetFoldersSelection: (folderPaths: List<String>, selected: Boolean) -> Unit,
    onToggleFolderExpand: (String) -> Unit,
    onStartConversion: () -> Unit,
    onCancel: () -> Unit,
    onDownload: () -> Unit,
    onRestart: () -> Unit,
    onClear: () -> Unit,
    onInspect: (BatchConversionResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    val phase = state.batchPhase ?: return

    val totalFiles = state.uploadedFiles.size
    val selectedCount = state.selectedFiles.size
    val allSelected = totalFiles > 0 && selectedCount == totalFiles

    val completedResults = when (phase) {
        is BatchPhase.Results -> phase.completed
        else -> emptyList()
    }

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

        Column(modifier = BatchPanelFileListStyle.toModifier()) {
            for (group in state.fileGroups) {
                if (group.folderPath.isEmpty()) {
                    RootFileRows(
                        group = group,
                        phase = phase,
                        selectedFiles = state.selectedFiles,
                        completedResults = completedResults,
                        onToggleFileSelection = onToggleFileSelection,
                        onInspect = onInspect,
                    )
                } else {
                    val folderIndex = folderPaths.indexOf(group.folderPath)
                    FolderGroup(
                        group = group,
                        phase = phase,
                        selectedFiles = state.selectedFiles,
                        expandedFolders = state.expandedFolders,
                        completedResults = completedResults,
                        completedCountByFolder = completedCountByFolder,
                        onToggleFolderExpand = onToggleFolderExpand,
                        onToggleFolderSelection = { shiftKey ->
                            handleFolderSelectionClick(
                                shiftKey = shiftKey,
                                folderIndex = folderIndex,
                                lastClickedIndex = lastClickedFolderIndex,
                                folderPaths = folderPaths,
                                selectedFiles = state.selectedFiles,
                                uploadedFiles = state.uploadedFiles,
                                onToggle = onToggleFolderSelection,
                                onSetRange = onSetFoldersSelection,
                            )
                            lastClickedFolderIndex = folderIndex
                        },
                        onToggleFileSelection = onToggleFileSelection,
                        onInspect = onInspect,
                    )
                }
            }
        }
    }
}

@Composable
private fun RootFileRows(
    group: FileGroup,
    phase: BatchPhase,
    selectedFiles: Set<String>,
    completedResults: List<BatchConversionResult>,
    onToggleFileSelection: (String) -> Unit,
    onInspect: (BatchConversionResult) -> Unit,
) {
    for (file in group.files) {
        val result = completedResults.find { it.fileName == file.name && it.relativePath == file.relativePath }
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
    group: FileGroup,
    phase: BatchPhase,
    selectedFiles: Set<String>,
    expandedFolders: Set<String>,
    completedResults: List<BatchConversionResult>,
    completedCountByFolder: Map<String, Int>,
    onToggleFolderExpand: (String) -> Unit,
    onToggleFolderSelection: (shiftKey: Boolean) -> Unit,
    onToggleFileSelection: (String) -> Unit,
    onInspect: (BatchConversionResult) -> Unit,
) {
    val isExpanded = group.folderPath in expandedFolders
    val groupSelectedCount = group.files.count { file -> file.fileKey() in selectedFiles }
    val groupCompletedCount = when (phase) {
        is BatchPhase.Converting -> completedCountByFolder[group.folderPath] ?: 0

        is BatchPhase.Results -> completedResults.count { result ->
            group.files.any { file -> file.name == result.fileName && file.relativePath == result.relativePath }
        }

        else -> 0
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        FileGroupHeader(
            group = group,
            phase = phase,
            isExpanded = isExpanded,
            selectedCount = groupSelectedCount,
            selectedFiles = selectedFiles,
            completedCount = groupCompletedCount,
            onToggleExpand = { onToggleFolderExpand(group.folderPath) },
            onToggleSelection = onToggleFolderSelection,
        )

        if (isExpanded) {
            ExpandedFolderFiles(
                group = group,
                phase = phase,
                selectedFiles = selectedFiles,
                completedResults = completedResults,
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
    completedResults: List<BatchConversionResult>,
    onToggleFileSelection: (String) -> Unit,
    onInspect: (BatchConversionResult) -> Unit,
) {
    var showAll by remember { mutableStateOf(false) }
    val filesToShow = if (showAll || group.files.size <= MAX_VISIBLE_FILES) {
        group.files
    } else {
        group.files.take(MAX_VISIBLE_FILES)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        for (file in filesToShow) {
            val result = completedResults.find {
                it.fileName == file.name && it.relativePath == file.relativePath
            }
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
        if (!showAll && group.files.size > MAX_VISIBLE_FILES) {
            SpanText(
                "Show all ${group.files.size} files",
                modifier = Modifier
                    .padding {
                        topBottom(0.375.cssRem)
                        left(1.5.cssRem)
                    }
                    .fontSize(0.75.cssRem)
                    .color(ColorMode.current.toSitePalette().primary)
                    .cursor(Cursor.Pointer)
                    .onClick { showAll = true },
            )
        }
    }
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
    uploadedFiles: List<UploadedFileInfo>,
    onToggle: (String) -> Unit,
    onSetRange: (paths: List<String>, selected: Boolean) -> Unit,
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
