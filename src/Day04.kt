import kotlin.math.pow

fun main() {

    fun List<String>.calculateWins(): List<Int> = map { line ->
        val (winning, numbers) = line.split(": ", " | ", limit = 3)
            .drop(1)
            .map { it.trim().split("\\s+".toRegex()).toSet() }

        winning.intersect(numbers).size
    }

    fun part1(input: List<String>): Int = input.calculateWins().sumOf { (2.0).pow(it - 1).toInt() }

    fun part2(input: List<String>): Int {
        val freq = input.indices.associateWith { 1 }.toMutableMap()

        input.calculateWins().forEachIndexed { i, wins ->
            for (w in 1..wins) {
                freq[i + w] = freq[i + w]!! + freq[i]!!
            }
        }

        return freq.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
