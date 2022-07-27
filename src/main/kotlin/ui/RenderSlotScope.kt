package ui

import analysis.LogLine
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RenderSlotScope(renderSlotID: String, logLines: List<LogLine>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(0.dp, 0.dp, 0.dp, 15.dp))
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            SelectionContainer {
                Text("RenderSlotID: $renderSlotID")
            }
            logLines.forEach { line ->
                SelectionContainer {
                    Text(line.message)
                }
            }
        }
    }
}
