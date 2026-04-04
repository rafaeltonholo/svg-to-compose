package dev.tonholo.s2c.website.state.playground

import dev.tonholo.s2c.website.validator.playground.template.TemplateTomlValidator
import kotlinx.browser.window

/**
 * Encapsulates template validation and persistence scheduling logic,
 * extracted from [PlaygroundViewModel] to keep the class focused on
 * coordination.
 *
 * @param readState Returns the current [PlaygroundState] snapshot.
 * @param dispatch Dispatches a [PlaygroundAction] through the reducer.
 */
internal class TemplatePersistenceHelper(
    private val readState: () -> PlaygroundState,
    private val dispatch: (PlaygroundAction) -> Unit,
) {
    private var templateValidationTimer: Int? = null
    private var persistenceTimer: Int? = null

    fun restorePersistedState() {
        val (options, templateToml) = PlaygroundPersistence.load() ?: return
        dispatch(PlaygroundAction.ChangeOptions(options))
        if (!templateToml.isNullOrBlank()) {
            dispatch(PlaygroundAction.UpdateTemplateToml(templateToml))
        }
    }

    fun scheduleTemplateValidation() {
        templateValidationTimer?.let { window.clearTimeout(it) }
        templateValidationTimer = window.setTimeout(
            ::validateTemplate,
            TEMPLATE_VALIDATION_DEBOUNCE_MS,
        )
    }

    fun schedulePersistence() {
        persistenceTimer?.let { window.clearTimeout(it) }
        persistenceTimer = window.setTimeout(
            handler = {
                val state = readState()
                val toml = state.templateToml.takeIf { it.isNotBlank() }
                PlaygroundPersistence.save(state.options, toml)
            },
            timeout = PERSISTENCE_DEBOUNCE_MS,
        )
    }

    private fun validateTemplate() {
        val toml = readState().templateToml
        if (toml.isTemplateStructuralOnly()) {
            dispatch(PlaygroundAction.TemplateValidated(errors = emptyList()))
            return
        }
        val errors = TemplateTomlValidator.validate(toml)
        dispatch(PlaygroundAction.TemplateValidated(errors = errors))
    }

    private companion object {
        const val TEMPLATE_VALIDATION_DEBOUNCE_MS = 300
        const val PERSISTENCE_DEBOUNCE_MS = 500
    }
}
