fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val first = it.first { it.isDigit() }
            val second = it.last { it.isDigit() }
            "$first$second".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val translationMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        fun Sequence<String>.findFirstNumber() = firstNotNullOf {
            for (number in translationMap) {
                if (it.contains(number.key) || it.contains(number.value)) {
                    return@firstNotNullOf number.value
                }
            }
            null
        }

        return input.sumOf { line ->
            val first = line
                .asSequence()
                .map { it.toString() }
                .runningReduce { acc, s -> acc + s }
                .findFirstNumber()

            val last = line
                .reversed()
                .asSequence()
                .map { it.toString() }
                .runningReduce { acc, s -> s + acc }
                .findFirstNumber()

            "$first$last".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day01_test_1")
    val testInputPart2 = readInput("Day01_test_2")
    check(part1(testInputPart1) == 142)
    check(part2(testInputPart2) == 281)
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
