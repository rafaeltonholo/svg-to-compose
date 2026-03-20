package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.geometry.Offset
import dev.tonholo.s2c.domain.compose.ComposeOffset

/** Converts a [ComposeOffset] domain value to a Compose geometry [Offset][Offset]. */
internal fun ComposeOffset.toOffset(): Offset {
    val xVal = x
    val yVal = y
    return when {
        this == ComposeOffset.Infinite -> Offset.Infinite
        this == ComposeOffset.Zero -> Offset.Zero
        xVal != null && yVal != null -> Offset(xVal, yVal)
        else -> Offset.Unspecified
    }
}
