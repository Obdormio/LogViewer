import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppWrapper
import ui.ApplicationRoot

@ExperimentalMaterial3Api
fun main() {
    application {
        Window(
            title = "Log Viewer",
            onCloseRequest = this::exitApplication,
        ) {
            AppWrapper(window = this.window) {
                ApplicationRoot()
            }
        }
    }
}
