package me.lidafan.patches.modifier

import androidx.compose.ui.Modifier

@Suppress("unused")
fun Modifier.thenOrNull(other: Modifier?): Modifier {
    if (other == null) {
        return this
    }
    return this.then(other)
}
