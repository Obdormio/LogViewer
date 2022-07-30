package analysis.renderer

import analysis.LogLine

class ResetWhenMute(
    val rtcJoinID: String,
    logLine: LogLine,
) : RendererLog(
    logLine = logLine,
)

val ResetWhenMuteParser = object : IParser {
    override fun parse(logLine: LogLine): ResetWhenMute? {
        val pattern = "reset video render status when mute\" \"(.*)\""
        val regex = Regex(pattern)
        val result = regex.find(logLine.message) ?: return null

        val rtcJoinID = result.groupValues[1]
        return ResetWhenMute(rtcJoinID, logLine)
    }
}
