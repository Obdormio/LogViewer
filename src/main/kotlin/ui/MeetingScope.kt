package ui

import analysis.LogLine
import analysis.renderer.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MeetingScope(
    logLines: List<LogLine>,
) {
    val rendererLogs = remember(logLines) {
        RenderLogAnalyzer(logLines)
    }

    val scrollState = rememberScrollState(0)

    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        Column {
            rendererLogs.rendererLogs.forEach { LineView(it) }
        }
    }
}

@Composable
fun LineView(rendererLog: RendererLog) {
    Row {
        when (rendererLog) {
            is RendererStart -> {
                Text("Start")
            }
            is RendererStop -> {
                Text("Stop")
            }
            is RendererAttach -> {
                Text("Attach")
                Text("Mode:${rendererLog.renderMode}")
                Text("RtcJoinId Count: ${rendererLog.rtcJoinIDs.size}")
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
            is RendererInstanceLog -> {
                Box(modifier = Modifier.background(Color.Red)) {
                    Text(rendererLog.renderSlotType + ":" + rendererLog.renderSlotID)
                }
            }
            else -> {
                Box(modifier = Modifier.background(Color.Red)) {
                    Text(rendererLog.logLine.message)
                }
            }
        }
    }
}
