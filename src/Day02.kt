import kotlin.random.Random

enum class RockPaperScissors(val shapeScore: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

enum class GameState(val roundScore: Int) {
    LOSS(0), DRAW(3), WIN(6)
}

fun main() {
    fun part1(input: List<String>): Int {
        val operations = input.map { it.split(' ')}
        var score = 0
        for ((opponent, response) in operations) {
            val opponentMove = when (opponent) {
                "A" -> RockPaperScissors.ROCK
                "B" -> RockPaperScissors.PAPER
                else -> RockPaperScissors.SCISSORS
            }
            val myMove = when (response) {
                "X" -> RockPaperScissors.ROCK
                "Y" -> RockPaperScissors.PAPER
                else -> RockPaperScissors.SCISSORS
            }
            score += myMove.shapeScore
            score += when {
                opponentMove == myMove -> GameState.DRAW.roundScore
                opponentMove == RockPaperScissors.ROCK && myMove == RockPaperScissors.PAPER
                        || opponentMove == RockPaperScissors.PAPER && myMove == RockPaperScissors.SCISSORS
                        || opponentMove == RockPaperScissors.SCISSORS && myMove == RockPaperScissors.ROCK
                -> GameState.WIN.roundScore

                else -> GameState.LOSS.roundScore
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        val operations = input.map { it.split(' ')}
        var score = 0
        for ((opponent, response) in operations) {
            val opponentMove = when (opponent) {
                "A" -> RockPaperScissors.ROCK
                "B" -> RockPaperScissors.PAPER
                else -> RockPaperScissors.SCISSORS
            }
            val endResult = when (response) {
                "X" -> GameState.LOSS
                "Y" -> GameState.DRAW
                else -> GameState.WIN
            }
            score += endResult.roundScore

            when (endResult) {
                GameState.LOSS -> {
                    score += when (opponentMove) {
                        RockPaperScissors.ROCK -> RockPaperScissors.SCISSORS.shapeScore
                        RockPaperScissors.PAPER -> RockPaperScissors.ROCK.shapeScore
                        RockPaperScissors.SCISSORS -> RockPaperScissors.PAPER.shapeScore
                    }
                }
                GameState.WIN -> {
                    score += when (opponentMove) {
                        RockPaperScissors.ROCK -> RockPaperScissors.PAPER.shapeScore
                        RockPaperScissors.PAPER -> RockPaperScissors.SCISSORS.shapeScore
                        RockPaperScissors.SCISSORS -> RockPaperScissors.ROCK.shapeScore
                    }

                }
                else -> {
                    score += opponentMove.shapeScore
                }
            }
        }
        return score
    }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))


    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
