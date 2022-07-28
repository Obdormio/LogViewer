package analysis.renderer

import analysis.LogLine

class RendererRenderStarted(
    val renderSlotID: String,
    val rtcJoinID: String,
    logLine: LogLine,
) : RendererLog(
    logLine = logLine
)

val RenderStartedParser = object : IParser {
    override fun parse(logLine: LogLine): RendererRenderStarted? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+render started:\\s+(.*)\""
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val rtcJoinID = result.groupValues[2]
        return RendererRenderStarted(renderSlotID, rtcJoinID, logLine)
    }
}
