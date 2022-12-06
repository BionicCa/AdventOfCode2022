private fun getVerticalStacks(input: List<String>): List<ArrayDeque<Char>> {
    val strLen = input.last().length

    val stackRows = input.map { line ->
        val padLine = line.padEnd(strLen, ' ')

        val row = mutableListOf<Char>()
        for (i in 1 until strLen step 4) {
            val c = padLine[i]
            if (c != ' ') row.add(c)
            else row.add('x')
        }

        return@map row
    }

    // transpose the list from rows to vertical stacks (was read in horizontally)
    return transposeList(stackRows)
        .map { col ->
            col.filter { it != 'x' }
                .reversed().toMutableList()
        }
        .map {
            ArrayDeque(it)
        }
}

// convert instructions into index values
private fun getInstructions(input: List<String>): List<List<Int>> =
    input.map { line ->
        line.split("move ", " from ", " to ")
            .filter { it.isNotBlank() }
            .mapIndexed { i, n ->
                // subtract index values to match 0-index lists, but amount to move stays same
                n.toInt() - if (i == 0) 0 else 1
            }
    }

fun main() {
    fun part1(input: List<String>): String {
        val stackRows = input.takeWhile { it.isNotEmpty() }.dropLast(1)
        val stacks = getVerticalStacks(stackRows)
        val instructions = getInstructions(input.drop(stackRows.size + 2))

        for ((amount, stackFrom, stackTo) in instructions) {
            for (index in 1..amount) {
                val char = stacks[stackFrom].removeLast()
                stacks[stackTo].addLast(char)
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stackRows = input.takeWhile { it.isNotEmpty() }.dropLast(1)
        val stacks = getVerticalStacks(stackRows)
        val instructions = getInstructions(input.drop(stackRows.size + 2))

        for ((amount, stackFrom, stackTo) in instructions) {
            val charsToAdd = mutableListOf<Char>()
            for (index in 1..amount) {
                val char = stacks[stackFrom].removeLast()
                charsToAdd.add(char)
            }
            stacks[stackTo].addAll(charsToAdd.reversed())
        }
        return stacks.map { it.last() }.joinToString("")

    }

//    val testInput = readInput("Day05_test")
//    println(part1(testInput))
//    println(part2(testInput))


    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
