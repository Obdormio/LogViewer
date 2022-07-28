package analysis.renderer

import analysis.LogLine

class RendererForceResync(
    renderSlotID: String,
    renderSlotType: String,
    logLine: LogLine
) : RendererInstanceLog(
    renderSlotID = renderSlotID,
    renderSlotType = renderSlotType,
    logLine = logLine,
)

val ForceReSyncParser = object : IParser {
    override fun parse(logLine: LogLine): RendererForceResync? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets\\s+force resync"
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val renderSlotType = result.groupValues[2]
        return RendererForceResync(renderSlotID, renderSlotType, logLine)
    }
}
