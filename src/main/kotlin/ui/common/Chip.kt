package ui.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun Chip(text: String, color: Color = Color.Cyan) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(color).padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Text(text, fontFamily = FontFamily.Monospace, lineHeight = 1.em)
    }
}

data class ChipConfig(val text: String, val color: Color)

class ChipsScope {
    private val chipConfigs = arrayListOf<ChipConfig>()

    fun chip(text: String, color: Color = Color.Cyan) {
        this.chipConfigs += ChipConfig(text, color)
    }

    fun content(): @Composable RowScope.() -> Unit {
        return {
            chipConfigs.forEachIndexed { index, config ->
                if (index > 0) {
                    Gap(8)
                }
                Chip(config.text, config.color)
            }
        }
    }
}

@Composable
fun Chips(content: ChipsScope.() -> Unit) {
    val chipsScope = ChipsScope().apply(content)

    Row(content = chipsScope.content())
}

@Preview
@Composable
fun PreviewChips() {
    MaterialTheme(
    ) {
        Chips {
            chip("Hello")
            chip("World", Color.Green)
            chip("Shit")
        }
    }
}
