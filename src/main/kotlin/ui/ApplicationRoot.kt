package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.useDerivedState
import utils.useState
import java.io.File

@ExperimentalMaterial3Api
@Composable
fun ApplicationRoot() {
    val (file, setFile) = useState<File?>(null)

    if (file == null) {
        DropFile(setFile)
        return
    }

    // 判断 isDirectory 是一个 io 操作, 缓存起来
    val isDirectory by useDerivedState { file.isDirectory }

    if (isDirectory) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("暂不支持文件夹", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.size(12.dp))
                Button(onClick = {
                    setFile(null)
                }) {
                    Text("重新选择")
                }
            }
        }
        return
    }

    LogFileScope(
        file = file,
        onReselectFile = {
            setFile(null)
        }
    )
}
