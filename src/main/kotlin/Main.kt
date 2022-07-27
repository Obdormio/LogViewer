import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import patches.DropProvider
import ui.DirectoryScope
import ui.DropFile
import ui.LogFileScope
import utils.useState
import java.io.File

@Composable
fun App() {
    val (file, setFile) = useState<File?>(null)

    if (file == null) {
        DropFile(onReceiveFile = setFile)
        return
    }

    val isDirectory by remember {
        derivedStateOf {
            file.isDirectory
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(15.dp)
    ) {
        Button({ setFile(null) }) {
            Text("Back")
        }

        if (isDirectory) {
            DirectoryScope(file)
        } else {
            LogFileScope(file)
        }
    }
}

fun main() {
    application {
        Window(onCloseRequest = this::exitApplication) {
            DropProvider(window = this.window) {
                App()
            }
        }
    }
}
