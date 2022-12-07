import java.lang.Exception

private enum class Commands(val cmd: String) {
    CHANGE_DIRECTORY("cd"), LIST("ls")
}

private fun <E> List<E>.padWithNulls(limit: Int): List<E?> {
    val result: MutableList<E?> = this.toMutableList()

    while (result.size < limit) {
        result.add(null)
    }

    return result
}

private data class Command(
    val command: Commands,
    val commandArgument: String? = null,
    var commandOutput: String? = null
)

private data class File(val name: String, val size: Long)
private class Directory(
    val name: String, var files: List<File> = emptyList(),
    var dirs: List<Directory> = emptyList(), val parent: Directory? = null
) {
    fun getSize(): Long {
        var currentFileSize = files.sumOf { it.size }
        if (dirs.isNotEmpty()) currentFileSize += dirs.sumOf { it.getSize() }
        return currentFileSize
    }

    override fun toString(): String {
        return "$name: $parent"
    }
}

private fun getCommands(input: List<String>): List<Command> {
    val commandList = mutableListOf<Command>()
    var command: Command? = null
    val outputStr = StringBuilder()
    for (i in input.indices) {
        val line = input[i]
        if (line.startsWith("$")) {
            if (command != null) {
                command.commandOutput = outputStr.toString()
                outputStr.clear()
                commandList.add(command)
            }
            val str = line.substringAfter("$").trim()
            val (cmd, argument: String?) = str.split(" ", limit = 2).padWithNulls(limit = 2)
            command = when (cmd) {
                Commands.CHANGE_DIRECTORY.cmd -> Command(Commands.CHANGE_DIRECTORY, argument)
                Commands.LIST.cmd -> Command(Commands.LIST, argument)
                else -> null
            }
        } else {
            outputStr.appendLine(line)
        }

        if (i == input.indices.last && command != null) {
            command.commandOutput = outputStr.toString()
            outputStr.clear()
            commandList.add(command)
        }
    }
    return commandList
}

private fun handleCommand(command: Command, current: Directory): Directory {
    when (command.command) {
        Commands.CHANGE_DIRECTORY -> return when (command.commandArgument) {
            ".." -> {
                if (current.parent == null) throw Exception()
                current.parent
            }

            else -> {
                current.dirs.first { directory -> directory.name == command.commandArgument }
            }
        }

        Commands.LIST -> {
            handleListCommand(current, command.commandOutput!!)
        }
    }
    return current
}

private fun handleListCommand(current: Directory, output: String) {
    val dirs = mutableListOf<Directory>()
    val files = mutableListOf<File>()

    for (line in output.trim().split("\n")) {
        if (line.isBlank()) continue

        when {
            line.startsWith("dir") -> {
                val (_, dirName) = line.split(" ")
                dirs.add(Directory(dirName, parent = current))
            }

            else -> {
                val (size, fileName) = line.split(" ")
                files.add(File(fileName, size.toLong()))
            }
        }
    }
    current.files = files
    current.dirs = dirs
}

private fun calculateSizeBelow10000AndCountFileSizesMultipleTimes(directory: Directory): Long {
    var currentSize = 0L
    val size = directory.getSize()
    if (size < 100000) {
        currentSize += size
    }

    for (dir in directory.dirs) {
        currentSize += calculateSizeBelow10000AndCountFileSizesMultipleTimes(dir)
    }
    return currentSize
}

private fun findFoldersAboveGivenSize(directory: Directory, biggerThanSize: Long, list: MutableList<Directory>) {
    if (directory.getSize() >= biggerThanSize) {
        list.add(directory)
    }
    for (dir in directory.dirs) findFoldersAboveGivenSize(dir, biggerThanSize, list)
}

fun main() {
    fun part1(input: List<String>): Long {
        val commands = getCommands(input)
        val rootCommand = commands.first()
        val root = Directory(rootCommand.commandArgument!!)
        var current = root
        for (command in commands.drop(1)) {
            current = handleCommand(command, current)
        }

        return calculateSizeBelow10000AndCountFileSizesMultipleTimes(root)
    }

    fun part2(input: List<String>): Long {
        val commands = getCommands(input)
        val rootCommand = commands.first()
        val root = Directory(rootCommand.commandArgument!!)
        var current = root
        for (command in commands.drop(1)) {
            current = handleCommand(command, current)
        }

        val neededSize = 30000000 - (70000000 - root.getSize())
        val dirs = mutableListOf<Directory>()
        findFoldersAboveGivenSize(root, neededSize, dirs)

        val minDir = dirs.minBy { it.getSize() }
        return minDir.getSize()
    }

//    val testInput = readInput("Day07_test")
//    println(part1(testInput))
//    println(part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
