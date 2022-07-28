package analysis.renderer

import analysis.LogLine

class RenderLogAnalyzer(logLines: List<LogLine>) {
    val rendererLogs: List<RendererLog>;

    init {
        val rendererLogs = arrayListOf<RendererLog>()
        val parsers = listOf(
            StartParser,
            StopParser,
            AttachParser,
            RenderStartedParser,
            ForceReSyncParser,
            ResetVideoParser,
            ResetScreenParser,
            ChangeRenderSlotTypeParser,
        )
        logLines.forEach { logLine ->
            val parsed = parsers.any { parser ->
                parser.parse(logLine)?.let {
                    rendererLogs += it
                    true
                } ?: false
            }

            if (!parsed) {
                rendererLogs += RendererLog(logLine)
            }
        }
        this.rendererLogs = rendererLogs
    }
}
