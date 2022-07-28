package analysis.renderer

import analysis.LogLine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RendererAttach(
    val renderMode: String,
    val rtcJoinIDs: List<String>,
    renderSlotID: String,
    renderSlotType: String,
    logLine: LogLine
) : RendererInstanceLog(
    renderSlotID = renderSlotID,
    renderSlotType = renderSlotType,
    logLine = logLine,
)

@Serializable
data class AttachProps(
    val renderMode: String,
    @SerialName("rtcJoinIds")
    val rtcJoinIDs: List<String>,
)

val AttachParser = object : IParser {
    override fun parse(logLine: LogLine): RendererAttach? {
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets\\s+attach to sdk\"\\s+(.*)"
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val renderSlotID = result.groupValues[1]
        val renderSlotType = result.groupValues[2]
        val json = result.groupValues[3]
        val attachProps = Json.decodeFromString<AttachProps>(json)

        return RendererAttach(attachProps.renderMode, attachProps.rtcJoinIDs, renderSlotID, renderSlotType, logLine)
    }
}
