package me.lidafan.ui.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import me.lidafan.patches.modifier.thenOrNull

typealias Handler = () -> Unit

@Composable
fun Chip(
    text: String,
    textColor: Color = Color.DarkGray,
    borderColor: Color = Color.Cyan,
    backgroundColor: Color = Color.Transparent,
    onClick: Handler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clickable = onClick?.let {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
        ) {
            onClick()
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .thenOrNull(clickable)
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp),
    ) {
        Text(
            text,
            fontFamily = FontFamily.Monospace,
            lineHeight = 1.em,
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            softWrap = false
        )
    }
}

@Preview
@Composable
fun PreviewChips() {
    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Chip("Hello")
            Chip("World", Color.Green)
            Chip("Shit")
        }
    }
}
