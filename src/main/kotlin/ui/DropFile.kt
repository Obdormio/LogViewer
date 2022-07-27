package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import patches.dashedBorder
import patches.useDropFile
import java.io.File

@Composable
fun DropFile(
    onReceiveFile: (file: File) -> Unit,
) {
    useDropFile(onDrop = onReceiveFile)

    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .dashedBorder(width = 2.dp, color = Color.Cyan, on = 8.dp, off = 8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "将日志文件拖拽至此处"
        )
    }
}
