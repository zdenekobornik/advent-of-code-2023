data class Game(
    val id: Int,
    val rounds: List<GameRound>
)
data class GameRound(
    val draws: List<Draw>
)
data class Draw(
    var value: Int,
    val color: String
)

fun main() {
    fun part1(input: List<Game>): Int {
        val filtered = input.filter {
            it.rounds.all {
                it.draws.filter { it.color == "red" }.sumOf { it.value } <= 12 &&
                it.draws.filter { it.color == "green" }.sumOf { it.value } <= 13 &&
                it.draws.filter { it.color == "blue" }.sumOf { it.value } <= 14
            }
        }

        return filtered.sumOf { it.id }
    }

    fun part2(input: List<Game>): Int {
        val filtered = input.sumOf {
            val draws = it.rounds.flatMap { it.draws }

            val red = draws.filter { it.color == "red" }.maxOf { it.value }
            val green = draws.filter { it.color == "green" }.maxOf { it.value }
            val blue = draws.filter { it.color == "blue" }.maxOf { it.value }

            red * green * blue
        }

        return filtered
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day02_test").parseGames()
    check(part1(testInputPart1) == 8)
    check(part2(testInputPart1) == 2286)

    val input = readInput("Day02").parseGames()
    part1(input).println()
    part2(input).println()
}

fun List<String>.parseGames(): List<Game> {
    return map {
        val gameInfo = it.split(':', ' ', limit = 3)
        val gameId = gameInfo[1].toInt()
        val rounds  = gameInfo[2]
            .split(';')
            .map {
                val picks = it.split(',').map {
                    val (value, color) = it.trim().split(' ')
                    Draw(value.toInt(), color)
                }
                GameRound(picks)
            }

        Game(gameId, rounds)
    }
}