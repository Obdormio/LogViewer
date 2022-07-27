package analysis

fun groupByCoreID(lines: List<LogLine>): LinkedHashMap<String, List<LogLine>> {
    val retVal = linkedMapOf<String, List<LogLine>>()
    var currentLines = arrayListOf<LogLine>()
    var currentCoreID: String? = null

    val put = put@{
        if (currentCoreID == null) {
            throw RuntimeException("CoreID should not be null")
        }

        if (retVal.contains(currentCoreID)) {
            println("Duplicated CoreID: $currentCoreID")
        }

        retVal[currentCoreID!!] = currentLines
    }

    lines.forEach { line ->
        if (currentCoreID == null) {
            currentCoreID = line.coreID
            currentLines += line
            return@forEach
        }

        if (currentCoreID == line.coreID) {
            currentLines += line
            return@forEach
        }

        if (line.coreID.isBlank()) {
            currentLines += line
            return@forEach
        }

        put()
        currentCoreID = line.coreID
        currentLines = arrayListOf(line)
    }

    if (currentLines.size > 0) {
        retVal[currentCoreID ?: "UNKNOWN"] = currentLines
    }

    return retVal
}

fun groupByRenderSlotID(lines: List<LogLine>): LinkedHashMap<String, List<LogLine>> {
    val retVal = linkedMapOf<String, ArrayList<LogLine>>()

    lines.forEach { line ->
        val inSquareBrackets = "\\[([^\\]]+)\\]"
        val pattern = "$inSquareBrackets\\s+$inSquareBrackets"

        val result = Regex(pattern).find(line.message) ?: return@forEach

        val renderSlotID = result.groupValues[1]
        val groupedLines = retVal[renderSlotID] ?: arrayListOf()
        groupedLines += line
        retVal[renderSlotID] = groupedLines
    }

    return retVal.mapValuesTo(linkedMapOf()) { entry ->
        entry.value
    }
}
