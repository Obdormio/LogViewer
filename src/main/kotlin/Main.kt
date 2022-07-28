import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import patches.DropProvider
import ui.DirectoryScope
import ui.DropFile
import ui.LogFileScope
import utils.useState
import java.io.File

@ExperimentalMaterial3Api
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

    if (isDirectory) {
        DirectoryScope(file, onBack = {
            setFile(null)
        })
    } else {
        LogFileScope(file)
    }
}

@ExperimentalMaterial3Api
fun main() {
    application {
        Window(
            title = "Log Viewer",
            onCloseRequest = this::exitApplication,
        ) {
            DropProvider(window = this.window) {
                MaterialTheme(
                    colorScheme = lightColorScheme(
                        background = Color(242, 240, 224),
                        primary = Color(8, 87, 134),
                    )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        App()
                    }
                }
            }
        }
    }
}
