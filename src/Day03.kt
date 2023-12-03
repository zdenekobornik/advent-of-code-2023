data class Number(val number: Int, val y: Int, val startX: Int, val endX: Int)

private fun List<String>.allPositions(): List<Pair<Int, Int>> = this.indices.flatMap { y ->
    this[y].indices.map { y to it }
}

private fun List<String>.adjacentPositions(y: Int, x: Int) = listOf(
    // directly adjacent
    y to x - 1, y - 1 to x, y to x + 1, y + 1 to x,
    // diagonally adjacent
    y - 1 to x - 1, y - 1 to x + 1, y + 1 to x - 1, y + 1 to x + 1,
).filter { (y, x) -> y in 0..this[y].lastIndex && x in 0..this.lastIndex }

private fun List<String>.findFinalNumberFromDigit(y: Int, x: Int): Number {
    val startX = (x - 1 downTo 0).takeWhile { this[y][it].isDigit() }.lastOrNull() ?: x
    val endX = (x + 1..this[y].lastIndex).takeWhile { this[y][it].isDigit() }.lastOrNull() ?: x
    val number = this[y].substring(startX..endX).toInt()

    return Number(number, y, startX, endX)
}

private fun List<String>.findAdjacentNumbers(y: Int, x: Int) = adjacentPositions(y, x)
    .filter { (y, x) -> this[y][x].isDigit() }
    .map { (y, x) -> findFinalNumberFromDigit(y, x) }
    .distinct()

fun main() {

    fun part1(input: List<String>): Int = input.allPositions()
        .filter { (y, x) -> !input[y][x].isDigit() && input[y][x] != '.' }
        .sumOf { (y, x) -> input.findAdjacentNumbers(y, x).sumOf { it.number } }

    fun part2(input: List<String>): Int = input.allPositions()
        .filter { (y, x) -> input[y][x] == '*' }
        .sumOf { (y, x) ->
            input.findAdjacentNumbers(y, x)
                .takeIf { it.size == 2 }
                ?.map { it.number }
                ?.reduce { acc, i -> acc * i } ?: 0
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
