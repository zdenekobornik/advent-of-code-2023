import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {

    fun solveQuadratic(time: Long, distance: Long): Double {
        val discriminant = (time * time - 4 * distance).toDouble()

        val x1 = floor((time - sqrt(discriminant)) / 2 + 1)
        val x2 = ceil((time + sqrt(discriminant)) / 2 - 1)

        return x2 - x1 + 1
    }

    fun part1(input: List<String>): Long {
        return input.map { it.split(' ')
            .mapNotNull { it.toLongOrNull() } }
            .let { (t, d) -> t.zip(d) }
            .map { (time, distance) -> solveQuadratic(time, distance).toLong() }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val (time, distance) = input.map { it.filter { it.isDigit() }.toLong() }
        return solveQuadratic(time, distance).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
