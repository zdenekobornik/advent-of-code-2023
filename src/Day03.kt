data class Number(val number: Int, val y: Int, val startX: Int, val endX: Int)

fun List<String>.allPositions(): List<Pair<Int, Int>> = this.indices.flatMap { y ->
    this[y].indices.map { y to it }
}

fun List<String>.adjacentDigits(y: Int, x: Int) = listOf(
    y to x - 1,
    y - 1 to x,
    y to x + 1,
    y + 1 to x,
    y - 1 to x - 1,
    y - 1 to x + 1,
    y + 1 to x - 1,
    y + 1 to x + 1,
)
    .filter { (y, x) -> y in 0..this[y].lastIndex && x in 0..this.lastIndex }
    .filter { (y, x) -> this[y][x].isDigit() }

fun List<String>.findPossibleNumber(y: Int, x: Int): Number {
    var startX = x
    var endX = x
    var currentNumberString = this[y][x].toString()

    while (startX > 0 && this[y][startX - 1].isDigit()) {
        currentNumberString = this[y][startX - 1] + currentNumberString
        startX -= 1
    }

    while (endX < this[y].lastIndex && this[y][endX + 1].isDigit()) {
        currentNumberString += this[y][endX + 1]
        endX += 1
    }

    return Number(currentNumberString.toInt(), y, startX, endX)
}

fun main() {

    fun part1(input: List<String>): Int = input.allPositions()
        .filter { (y, x) -> !input[y][x].isDigit() && input[y][x] != '.' }
        .sumOf { (y, x) ->
            input.adjacentDigits(y, x)
                .map { (y, x) -> input.findPossibleNumber(y, x) }
                .distinct()
                .sumOf { it.number }
        }

    fun part2(input: List<String>): Int = input.allPositions()
        .filter { (y, x) -> input[y][x] == '*' }
        .sumOf { (y, x) ->
            input.adjacentDigits(y, x)
                .map { (y, x) -> input.findPossibleNumber(y, x) }
                .distinct()
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
