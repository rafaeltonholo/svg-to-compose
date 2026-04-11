package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.animation.Animation
import com.github.ajalt.mordant.animation.animation
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.reducer.reduce
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.cli.output.tui.widget.dashboardWidget
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class TuiRenderer(private val terminal: Terminal) : OutputRenderer {
    private val _state = MutableStateFlow(TuiState())
    val state: StateFlow<TuiState> = _state.asStateFlow()

    private val animation: Animation<TuiState> = terminal.animation { s ->
        dashboardWidget(state = s, terminalWidth = terminal.size.width)
    }

    override fun onEvent(event: ConversionEvent) {
        _state.update { current -> reduce(state = current, event = event) }
    }

    fun toggleHeader() {
        _state.update { current ->
            current.copy(
                header = current.header.copy(expanded = !current.header.expanded),
            )
        }
    }

    suspend fun run() {
        state.collect { s -> animation.update(s) }
    }

    fun stop() {
        animation.stop()
    }
}
