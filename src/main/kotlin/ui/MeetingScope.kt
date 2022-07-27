package ui

import analysis.LogLine
import analysis.groupByRenderSlotID
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun MeetingScope(
    logLines: List<LogLine>,
) {
    val linesOfRenderSlotID = remember(logLines) {
        groupByRenderSlotID(logLines)
    }

    val scrollState = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {
        Column {
            linesOfRenderSlotID.forEach { entry ->
                RenderSlotScope(entry.key, entry.value)
            }

            logLines.forEach { line ->
                Row {
                    val time = line.formatTime()
                    SelectionContainer {
                        Text("$time  -  ${line.message}")
                    }
                }
            }
        }
    }
}
