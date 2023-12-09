fun main() {

    fun List<String>.getStepsCount(nodeMap: Map<String, Pair<String, String>>, instructions: String): LongArray {
        val steps = LongArray(this.size)
        this.forEachIndexed { index, start ->
            var current = start
            while (!current.endsWith('Z')) {
                val action = instructions[(steps[index]++ % instructions.length).toInt()]
                val node = nodeMap.getValue(current)
                current = if (action == 'L') node.first else node.second
            }
        }

        return steps
    }

    fun List<String>.parseInput(): Pair<String, Map<String, Pair<String, String>>> {
        val instructions = this.first()
        val nodeMap = this.drop(2).associate {
            val (key, left, right) = it.replace("(", "").replace(")", "").split(" = ", ", ")
            key to (left to right)
        }
        return instructions to nodeMap
    }

    fun part1(input: List<String>): Long {
        val (instructions, nodeMap) = input.parseInput()
        val starts = nodeMap.keys.filter { it.endsWith('A') }
        val steps = starts.getStepsCount(nodeMap, instructions)

        return steps[starts.indexOf("AAA")]
    }

    fun part2(input: List<String>): Long {
        val (instructions, nodeMap) = input.parseInput()

        tailrec fun gcd(a: Long, b: Long): Long {
            return if (b == 0L) a else gcd(b, a % b)
        }
        fun lcm(a: Long, b: Long): Long {
            return a * b / gcd(a, b)
        }

        return nodeMap.keys.filter { it.endsWith('A') }.getStepsCount(nodeMap, instructions).reduce(::lcm)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test_1")
    val testInput2 = readInput("Day08_test_2")
    check(part1(testInput1) == 2L)
    check(part2(testInput2) == 6L)
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
