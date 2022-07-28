package analysis.renderer

import analysis.LogLine

class RendererStop(
    renderSlotID: String,
    renderSlotType: String,
    logLine: LogLine,
) : RendererInstanceLog(
    renderSlotID = renderSlotID,
    renderSlotType = renderSlotType,
    logLine = logLine
)

val StopParser = object : IParser {
    override fun parse(logLine: LogLine): RendererStop? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets\\s+stop"
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val renderSlotType = result.groupValues[2]
        return RendererStop(renderSlotID, renderSlotType, logLine)
    }
}

