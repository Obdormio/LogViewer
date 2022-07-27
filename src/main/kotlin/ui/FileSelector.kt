package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun FileSelector(
    parent: Frame? = null, title: String = "Select File", onClose: (directory: String?, filename: String?) -> Unit
) {
    AwtWindow(
        create = {
            object : FileDialog(parent, title, LOAD) {
                override fun setVisible(visible: Boolean) {
                    if (!visible) {
                        onClose(this.directory, this.file)
                    }
                    super.setVisible(visible)
                }
            }
        }, dispose = FileDialog::dispose
    )
}
