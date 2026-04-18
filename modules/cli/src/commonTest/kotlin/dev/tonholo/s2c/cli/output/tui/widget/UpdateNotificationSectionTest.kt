package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.state.UpdateNotificationState
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse

class UpdateNotificationSectionTest {

    private val terminal = Terminal(ansiLevel = AnsiLevel.NONE)

    @Test
    fun `given wrapper detected - when updateNotificationSection called - then shows upgrade command`() {
        // Arrange
        val state = UpdateNotificationState(
            currentVersion = "2.2.0",
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0",
            isWrapper = true,
        )

        // Act
        val widget = updateNotificationSection(state = state)
        val rendered = terminal.render(widget)

        // Assert
        assertContains(rendered, "2.2.0")
        assertContains(rendered, "2.3.0")
        assertContains(rendered, "s2c --upgrade")
        assertContains(rendered, "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0")
    }

    @Test
    fun `given direct binary - when updateNotificationSection called - then shows download link`() {
        // Arrange
        val state = UpdateNotificationState(
            currentVersion = "2.2.0",
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0",
            isWrapper = false,
        )

        // Act
        val widget = updateNotificationSection(state = state)
        val rendered = terminal.render(widget)

        // Assert
        assertContains(rendered, "2.2.0")
        assertContains(rendered, "2.3.0")
        assertContains(rendered, "Download")
        assertContains(rendered, "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0")
    }

    @Test
    fun `given wrapper detected - when updateNotificationSection called - then does not show Download label`() {
        // Arrange
        val state = UpdateNotificationState(
            currentVersion = "1.0.0",
            latestVersion = "2.0.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.0.0",
            isWrapper = true,
        )

        // Act
        val widget = updateNotificationSection(state = state)
        val rendered = terminal.render(widget)

        // Assert
        assertContains(rendered, "s2c --upgrade")
        assertFalse(
            actual = rendered.contains(other = "Download:"),
            message = "wrapper branch should not render the Download label, got:\n$rendered",
        )
    }

    @Test
    fun `given direct binary - when updateNotificationSection called - then does not show upgrade command`() {
        // Arrange
        val state = UpdateNotificationState(
            currentVersion = "1.0.0",
            latestVersion = "2.0.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.0.0",
            isWrapper = false,
        )

        // Act
        val widget = updateNotificationSection(state = state)
        val rendered = terminal.render(widget)

        // Assert
        assertContains(rendered, "Download:")
        assertFalse(
            actual = rendered.contains(other = "s2c --upgrade"),
            message = "non-wrapper branch should not render the upgrade command, got:\n$rendered",
        )
    }
}
