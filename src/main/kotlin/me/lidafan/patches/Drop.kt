package me.lidafan.patches

import androidx.compose.runtime.*
import androidx.compose.ui.awt.ComposeWindow
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetAdapter
import java.awt.dnd.DropTargetDropEvent
import java.io.File

val LocalDropTarget = staticCompositionLocalOf { DropTarget() }

@Composable
fun DropProvider(window: ComposeWindow, content: @Composable () -> Unit) {
    val localDropTarget = remember { DropTarget() }

    LaunchedEffect(true) {
        window.contentPane.dropTarget = localDropTarget
    }

    CompositionLocalProvider(LocalDropTarget provides localDropTarget, content = content)
}

@Composable
fun useDropFile(onDrop: (file: File) -> Unit) {
    val dropTarget = LocalDropTarget.current

    DisposableEffect(true) {
        val dropHandler = object : DropTargetAdapter() {
            override fun drop(event: DropTargetDropEvent?) {
                if (event == null) {
                    return
                }

                event.acceptDrop(DnDConstants.ACTION_COPY)
                val transferable = event.transferable
                val flavors = transferable.transferDataFlavors
                if (!flavors.contains(DataFlavor.javaFileListFlavor)) {
                    println("DragTarget does not support")
                    return
                }
                val data = transferable.getTransferData(DataFlavor.javaFileListFlavor)
                if (data !is List<*>) {
                    println("TransferData is not list")
                    return
                }
                val file = data[0]
                if (file !is File) {
                    println("TransferData is not File")
                    return
                }
                onDrop(file)
            }
        }

        dropTarget.addDropTargetListener(dropHandler)

        this.onDispose {
            dropTarget.removeDropTargetListener(dropHandler)
        }
    }
}
