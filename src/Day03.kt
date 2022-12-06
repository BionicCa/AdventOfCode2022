fun main() {
    fun part1(input: List<String>): Int {
        var totalScore = 0
        for (rucksack in input) {
            val chunks = rucksack.chunked(rucksack.length / 2)

            val commonElements = findCommonCharacter(chunks[0].toCharArray(), chunks[1].toCharArray())
            val score = commonElements.sumOf {
                when {
                    it.isLowerCase() -> it.code - 96 // a - z -> 1 - 26
                    else -> it.code - 38// A - Z -> 27  - 52
                }
            }
            totalScore += score
        }

        return totalScore
    }

    fun part2(input: List<String>): Int {
        val chunks = input.windowed(3, step = 3)
        var totalScore = 0
        for (rucksackGroup in chunks) {
            val commonElements = findCommonCharacter(
                findCommonCharacter(rucksackGroup[0].toCharArray(), rucksackGroup[1].toCharArray()).toCharArray(),
                rucksackGroup[2].toCharArray()
            )
            val score = commonElements.sumOf {
                when {
                    it.isLowerCase() -> it.code - 96 // a - z -> 1 - 26
                    else -> it.code - 38// A - Z -> 27  - 52
                }
            }
            totalScore += score
        }

        return totalScore
    }

    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))


    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
