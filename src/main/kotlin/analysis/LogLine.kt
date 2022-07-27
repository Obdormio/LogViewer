package analysis

import java.io.File

data class LogLine(
    val lineNumber: Int,
    val month: Int,
    val date: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val millisecond: Int,
    val level: String,
    val pid: String,
    val label: String,
    val route: String,
    val coreID: String,
    val message: String
) {
    fun formatTime(): String {
        var retVal = ""
        retVal += this.hour.toString().padStart(2, '0') + ":"
        retVal += this.minute.toString().padStart(2, '0') + ":"
        retVal += this.second.toString().padStart(2, '0') + "."
        retVal += this.millisecond.toString().padStart(3, '0')
        return retVal
    }
}

private fun makePattern(): Regex {
    // 07-25 18:42:45.235 - INFO - [11444][Stream Renderer][/meeting][5182]	"Some Content" { "key": 30 }
    val pattern = arrayListOf<String>()
    pattern += "(\\d{2})-(\\d{2})" // 07-25
    pattern += "(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})" // 18:42:45.235
    pattern += "-"
    pattern += "(\\w+)" // INFO
    pattern += "-"
    val inSquareBrackets = "\\[([^\\]]+)\\]"
    pattern += inSquareBrackets // [11444]
    pattern += inSquareBrackets // [Stream Renderer]
    pattern += inSquareBrackets // [/meeting]
    pattern += "(?:$inSquareBrackets)?" // [5182]
    pattern += "(.*)"

    return Regex(pattern.joinToString("\\s*"))
}

private fun toLogLine(line: String, lineNumber: Int): LogLine {
    val regex = makePattern()

    val result = regex.find(line) ?: throw RuntimeException("can not parse line: $lineNumber")
    val iter = result.groupValues.iterator()
    iter.next() // first is the whole string, skip it

    return LogLine(
        lineNumber = lineNumber,
        month = iter.next().toInt(),
        date = iter.next().toInt(),
        hour = iter.next().toInt(),
        minute = iter.next().toInt(),
        second = iter.next().toInt(),
        millisecond = iter.next().toInt(),
        level = iter.next(),
        pid = iter.next(),
        label = iter.next(),
        route = iter.next(),
        coreID = iter.next(),
        message = iter.next()
    )
}

fun findLogLines(logFile: File): List<LogLine> {
    val logLines = arrayListOf<LogLine>()

    var lineNumber = 0
    logFile.forEachLine { line ->
        lineNumber++
        if (!line.contains("Stream Renderer")) {
            return@forEachLine
        }
        logLines += toLogLine(line, lineNumber)
    }

    return logLines
}
