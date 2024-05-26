package dev.tonholo.s2c.geom.bounds

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BoundingBoxTest {
    @Test
    fun `ensure BoundingBox creation with valid coordinates works`() {
        val boundingBox = BoundingBox(minX = 1.0, minY = 2.0, maxX = 3.0, maxY = 4.0)
        assertFalse(boundingBox.minX.isNaN())
        assertFalse(boundingBox.minY.isNaN())
        assertFalse(boundingBox.maxX.isNaN())
        assertFalse(boundingBox.maxY.isNaN())
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 2.0, actual = boundingBox.width)
        assertEquals(expected = 2.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure bounding box creation with NaN coordinates return no bounding box`() {
        val boundingBox = BoundingBox(Double.NaN, 2.0, 3.0, 4.0)
        assertTrue(boundingBox is BoundingBox.NoBoundingBox)
    }

    @Test
    fun `ensure copy method creates a new BoundingBox with the specified changes`() {
        val boundingBox = BoundingBox(minX = 1.0, minY = 2.0, maxX = 3.0, maxY = 4.0)
        val newBoundingBox = boundingBox.copy(minX = 1.5, maxY = 4.5)
        assertEquals(expected = 1.5, actual = newBoundingBox.x)
        assertEquals(expected = 2.0, actual = newBoundingBox.y)
        assertEquals(expected = 1.5, actual = newBoundingBox.width)
        assertEquals(expected = 2.5, actual = newBoundingBox.height)
    }
}
