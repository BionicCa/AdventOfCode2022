import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputAsIntsList(name: String) : List<List<Int>> {
    val inputArray = readInput(name)
    val map = inputArray.flatMapIndexed { index: Int, value: String ->
        when {
            index == 0 || index == inputArray.lastIndex -> listOf(index)
            value.isEmpty() -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }
    .windowed(size = 2, step = 2) { (from, to) -> inputArray.slice(from..to).map { it.toInt() }}

    return map
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun findCommonCharacter(first: CharArray, second: CharArray): Set<Char> {
    val common = first.toMutableSet()
    common.retainAll(second.toSet())
    return common
}

fun <T> transposeList(list: List<List<T>>): List<MutableList<T>> {
    val transposed = mutableListOf<MutableList<T>>()

    list.forEach { line ->
        line.forEachIndexed { i, value ->
            if (transposed.size <= i) transposed.add(i, mutableListOf(value))
            else transposed[i].add(value)
        }
    }

    return transposed
}