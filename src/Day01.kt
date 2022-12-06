fun main() {
    fun part1(input: List<List<Int>>): Int {
        //return input.indices.maxBy { input[it].sum() } + 1
        return input.maxOf { it.sum() }
    }

    fun part2(input: List<List<Int>>): Int {
        return input
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsIntsList("Day01_test")
    println(part1(testInput))


    val input = readInputAsIntsList("Day01")
    println(part1(input))

    println(part2(input))
}
