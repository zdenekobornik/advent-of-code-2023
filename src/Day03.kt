data class Number(val number: Int, val y: Int, val startX: Int, val endX: Int)

fun List<String>.validPositionsFor(y: Int, x: Int) = listOf(
    y to x - 1,
    y - 1 to x,
    y to x + 1,
    y + 1 to x,
    y - 1 to x - 1,
    y - 1 to x + 1,
    y + 1 to x - 1,
    y + 1 to x + 1,
).filter {
    it.first in 0..this[y].lastIndex && it.second in 0..this.lastIndex
}

fun List<String>.findPossibleNumber(y: Int, x: Int): Number? {
    if (!this[y][x].isDigit()) return null

    var startX = x
    var endX = x
    var currentNumberString = this[y][x].toString()

    if (x > 0) {
        for (cur in x - 1 downTo 0) {
            if (this[y][cur].isDigit()) {
                currentNumberString = this[y][cur] + currentNumberString
                startX = cur
            } else {
                break
            }
        }
    }


    if (x < this[y].lastIndex) {
        for (cur in x + 1..this[y].lastIndex) {
            if (this[y][cur].isDigit()) {
                endX = cur
                currentNumberString = currentNumberString + this[y][cur]
            } else {
                break
            }
        }
    }

    return Number(currentNumberString.toInt(), y, startX, endX)
}

fun main() {

    fun part1(input: List<String>): Int {
        var finalScore = 0

        for (y in input.indices) {
            for (x in 0 until input[y].length) {
                if (!input[y][x].isDigit() && input[y][x] != '.') {
                    finalScore += input.validPositionsFor(y, x)
                        .mapNotNull { (y, x) -> input.findPossibleNumber(y, x) }
                        .distinct()
                        .sumOf { it.number }
                }
            }
        }

        return finalScore
    }

    fun part2(input: List<String>): Int {
        var finalScore = 0

        for (y in input.indices) {
            for (x in 0 until input[y].length) {
                if (input[y][x] == '*') {
                    finalScore += input.validPositionsFor(y, x)
                        .mapNotNull { (y, x) -> input.findPossibleNumber(y, x) }
                        .distinct()
                        .takeIf { it.size == 2 }
                        ?.map { it.number }
                        ?.reduce { acc, i -> acc * i } ?: 0
                }
            }
        }

        return finalScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
