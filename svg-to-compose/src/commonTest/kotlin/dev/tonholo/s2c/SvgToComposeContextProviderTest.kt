package dev.tonholo.s2c

import dev.tonholo.s2c.runtime.S2cConfig
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SvgToComposeContextProviderTest {
    @AfterTest
    fun tearDown() {
        SvgToComposeContextProvider.reset()
    }

    @Test
    fun `given uninitialized provider - when current is accessed - then throws IllegalStateException`() {
        // Arrange - provider is not initialized (fresh state after reset)

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            SvgToComposeContextProvider.current
        }
    }

    @Test
    fun `given initialized provider - when current is accessed - then returns the context`() {
        // Arrange
        val config = object : S2cConfig {
            override val debug: Boolean = false
            override val verbose: Boolean = false
            override val silent: Boolean = false
            override val stackTrace: Boolean = false
        }
        val context = SvgToComposeContextImpl(initial = config)
        SvgToComposeContextProvider.initialize(context)

        // Act
        val result = SvgToComposeContextProvider.current

        // Assert
        assertNotNull(result)
        assertEquals(context, result)
    }

    @Test
    fun `given uninitialized provider - when currentOrNull is accessed - then returns null`() {
        // Arrange - provider is not initialized (fresh state after reset)

        // Act
        val result = SvgToComposeContextProvider.currentOrNull

        // Assert
        assertNull(result)
    }

    @Test
    fun `given initialized provider - when currentOrNull is accessed - then returns the context`() {
        // Arrange
        val config = object : S2cConfig {
            override val debug: Boolean = false
            override val verbose: Boolean = false
            override val silent: Boolean = false
            override val stackTrace: Boolean = false
        }
        val context = SvgToComposeContextImpl(initial = config)
        SvgToComposeContextProvider.initialize(context)

        // Act
        val result = SvgToComposeContextProvider.currentOrNull

        // Assert
        assertNotNull(result)
        assertEquals(context, result)
    }

    @Test
    fun `given initialized provider - when reset is called - then current throws IllegalStateException`() {
        // Arrange
        val config = object : S2cConfig {
            override val debug: Boolean = false
            override val verbose: Boolean = false
            override val silent: Boolean = false
            override val stackTrace: Boolean = false
        }
        val context = SvgToComposeContextImpl(initial = config)
        SvgToComposeContextProvider.initialize(context)

        // Act
        SvgToComposeContextProvider.reset()

        // Assert
        assertFailsWith<IllegalStateException> {
            SvgToComposeContextProvider.current
        }
    }
}
