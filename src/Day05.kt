import kotlin.math.max
import kotlin.math.min

data class ParsedData(val categories: List<List<List<Long>>>, val seedsInput: List<Long>)

private fun String.toParsedData(): ParsedData {
    val map = this.split("\n\n")
    val categories = map.drop(1).map { it.split("\n").drop(1).map { it.split(' ').map { it.toLong() } } }
    val seedRanges = map.first()
        .drop(7)
        .split(' ')
        .map { it.toLong() }

    return ParsedData(categories, seedRanges)
}

fun main() {
    fun solve(seedRanges: List<Pair<Long, Long>>, categories: List<List<List<Long>>>): Long {
        return seedRanges.map { range ->
            categories.fold(listOf(range)) { ranges, mappings ->
                val remainingRanges = ranges.toMutableList()
                val mappedRanges = mutableListOf<Pair<Long, Long>>()

                while (remainingRanges.isNotEmpty()) {
                    val currentRange = remainingRanges.removeFirst()
                    val start = currentRange.first
                    val end = start + currentRange.second

                    val mapping = mappings.find { (_, mapStart, mapRange) ->
                        val mapEnd = mapStart + mapRange
                        start < mapEnd && end > mapStart
                    }

                    if (mapping == null) {
                        mappedRanges += currentRange
                        continue
                    }

                    val (target, mapStart, mapRange) = mapping
                    val mapEnd = mapStart + mapRange
                    mappedRanges += (target + (max(start, mapStart) - mapStart)) to min(end, mapEnd) - max(
                        start,
                        mapStart
                    )
                    if (start < mapStart) {
                        remainingRanges += start to (mapStart - start)
                    }
                    if (end > mapEnd) {
                        remainingRanges += mapEnd to (end - mapEnd)
                    }
                }
                mappedRanges
            }
        }.flatten().minOf { it.first }
    }

    fun part1(input: ParsedData): Long {
        val seedRanges = input.seedsInput.map { it to 0L }
        return solve(seedRanges, input.categories)
    }

    fun part2(input: ParsedData): Long {
        val seedRanges = input.seedsInput.windowed(2, step = 2) { (from, to) -> from to to }
        return solve(seedRanges, input.categories)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputRaw("Day05_test").toParsedData()
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)
    val input = readInputRaw("Day05").toParsedData()
    part1(input).println()
    part2(input).println()
}
