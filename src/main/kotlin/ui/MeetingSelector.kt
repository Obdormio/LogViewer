package ui

import analysis.LogLine
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MeetingSelector(
    logLinesOfCoreID: LinkedHashMap<String, List<LogLine>>,
    onSelect: (coreID: String) -> Unit,
    coreID: String?
) {
    val scrollState = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Row {
            logLinesOfCoreID.onEachIndexed { index, entry ->
                if (index > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                val isSelected = entry.key == coreID
                val buttonBackgroundColor = if (isSelected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.secondary
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonBackgroundColor,
                    ),
                    onClick = {
                        onSelect(entry.key)
                    }) {
                    Text(entry.key)
                }
            }
        }
    }
}
