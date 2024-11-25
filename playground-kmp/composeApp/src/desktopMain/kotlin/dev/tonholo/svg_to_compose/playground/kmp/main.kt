package dev.tonholo.svg_to_compose.playground.kmp

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