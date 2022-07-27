package ui

import analysis.findLogLines
import analysis.groupByCoreID
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.useState
import java.io.File


@Composable
fun LogFileScope(file: File) {
    val logLines by remember {
        derivedStateOf {
            findLogLines(file)
        }
    }

    val (coreID, setCoreID) = useState<String?>(null)

    val linesOfCoreID = remember(logLines) {
        groupByCoreID(logLines)
    }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        MeetingSelector(linesOfCoreID, setCoreID, coreID)

        if (coreID != null) {
            MeetingScope(
                logLines = linesOfCoreID[coreID]!!
            )
        }
    }
}
