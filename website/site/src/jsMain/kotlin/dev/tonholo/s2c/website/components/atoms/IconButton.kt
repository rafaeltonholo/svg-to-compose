package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import dev.tonholo.s2c.website.CircleButtonVariant
import dev.tonholo.s2c.website.UncoloredButtonVariant
import org.jetbrains.compose.web.css.em

@Composable
fun IconButton(
    onClick: () -> Unit,
    ariaLabel: String? = null,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = { onClick() },
        Modifier
            .setVariable(ButtonVars.FontSize, 1.em)
            .let { mod -> if (ariaLabel != null) mod.ariaLabel(ariaLabel) else mod },
        variant = CircleButtonVariant.then(UncoloredButtonVariant),
    ) {
        content()
    }
}
