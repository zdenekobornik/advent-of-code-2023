fun List<Int>.getNewValue(): Int = getAllDifferences().reversed().sumOf { it.last() }

fun List<Int>.getAllDifferences(): List<List<Int>> {
    val result: MutableList<List<Int>> = mutableListOf(this)
    while (!result.last().all { it == 0 }) {
        result.add(result.last().getDifferences())
    }
    return result
}

fun List<Int>.getDifferences() = mapIndexedNotNull { index, num -> getOrNull(index + 1)?.minus(num) }

fun main() {

    fun List<String>.getNumbers(): List<List<Int>> = this.filter { it.isNotBlank() }
        .map { it.split(" ").map { it.toInt() } }

    fun part1(input: List<String>): Int = input.getNumbers().sumOf { it.getNewValue() }

    fun part2(input: List<String>): Int = input.getNumbers().sumOf { it.reversed().getNewValue() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
