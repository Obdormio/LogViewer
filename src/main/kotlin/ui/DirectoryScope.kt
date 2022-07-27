package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import utils.useState
import java.io.File

@Composable
fun DirectoryScope(directory: File) {
    val (files, setFiles) = useState<Array<File>>(emptyArray())

    LaunchedEffect(directory) {
        directory.listFiles()?.let { setFiles(it) }
    }

    Column {
        files.forEach { file ->
            Card {
                Text(file.name)
            }
        }
    }
}
