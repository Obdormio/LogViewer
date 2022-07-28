package analysis.renderer

import analysis.LogLine

interface IParser {
    fun parse(logLine: LogLine): RendererLog?
}
