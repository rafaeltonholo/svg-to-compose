package dev.tonholo.svg_to_compose.playground.kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tonholo.svg_to_compose.playground.kmp.ui.Theme
import dev.tonholo.svg_to_compose.playground.kmp.ui.icons.compose_resources.ComposeMultiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playground_kmp.composeapp.generated.resources.Res
import playground_kmp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    Theme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("From Resources:")
            Image(
                painter = painterResource(resource = Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
            Text("From SVG/XML to Compose Plugin:")
            Image(
                imageVector = ComposeMultiplatform,
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
        }
    }
}
