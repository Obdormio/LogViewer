package ui

import analysis.findLogLines
import analysis.groupByCoreID
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.useState
import java.io.File

fun makeTopBar(title: () -> String, onReselectFile: () -> Unit): @Composable () -> Unit {
    return {
        SmallTopAppBar(
            title = {
                Text(title())
            },
            actions = {
                TextButton(
                    onClick = onReselectFile,
                ) {
                    Text("重新选择")
                }
            }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun LogFileScope(file: File, onReselectFile: () -> Unit) {
    val logLines by remember {
        derivedStateOf {
            findLogLines(file)
        }
    }

    val (coreID, setCoreID) = useState<String?>(null)

    val linesOfCoreID = remember(logLines) {
        groupByCoreID(logLines)
    }

    val topBar = makeTopBar(
        title = { file.name },
        onReselectFile = onReselectFile
    )

    Scaffold(
        topBar = topBar
    ) {
        Column(modifier = Modifier.padding(15.dp).fillMaxSize()) {
            MeetingSelector(linesOfCoreID, setCoreID, coreID)

            if (coreID != null) {
                MeetingScope(
                    logLines = linesOfCoreID[coreID]!!
                )
            }
        }
    }
}
