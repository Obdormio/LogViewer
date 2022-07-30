package me.lidafan.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.lidafan.entities.Line
import me.lidafan.entities.readLinesFromFile
import me.lidafan.ui.common.Chips
import java.io.File

@Composable
fun FileView(file: File) {
    val lazyListState = rememberLazyListState()
    val (lines, setLines) = remember { mutableStateOf(listOf<Line>()) }

    LaunchedEffect(file) {
        readLinesFromFile(file).let(setLines)
    }

    Box {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
            items(lines) {
                Row(modifier = Modifier.heightIn(min = 30.dp)) {
                    val parsedLine = it.parsedLine
                    if (parsedLine == null) {
                        Text(it.text, overflow = TextOverflow.Ellipsis, softWrap = false)
                        return@Row
                    }

                    Chips {
                        chip(parsedLine.formatTime())
                        chip(parsedLine.pid)
                        chip(parsedLine.label)
                        chip(parsedLine.route)

                        val message = if (parsedLine.message.length > 100) {
                            parsedLine.message.substring(0, 100)
                        } else {
                            parsedLine.message
                        }
                        val backgroundColor = when (parsedLine.level.lowercase()) {
                            "info" -> Color.Gray
                            "warn" -> Color.Yellow
                            "error" -> Color.Red
                            else -> Color.Red
                        }

                        chip(message, backgroundColor = backgroundColor)
                    }
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(lazyListState)
        )
    }
}
