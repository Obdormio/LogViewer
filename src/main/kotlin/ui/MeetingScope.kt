package ui

import analysis.LogLine
import analysis.renderer.*
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.lidafan.ui.common.Chip
import me.lidafan.ui.common.Gap

@Composable
fun MeetingScope(
    logLines: List<LogLine>,
) {
    val rendererLogs = remember(logLines) {
        RenderLogAnalyzer(logLines)
    }

    val lazyScrollState = rememberLazyListState()

    LaunchedEffect(logLines) {
        lazyScrollState.scrollToItem(0)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(state = lazyScrollState, modifier = Modifier.fillMaxWidth()) {
            items(rendererLogs.rendererLogs) {
                LineView(it)
                Gap(8)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(lazyScrollState)
        )
    }
}

@Composable
fun LineView(log: RendererLog) {
    Row() {
        when (log) {
            is RendererStart -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Chip(log.renderSlotID)
                    Chip(log.renderSlotType)
                    Chip("Start", Color.Green)
                }
            }
            is RendererStop -> {
                Text("Stop")
            }
            is RendererAttach -> {
                Text("Attach")
                Text("Mode:${log.renderMode}")
                Text("RtcJoinId Count: ${log.rtcJoinIDs.size}")
            }
            is RendererRenderStarted -> {
                Text("Render Started")
            }
            is RendererForceResync -> {
                Text("Force Resync")
            }
            is ResetVideo -> {
                Text("Reset Video")
            }
            is ResetScreen -> {
                Text("Reset Screen")
            }
            is RendererChangeRenderSlotType -> {
                Text("Change RenderSlotType")
            }
            is ResetWhenMute -> {
                Text("Rest when mute")
            }
            is RendererInstanceLog -> {
                Box(modifier = Modifier.background(Color.Red)) {
                    Text(log.renderSlotType + ":" + log.renderSlotID)
                }
            }
            else -> {
                Box(modifier = Modifier.background(Color.Red)) {
                    Text(log.logLine.message)
                }
            }
        }
    }
}
