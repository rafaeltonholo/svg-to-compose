package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File

/**
 * Hidden file input that can be triggered programmatically via [inputRef].
 *
 * @param accept File types to accept (e.g. ".svg,.xml").
 * @param directory When true, enables directory selection via `webkitdirectory`.
 * @param multiple When true, allows selecting multiple files.
 * @param inputRef Callback receiving the [HTMLInputElement] for programmatic `.click()`.
 * @param onSelectFiles Called with the list of selected [File]s.
 */
@Composable
fun FilePickerInput(
    inputRef: (HTMLInputElement?) -> Unit,
    onSelectFiles: (List<File>) -> Unit,
    modifier: Modifier = Modifier,
    accept: String = ".svg,.xml",
    directory: Boolean = false,
    multiple: Boolean = true,
) {
    Input(
        type = InputType.File,
        attrs = modifier
            .display(DisplayStyle.None)
            .toAttrs {
                attr("accept", accept)
                if (multiple) attr("multiple", "")
                if (directory) attr("webkitdirectory", "")
                ref { input ->
                    inputRef(input)
                    input.onchange = {
                        val files = input.files
                        if (files != null) {
                            val fileList = (0 until files.length).mapNotNull { i ->
                                files.item(i)
                            }
                            onSelectFiles(fileList)
                        }
                        input.value = ""
                        null
                    }
                    onDispose {
                        inputRef(null)
                        input.onchange = null
                    }
                }
            },
    )
}
