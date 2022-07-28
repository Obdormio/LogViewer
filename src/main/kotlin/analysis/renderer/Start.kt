package analysis.renderer

import analysis.LogLine

class RendererStart(
    renderSlotID: String,
    renderSlotType: String,
    logLine: LogLine,
) : RendererInstanceLog(
    renderSlotID = renderSlotID,
    renderSlotType = renderSlotType,
    logLine = logLine,
)

val StartParser = object : IParser {
    override fun parse(logLine: LogLine): RendererStart? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets\\s+start"
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val renderSlotType = result.groupValues[2]
        return RendererStart(renderSlotID, renderSlotType, logLine)
    }
}
