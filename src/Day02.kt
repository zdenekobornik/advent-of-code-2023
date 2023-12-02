data class Game(val id: Int, val rounds: List<GameRound>)
data class GameRound(val picks: List<Pick>)
data class Pick(var value: Int, val color: String)

fun main() {
    fun part1(input: List<Game>): Int = input.filter {
        it.rounds.all {
            val groups = it.picks
                .groupBy(Pick::color, Pick::value)
                .mapValues { it.value.sum() }

            val red = groups["red"] ?: 0
            val green = groups["green"] ?: 0
            val blue = groups["blue"] ?: 0

            red <= 12 && green <= 13 && blue <= 14
        }
    }.sumOf { it.id }

    fun part2(input: List<Game>): Int = input.sumOf {
        val groups = it.rounds
            .flatMap { it.picks }
            .groupBy(Pick::color, Pick::value)
            .mapValues { it.value.max() }

        val red = groups["red"] ?: 0
        val green = groups["green"] ?: 0
        val blue = groups["blue"] ?: 0

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

fun List<String>.parseGames(): List<Game> {
    return map {
        val (_, gameId, allRounds) = it.split(':', ' ', limit = 3)
        val rounds = allRounds
            .split(';')
            .map { round ->
                val picks = round
                    .split(',')
                    .map { pick ->
                        val (value, color) = pick.trim().split(' ', limit = 2)
                        Pick(value.toInt(), color)
                    }
                GameRound(picks)
            }

        Game(gameId.toInt(), rounds)
    }
}