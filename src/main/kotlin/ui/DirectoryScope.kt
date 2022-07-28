package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.useState
import java.io.File

private data class FileInfo(
    val name: String,
    val isDirectory: Boolean,
    val file: File,
) {
    companion object {
        fun fromFile(file: File): FileInfo {
            return FileInfo(
                name = file.name,
                isDirectory = file.isDirectory,
                file = file,
            )
        }
    }
}

private fun listFiles(directory: File) = directory
    .listFiles()?.map(FileInfo::fromFile)?.filter { fileInfo ->
        !fileInfo.name.startsWith('.')
    }
    ?.sortedBy { fileInfo ->
        if (fileInfo.isDirectory) {
            -1
        } else {
            fileInfo.name[0].code
        }
    }

private fun makeTopBar(title: String, onBack: () -> Unit): @Composable () -> Unit {
    return {
        SmallTopAppBar(
            title = { Text(title) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            actions = {
                TextButton(onClick = onBack) {
                    Text("重新选择")
                }
            }
        )
    }
}

private fun makeContent(files: List<FileInfo>): @Composable (paddingValues: PaddingValues) -> Unit {
    return { paddingValues ->
        val scrollState = rememberScrollState(0)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .verticalScroll(scrollState),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                files.forEach { file ->
                    Button(
                        onClick = {}
                    ) {
                        if (file.isDirectory) {
                            Icon(imageVector = Icons.Rounded.Folder, contentDescription = null)
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        }
                        Text(file.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DirectoryScope(directory: File, onBack: () -> Unit) {
    val (files, setFiles) = useState<List<FileInfo>>(listOf())

    LaunchedEffect(directory) {
        listFiles(directory)?.let {
            setFiles(it)
        }
    }

    Scaffold(
        topBar = makeTopBar(title = directory.name, onBack = onBack),
        content = makeContent(files)
    )
}
