data class Game(val id: Int, val rounds: List<GameRound>)
data class GameRound(val red: Int, val green: Int, val blue: Int)

fun main() {
    fun part1(input: List<Game>): Int = input
        .filter { it.rounds.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 } }
        .sumOf { it.id }

    fun part2(input: List<Game>): Int = input.sumOf {
        val red = it.rounds.maxOf { it.red }
        val green = it.rounds.maxOf { it.green }
        val blue = it.rounds.maxOf { it.blue }

        red * green * blue
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day02_test").parseGames()
    check(part1(testInputPart1) == 8)
    check(part2(testInputPart1) == 2286)

    val input = readInput("Day02").parseGames()
    part1(input).println()
    part2(input).println()
}

fun List<String>.parseGames(): List<Game> = map {
    val (_, gameId, allRounds) = it.split(':', ' ', limit = 3)
    val rounds = allRounds
        .split(';')
        .map { round ->
            val picks = round
                .split(',')
                .associate {
                    val (number, color) = it.trim().split(' ', limit = 2)
                    color to number.toInt()
                }

            GameRound(picks["red"] ?: 0, picks["green"] ?: 0, picks["blue"] ?: 0)
        }

    Game(gameId.toInt(), rounds)
}