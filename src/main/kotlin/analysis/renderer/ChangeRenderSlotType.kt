package analysis.renderer

import analysis.LogLine

class RendererChangeRenderSlotType(
    val to: String,
    renderSlotID: String,
    renderSlotType: String,
    logLine: LogLine,
) : RendererInstanceLog(
    renderSlotID = renderSlotID,
    renderSlotType = renderSlotType,
    logLine = logLine,
)

val ChangeRenderSlotTypeParser = object : IParser {
    override fun parse(logLine: LogLine): RendererChangeRenderSlotType? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets\\s+change renderSlotType to\\s+(.*)\""
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val renderSlotType = result.groupValues[2]
        val to = result.groupValues[3]
        return RendererChangeRenderSlotType(to, renderSlotID, renderSlotType, logLine)
    }
}
