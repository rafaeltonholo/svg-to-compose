package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.components.organisms.playground.DEFAULT_TEMPLATE_TOML
import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState

internal class TemplateReducer : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.UpdateTemplateToml -> state.copy(templateToml = action.toml)

        is PlaygroundAction.TemplateValidated -> state.copy(
            templateErrors = action.errors,
        )

        is PlaygroundAction.TemplateFileLoaded -> state.copy(
            templateToml = action.content,
            templateErrors = emptyList(),
        )

        is PlaygroundAction.ClearTemplate -> state.copy(
            templateToml = DEFAULT_TEMPLATE_TOML,
            templateErrors = emptyList(),
        )

        is PlaygroundAction.ChangeTemplateExpanded -> state.copy(templateExpanded = action.expanded)

        else -> state
    }
}
