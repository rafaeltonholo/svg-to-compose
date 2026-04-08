package dev.tonholo.s2c.cli.update

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SemVerTest {

    @Test
    fun `given valid vX_Y_Z string - when parse is called - then returns correct SemVer`() {
        // Arrange
        val input = "v2.3.1"

        // Act
        val result = SemVer.parse(input)

        // Assert
        assertEquals(expected = 2, actual = result?.major)
        assertEquals(expected = 3, actual = result?.minor)
        assertEquals(expected = 1, actual = result?.patch)
    }

    @Test
    fun `given valid X_Y_Z string - when parse is called - then returns correct SemVer`() {
        // Arrange
        val input = "2.3.1"

        // Act
        val result = SemVer.parse(input)

        // Assert
        assertEquals(expected = 2, actual = result?.major)
        assertEquals(expected = 3, actual = result?.minor)
        assertEquals(expected = 1, actual = result?.patch)
    }

    @Test
    fun `given invalid string - when parse is called - then returns null`() {
        // Arrange
        val inputs = listOf("abc", "1.2", "v1", "", "1.2.3.4", "v.1.2.3")

        // Act & Assert
        for (input in inputs) {
            assertNull(
                actual = SemVer.parse(input),
                message = "Expected null for input: '$input'",
            )
        }
    }

    @Test
    fun `given two versions - when compared - then newer version is greater`() {
        // Arrange
        val older = SemVer(major = 1, minor = 0, patch = 0)
        val newer = SemVer(major = 2, minor = 0, patch = 0)

        // Act & Assert
        assertTrue(newer > older)
        assertTrue(older < newer)
    }

    @Test
    fun `given same versions - when compared - then they are equal`() {
        // Arrange
        val version1 = SemVer(major = 1, minor = 2, patch = 3)
        val version2 = SemVer(major = 1, minor = 2, patch = 3)

        // Act
        val result = version1.compareTo(version2)

        // Assert
        assertEquals(expected = 0, actual = result)
    }

    @Test
    fun `given version with higher patch - when compared to lower patch - then it is greater`() {
        // Arrange
        val lower = SemVer(major = 1, minor = 2, patch = 3)
        val higher = SemVer(major = 1, minor = 2, patch = 4)

        // Act & Assert
        assertTrue(higher > lower)
    }

    @Test
    fun `given version with higher minor - when compared to lower minor - then it is greater`() {
        // Arrange
        val lower = SemVer(major = 1, minor = 2, patch = 9)
        val higher = SemVer(major = 1, minor = 3, patch = 0)

        // Act & Assert
        assertTrue(higher > lower)
    }

    @Test
    fun `given version with SNAPSHOT suffix - when parse is called - then returns correct SemVer`() {
        // Arrange
        val input = "3.0.0-SNAPSHOT"

        // Act
        val result = SemVer.parse(input)

        // Assert
        assertEquals(expected = 3, actual = result?.major)
        assertEquals(expected = 0, actual = result?.minor)
        assertEquals(expected = 0, actual = result?.patch)
    }

    @Test
    fun `given version with rc suffix - when parse is called - then returns correct SemVer`() {
        // Arrange
        val input = "v2.1.0-rc.1"

        // Act
        val result = SemVer.parse(input)

        // Assert
        assertEquals(expected = 2, actual = result?.major)
        assertEquals(expected = 1, actual = result?.minor)
        assertEquals(expected = 0, actual = result?.patch)
    }

    @Test
    fun `given version with build metadata - when parse is called - then returns correct SemVer`() {
        // Arrange
        val input = "1.2.3+build.456"

        // Act
        val result = SemVer.parse(input)

        // Assert
        assertEquals(expected = 1, actual = result?.major)
        assertEquals(expected = 2, actual = result?.minor)
        assertEquals(expected = 3, actual = result?.patch)
    }

    @Test
    fun `given stable version string - when isPreRelease is called - then returns false`() {
        // Arrange
        val version = "v2.3.0"

        // Act
        val result = SemVer.isPreRelease(version)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given version with rc suffix - when isPreRelease is called - then returns true`() {
        // Arrange
        val version = "v4.0.0-rc.1"

        // Act
        val result = SemVer.isPreRelease(version)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given version with SNAPSHOT suffix - when isPreRelease is called - then returns true`() {
        // Arrange
        val version = "3.0.0-SNAPSHOT"

        // Act
        val result = SemVer.isPreRelease(version)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given version with build metadata - when isPreRelease is called - then returns true`() {
        // Arrange
        val version = "1.2.3+build.456"

        // Act
        val result = SemVer.isPreRelease(version)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given plain version without v prefix - when isPreRelease is called - then returns false`() {
        // Arrange
        val version = "2.3.1"

        // Act
        val result = SemVer.isPreRelease(version)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given valid SemVer - when toString is called - then returns X_Y_Z format`() {
        // Arrange
        val semVer = SemVer(major = 2, minor = 3, patch = 1)

        // Act
        val result = semVer.toString()

        // Assert
        assertEquals(expected = "2.3.1", actual = result)
    }
}
