enum class Type { FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH, UNKNOWN }

fun main() {
    fun String.type(): Type {
        val counts = groupingBy { it }.eachCount()
        return when (counts.values.max()) {
            5 -> Type.FIVE
            4 -> Type.FOUR
            3 -> if (counts.values.size == 2) Type.FULL else Type.THREE
            2 -> if (counts.values.size == 3) Type.TWO else Type.ONE
            1 -> Type.HIGH
            else -> Type.UNKNOWN
        }
    }

    class CardComparator(private val order: List<Char>) : Comparator<Triple<String, Type, Int>> {
        override fun compare(a: Triple<String, Type, Int>, b: Triple<String, Type, Int>): Int {
            val comparison = compareValuesBy(b, a) { it.second.ordinal }
            return if (comparison != 0) comparison else compareValuesBy(b, a) {
                it.first.fold(1L) { acc, c -> acc * 13L + order.indexOf(c) }
            }
        }
    }

    fun List<String>.getTotalWinnings(order: List<Char>, handType: (hand: String) -> Type): Int {
        return asSequence()
            .map {
                val (hand, bid) = it.split(' ')
                Triple(hand, handType(hand), bid.toInt())
            }
            .sortedWith(CardComparator(order))
            .withIndex()
            .sumOf { (index, triple) -> (index + 1) * triple.third }
    }

    fun part1(input: List<String>): Int {
        val cardOrder = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        return input.getTotalWinnings(cardOrder, handType = { it.type() })
    }

    fun part2(input: List<String>): Int {
        val cardOrder = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        return input.getTotalWinnings(cardOrder, handType = { cardOrder.dropLast(1).minOf { c -> it.replace('J', c).type() } })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
