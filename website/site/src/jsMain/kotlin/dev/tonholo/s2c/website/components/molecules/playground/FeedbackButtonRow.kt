package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.browser.uri.encodeURIComponent
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.icons.fa.FaBug
import com.varabyte.kobweb.silk.components.icons.fa.FaToolbox
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.extendedByBase
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import dev.tonholo.s2c.website.theme.palette
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

private const val REPOSITORY_NEW_ISSUE_URL = "https://github.com/rafaeltonholo/svg-to-compose/issues/new"

@Composable
internal fun FeedbackButtonRow(state: PlaygroundState, modifier: Modifier = Modifier) {
    Row(
        modifier = FeedbackButtonRowStyle.toModifier().then(modifier),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Link(
            path = "$REPOSITORY_NEW_ISSUE_URL?template=enhancement.yml&label=enhancement&title=%5BEnhancement%5D%3A%20",
            variant = FeedbackLinkVariant,
        ) {
            Text("Ask for a feature")
            FaToolbox()
        }
        Link(
            path = state.buildBugReportUrl(),
            variant = FeedbackLinkVariant,
        ) {
            Text("Report a bug")
            FaBug()
        }
    }
}

val FeedbackButtonRowStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding {
                top(SiteTheme.dimensions.size.Sm)
            }
            .gap(SiteTheme.dimensions.size.Md)
    }

    cssRule(" :where(:not(:last-child))") {
        Modifier
            .borderRight {
                width(1.px)
                style(LineStyle.Solid)
                color(palette.outline)
            }
            .padding {
                right(SiteTheme.dimensions.size.Md)
            }
    }
}

val FeedbackLinkVariant = SiteLinkStyleVariant.extendedByBase {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(SiteTheme.dimensions.size.Sm)
}

private const val INPUT_TRUNCATE_LIMIT = 500

private fun PlaygroundState.buildBugReportUrl(): String {
    val environment = buildEnvironment()
    val reproductionSteps = buildReproductionSteps()
    val inputOutput = buildInputOutput()
    val additionalContext = buildAdditionalContext()

    return buildString {
        append(REPOSITORY_NEW_ISSUE_URL)
        append("?template=bug_report.yml")
        append("&title=")
        append(encodeURIComponent("[Bug]: "))
        append("&environment=")
        append(encodeURIComponent(environment))
        append("&reproduction-steps=")
        append(encodeURIComponent(reproductionSteps))
        if (inputOutput.isNotBlank()) {
            append("&input-output=")
            append(encodeURIComponent(inputOutput))
        }
        append("&additional-context=")
        append(encodeURIComponent(additionalContext))
    }
}

private fun buildEnvironment(): String = buildString {
    appendLine("- Platform: Website Playground")
    appendLine("- svg-to-compose version: ${SiteTheme.VERSION}")
    appendLine("- Browser: ${window.navigator.userAgent}")
}

private fun PlaygroundState.buildReproductionSteps(): String = buildString {
    val source = when {
        selectedSample >= 0 -> "Selected sample: \"$selectedSampleName\""
        inputMode == "paste" -> "Pasted $extension content"
        else -> "Uploaded file(s): ${uploadedFiles.joinToString { it.name }}"
    }
    appendLine("1. Go to the SVG-to-Compose Playground")
    appendLine("2. $source")
    appendLine(
        "3. Set options: optimize=${options.optimize}, minified=${options.minified}, " +
            "kmpPreview=${options.kmpPreview}, noPreview=${options.noPreview}, " +
            "makeInternal=${options.makeInternal}",
    )
    if (options.pkg != "com.example.icons") {
        appendLine("   - Package: ${options.pkg}")
    }
    if (options.theme != "com.example.theme.AppTheme") {
        appendLine("   - Theme: ${options.theme}")
    }
    if (options.receiverType.isNotBlank()) {
        appendLine("   - Receiver type: ${options.receiverType}")
    }
    appendLine("4. Click Convert")
    appendLine("5. See error / unexpected output")
}

private fun PlaygroundState.buildInputOutput(): String = buildString {
    if (inputCode.isNotBlank()) {
        val truncatedInput = if (inputCode.length > INPUT_TRUNCATE_LIMIT) {
            inputCode.take(INPUT_TRUNCATE_LIMIT) + "\n... (truncated, ${inputCode.length} chars total)"
        } else {
            inputCode
        }
        appendLine("**Input ($extension, file: $inputFileName):**")
        appendLine("```$extension")
        appendLine(truncatedInput)
        appendLine("```")
    }
    if (conversionError != null) {
        appendLine()
        appendLine("**Conversion Error:**")
        appendLine("```")
        appendLine(conversionError)
        appendLine("```")
    } else if (convertedKotlinCode.isNotBlank()) {
        val truncatedOutput = if (convertedKotlinCode.length > INPUT_TRUNCATE_LIMIT) {
            convertedKotlinCode.take(INPUT_TRUNCATE_LIMIT) +
                "\n... (truncated, ${convertedKotlinCode.length} chars total)"
        } else {
            convertedKotlinCode
        }
        appendLine()
        appendLine("**Output (file: $outputFileName):**")
        appendLine("```kotlin")
        appendLine(truncatedOutput)
        appendLine("```")
    }
}

private fun PlaygroundState.buildAdditionalContext(): String = buildString {
    appendLine("**Playground State:**")
    appendLine("- Input mode: $inputMode")
    appendLine("- Extension: $extension")
    appendLine("- Selected sample: $selectedSampleName")
    if (isBatchMode) {
        appendLine(
            "- Batch mode: active (${uploadedFiles.size} files uploaded, " +
                "${fileGroups.size} groups)",
        )
        appendLine("- Viewing batch result: $viewingBatchResult (index: $viewingBatchIndex)")
    }
    appendLine("- Preview expanded: $previewExpanded")
    appendLine("- Zoom level: $zoomLevel")
}
