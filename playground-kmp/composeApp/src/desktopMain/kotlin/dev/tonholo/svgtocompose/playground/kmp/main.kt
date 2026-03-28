package dev.tonholo.svgtocompose.playground.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "playground-kmp",
    ) {
        App()
    }
}
