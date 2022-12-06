fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input.map { it.split(",") }

        var foundFullyContainedNumbers = 0
        for ((first, second) in pairs) {
            val rangeFirst = first.split("-").let { it[0].toInt()..it[1].toInt() }
            val rangeSecond = second.split("-").let { it[0].toInt()..it[1].toInt() }
            foundFullyContainedNumbers +=
                if (rangeFirst.all { it in rangeSecond } || rangeSecond.all { it in rangeFirst }) 1 else 0
        }
        return foundFullyContainedNumbers
    }

    fun part2(input: List<String>): Int {
        val pairs = input.map { it.split(",") }

        var foundFullyContainedNumbers = 0
        for ((first, second) in pairs) {
            val rangeFirst = first.split("-").let { it[0].toInt()..it[1].toInt() }
            val rangeSecond = second.split("-").let { it[0].toInt()..it[1].toInt() }
            val duplicates = rangeFirst.intersect(rangeSecond)
            if (duplicates.isNotEmpty()) foundFullyContainedNumbers += 1
        }
        return foundFullyContainedNumbers
    }

    val testInput = readInput("Day04_test")
    println(part1(testInput))
    println(part2(testInput))


    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
