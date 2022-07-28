package analysis.renderer

import analysis.LogLine

open class RendererLog(
    val logLine: LogLine
)

open class RendererInstanceLog(
    val renderSlotID: String,
    val renderSlotType: String,
    logLine: LogLine
) : RendererLog(logLine)
