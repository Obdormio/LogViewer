package me.lidafan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import me.lidafan.patches.DropProvider

@Composable
fun AppWrapper(window: ComposeWindow, content: @Composable BoxScope.() -> Unit) {
    DropProvider(window = window) {
        MaterialTheme(
            lightColorScheme(
                background = Color(238, 238, 238)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background),
                content = content
            )
        }
    }
}
