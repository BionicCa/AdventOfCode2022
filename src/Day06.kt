fun main() {
    fun part1(input: List<String>): Int {
        val dataStream = input.first().toCharArray()
        val checkCharacters = 4
        for (i in dataStream.indices) {
            if (i + checkCharacters - 1 >= dataStream.size) break
            val slice = dataStream.slice(i until i + checkCharacters)
            val noDuplicates = slice.distinct().size == slice.size
            if (noDuplicates) {
                return i + checkCharacters
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val dataStream = input.first().toCharArray()
        val checkCharacters = 14
        for (i in dataStream.indices) {
            if (i + checkCharacters - 1 >= dataStream.size) break
            val slice = dataStream.slice(i until i + checkCharacters)
            val noDuplicates = slice.distinct().size == slice.size
            if (noDuplicates) {
                return i + checkCharacters
            }
        }
        return -1
    }

//    val testInput = readInput("Day06_test")
//    println(part1(testInput))
//    println(part2(testInput))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
