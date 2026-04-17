package dev.tonholo.s2c.cli.output.tui.state

import dev.tonholo.s2c.output.ConversionPhase

/**
 * State for the currently-processing file section of the TUI.
 *
 * @property fileName the name of the file being processed.
 * @property currentPhase the phase the file is currently in.
 * @property completedPhases phases already completed for this file.
 * @property optimizationEnabled whether the optimization phase is active.
 */
internal data class CurrentFileState(
    val fileName: String,
    val currentPhase: ConversionPhase,
    val completedPhases: Set<ConversionPhase> = emptySet(),
    val optimizationEnabled: Boolean = true,
)
