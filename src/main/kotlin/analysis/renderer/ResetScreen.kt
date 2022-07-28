package analysis.renderer

import analysis.LogLine

class ResetScreen(
    val rtcJoinID: String,
    logLine: LogLine,
) : RendererLog(
    logLine = logLine,
)

val ResetScreenParser = object : IParser {
    override fun parse(logLine: LogLine): ResetVideo? {
        val pattern = "reset screen render status of\\s+(.*)\""
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val rtcJoinID = result.groupValues[1]
        return ResetVideo(rtcJoinID, logLine)
    }
}
