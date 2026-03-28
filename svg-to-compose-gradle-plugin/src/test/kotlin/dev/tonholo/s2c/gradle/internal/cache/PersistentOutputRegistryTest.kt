package dev.tonholo.s2c.gradle.internal.cache

import java.io.File
import java.nio.file.Files
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PersistentOutputRegistryTest {
    private lateinit var tempDir: File

    @BeforeTest
    fun setUp() {
        tempDir = Files.createTempDirectory("persistent-output-registry-test").toFile()
    }

    @AfterTest
    fun tearDown() {
        tempDir.deleteRecursively()
    }

    private fun createRegistry(): PersistentOutputRegistry {
        val registryFile = tempDir.resolve("persistent-output-registry.properties")
        return PersistentOutputRegistry(registryFile)
    }

    @Test
    fun `given empty registry, when getOutput called, then returns null`() {
        // Arrange
        val registry = createRegistry()

        // Act
        val result = registry.getOutput("/path/to/icon.svg")

        // Assert
        assertNull(result)
    }

    @Test
    fun `given entry registered, when getOutput called, then returns output path`() {
        // Arrange
        val registry = createRegistry()
        registry.register("/path/to/icon.svg", "/src/main/kotlin/Icon.kt")

        // Act
        val result = registry.getOutput("/path/to/icon.svg")

        // Assert
        assertEquals("/src/main/kotlin/Icon.kt", result)
    }

    @Test
    fun `given entry registered, when remove called, then getOutput returns null`() {
        // Arrange
        val registry = createRegistry()
        registry.register("/path/to/icon.svg", "/src/main/kotlin/Icon.kt")

        // Act
        registry.remove("/path/to/icon.svg")

        // Assert
        assertNull(registry.getOutput("/path/to/icon.svg"))
    }

    @Test
    fun `given entries registered, when save and reload, then entries persist`() {
        // Arrange
        val registryFile = tempDir.resolve("persistent-output-registry.properties")
        val registry = PersistentOutputRegistry(registryFile)
        registry.register("/path/to/a.svg", "/src/main/kotlin/A.kt")
        registry.register("/path/to/b.svg", "/src/main/kotlin/B.kt")

        // Act
        registry.save()
        val reloaded = PersistentOutputRegistry(registryFile)

        // Assert
        assertEquals("/src/main/kotlin/A.kt", reloaded.getOutput("/path/to/a.svg"))
        assertEquals("/src/main/kotlin/B.kt", reloaded.getOutput("/path/to/b.svg"))
    }

    @Test
    fun `given registry file does not exist, when created, then starts empty`() {
        // Arrange
        val registryFile = tempDir.resolve("nonexistent.properties")
        assertFalse(registryFile.exists())

        // Act
        val registry = PersistentOutputRegistry(registryFile)

        // Assert
        assertTrue(registry.allEntries().isEmpty())
    }

    @Test
    fun `given entries registered, when allEntries called, then returns all entries`() {
        // Arrange
        val registry = createRegistry()
        registry.register("/a.svg", "/A.kt")
        registry.register("/b.svg", "/B.kt")

        // Act
        val entries = registry.allEntries()

        // Assert
        assertEquals(2, entries.size)
        assertEquals("/A.kt", entries["/a.svg"])
        assertEquals("/B.kt", entries["/b.svg"])
    }

    @Test
    fun `given entries registered, when clear called, then allEntries is empty`() {
        // Arrange
        val registry = createRegistry()
        registry.register("/a.svg", "/A.kt")

        // Act
        registry.clear()

        // Assert
        assertTrue(registry.allEntries().isEmpty())
    }

    @Test
    fun `given cleared registry, when save and reload, then reloaded registry is empty`() {
        // Arrange
        val registryFile = tempDir.resolve("persistent-output-registry.properties")
        val registry = PersistentOutputRegistry(registryFile)
        registry.register("/a.svg", "/A.kt")
        registry.clear()

        // Act
        registry.save()
        val reloaded = PersistentOutputRegistry(registryFile)

        // Assert
        assertTrue(reloaded.allEntries().isEmpty())
    }

    @Test
    fun `given unreadable registry file, when created, then starts empty`() {
        // Arrange
        // Use a directory with the same name as the registry file so that
        // opening it as an InputStream throws a FileNotFoundException (IOException subtype),
        // exercising the try/catch fallback in the init block.
        val registryFile = tempDir.resolve("persistent-output-registry.properties")
        registryFile.mkdirs() // make it a directory so inputStream() throws IOException

        // Act
        val registry = PersistentOutputRegistry(registryFile)

        // Assert
        assertTrue(registry.allEntries().isEmpty())
    }
}
