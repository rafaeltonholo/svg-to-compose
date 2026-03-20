package dev.tonholo.s2c.website.editor.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Applies a checkerboard pattern background to the modifier.
 *
 * Draws a grid of alternating colored rectangles behind the content,
 * commonly used to indicate transparency or provide visual contrast
 * for icons and images.
 *
 * @param checkerColor A pair of colors to alternate in the checkerboard
 * pattern. The first color is used for cells where (row + column) is even,
 * and the second color is used for cells where (row + column) is odd.
 * @param cellSize The size of each square cell in the checkerboard grid.
 * @return A modifier that draws a checkerboard pattern background.
 */
internal fun Modifier.checkerboardBackground(
    checkerColor: Pair<Color, Color>,
    cellSize: Dp,
): Modifier = this then Modifier
    .drawBehind {
        val (color1, color2) = checkerColor
        val cellSize = cellSize.toPx()
        val cols = (size.width / cellSize).toInt() + 1
        val rows = (size.height / cellSize).toInt() + 1
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                drawRect(
                    color = if ((row + col) % 2 == 0) color1 else color2,
                    topLeft = Offset(col * cellSize, row * cellSize),
                    size = Size(cellSize, cellSize),
                )
            }
        }
    }
