fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.splitIgnoreEmpty("").map { it.toInt() } }

        // Count the number of visible trees
        var visibleTrees = 0
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                // check if we are on the edge
                if (row == 0 || col == 0 || grid[row].size - 1 == col || grid.size - 1 == row) {
                    visibleTrees++
                    continue
                }
                val isVisible = isTreeVisible(row, col, grid)
                if (isVisible) {
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.splitIgnoreEmpty("").map { it.toInt() } }
        val scores = mutableListOf<Int>()
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                // skip check if we are on the edge
                if (row == 0 || col == 0 || grid[row].size - 1 == col || grid.size - 1 == row) {
                    continue
                }

                val score = countScoreOfVisibleTrees(row, col, grid)
                scores.add(score)
//                println("${grid[row][col]} -> score: $score")
            }
        }

        return scores.max()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))

}

fun isTreeVisible(i: Int, j: Int, grid: List<List<Int>>): Boolean {
    val current = grid[i][j]
    val tallestLeft = grid[i].slice(0 until j).max()
    val tallestRight = grid[i].slice(j + 1 until grid[i].size).max()
    val tallestTop = grid.map { it[j] }.slice(0 until i).max()
    val tallestDown = grid.map { it[j] }.slice(i + 1 until grid.size).max()

    // check top
    return if (grid[i - 1][j] < current && tallestTop < current) true
    // check down
    else if (grid[i + 1][j] < current && tallestDown < current) true
    // check left
    else if (grid[i][j - 1] < current && tallestLeft < current) true
    // check right
    else grid[i][j + 1] < current && tallestRight < current
}

fun countScoreOfVisibleTrees(i: Int, j: Int, grid: List<List<Int>>): Int {
    val current = grid[i][j]
    var countLeft = 0
    var countRight = 0
    var countTop = 0
    var countDown = 0

    for (left in j - 1 downTo 0) {
        countLeft++
        if (grid[i][left] >= current) break
    }
    for (right in j + 1 until grid.size) {
        countRight++
        if (grid[i][right] >= current) break
    }
    for (top in i - 1 downTo 0) {
        countTop++
        if (grid[top][j] >= current) break
    }
    for (down in i + 1 until grid[i].size) {
        countDown++
        if (grid[down][j] >= current) break
    }

    return countTop * countLeft * countDown * countRight
}