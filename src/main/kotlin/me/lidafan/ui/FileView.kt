package me.lidafan.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.lidafan.entities.Line
import me.lidafan.entities.ParsedLine
import me.lidafan.entities.readLinesFromFile
import me.lidafan.ui.common.Chip
import java.io.File
import java.util.logging.Logger

@Suppress("unused")
private val logger = Logger.getLogger("FileView")

@Composable
fun FileView(file: File) {
    val lazyListState = rememberLazyListState()
    var lines by remember { mutableStateOf(listOf<Line>()) }
    LaunchedEffect(file) {
        lines = readLinesFromFile(file)
    }

    if (lines.isEmpty()) {
        return
    }

    val timeLineChipState = rememberTimeChipState(1000, lines)
    val labelChipsState = rememberLineChipsState { it.label }
    val pidChipsState = rememberLineChipsState { it.pid }
    val routeChipsState = rememberLineChipsState { it.route }

    Box {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
            items(lines) {
                Row(modifier = Modifier.heightIn(min = 30.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val parsedLine = it.parsedLine
                    if (parsedLine == null) {
                        Text(it.text, overflow = TextOverflow.Ellipsis, softWrap = false)
                        return@Row
                    }

                    LineChip(timeLineChipState, parsedLine)
                    LineChip(pidChipsState, parsedLine)
                    LineChip(labelChipsState, parsedLine)
                    LineChip(routeChipsState, parsedLine)

                    val message = if (parsedLine.message.length > 100) {
                        parsedLine.message.substring(0, 100)
                    } else {
                        parsedLine.message
                    }
                    val backgroundColor = when (parsedLine.level.lowercase()) {
                        "info" -> Color.Transparent
                        "warn" -> Color.Yellow
                        "error" -> Color.Red
                        else -> Color.Red
                    }

                    SelectionContainer {
                        Chip(message, backgroundColor = backgroundColor, borderColor = Color.Transparent)
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

interface ILineChipState {
    fun getChipText(line: ParsedLine): String

    fun isHighlight(line: ParsedLine): Boolean

    fun toggleHighlight(line: ParsedLine)
}

@Composable
fun LineChip(state: ILineChipState, line: ParsedLine) {
    val text = state.getChipText(line)
    val isHighlight = state.isHighlight(line)
    val background = if (isHighlight) Color.Cyan else Color.Transparent

    Chip(
        text = text,
        backgroundColor = background,
        onClick = {
            state.toggleHighlight(line)
        }
    )
}

class TimeLineChipState(private val threshold: Int, private val lines: List<Line>) : ILineChipState {
    private val highlight = mutableStateListOf<IntRange>()

    override fun getChipText(line: ParsedLine): String {
        return line.formatTime()
    }

    override fun isHighlight(line: ParsedLine): Boolean {
        val timestamp = line.getTimestamp()
        return highlight.any { range -> timestamp in range }
    }

    override fun toggleHighlight(line: ParsedLine) {
        val timestamp = line.getTimestamp()
        var duration = this.highlight.find { range -> timestamp in range }
        if (duration == null) {
            this.addHighlight(line)
            return
        }

        while (duration != null) {
            this.highlight.remove(duration)
            duration = this.highlight.find { range -> timestamp in range }
        }
    }

    private fun addHighlight(line: ParsedLine) {
        val idx = lines.binarySearchBy(line.originLine.lineNumber) { it.lineNumber }
        var start = line.getTimestamp()
        var prevPtr = idx
        while (prevPtr >= 0) {
            val it = this.lines[prevPtr]
            prevPtr -= 1
            val parsedLine = it.parsedLine ?: continue

            val itTimestamp = parsedLine.getTimestamp()
            if (itTimestamp >= start) {
                continue
            }

            if (itTimestamp >= start - this.threshold) {
                start = itTimestamp
                continue
            }

            break
        }

        var end = line.getTimestamp()
        var nextPtr = idx
        val endPtr = this.lines.size - 1
        while (nextPtr <= endPtr) {
            val it = this.lines[nextPtr]
            nextPtr += 1
            val parsedLine = it.parsedLine ?: continue

            val itTimestamp = parsedLine.getTimestamp()
            if (itTimestamp <= end) {
                continue
            }

            if (itTimestamp <= end + this.threshold) {
                end = itTimestamp
                continue
            }

            break
        }

        this.highlight += start..end
    }
}

@Composable
fun rememberTimeChipState(threshold: Int, lines: List<Line>) = remember { TimeLineChipState(threshold, lines) }

class TextLineChipState(
    private val chipText: (line: ParsedLine) -> String
) : ILineChipState {
    private val highlightState = mutableStateListOf<String>()

    override fun getChipText(line: ParsedLine): String {
        return this.chipText(line)
    }

    override fun isHighlight(line: ParsedLine): Boolean {
        val text = this.getChipText(line)
        return this.highlightState.contains(text)
    }

    override fun toggleHighlight(line: ParsedLine) {
        val text = this.getChipText(line)
        if (this.isHighlight(line)) {
            this.highlightState.remove(text)
        } else {
            this.highlightState += text
        }
    }
}

@Composable
fun rememberLineChipsState(chipText: (line: ParsedLine) -> String) = remember { TextLineChipState(chipText) }
