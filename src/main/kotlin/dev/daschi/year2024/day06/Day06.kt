package dev.daschi.year2024.day06

import dev.daschi.util.CharGrid
import dev.daschi.util.Input
import dev.daschi.util.Solution

/**
 * Solution for Day 6 of Advent of Code 2024.
 */
class Day06(
    input: List<String> = Input.readLines(2024, 6)
) : Solution {
    override val year = 2024
    override val day = 6

    private val charGrid = parseInput(input)

    private fun parseInput(input: List<String>): CharGrid {
        return CharGrid(input)
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        charGrid.printGrid()
        println(charGrid.findPositions('^'))
        println(charGrid.height)
        println(charGrid.width)
        val guardPath = predictGuardPath()
        println(guardPath)
        println(guardPath.size)
        println(guardPath.distinct())
        println(guardPath.distinct().size)
        return guardPath.distinct().size
    }

    private fun predictGuardPath(): List<Pair<Int, Int>> {
        val guardPath = mutableListOf<Triple<Int, Int, CharGrid.Direction>>()
        var currentDirection = CharGrid.Direction.UP
        var (currentY, currentX) = charGrid.findPositions('^').first()
        guardPath.add(Triple(currentY, currentX, currentDirection))
        var predict = true

        println("Starting predictGuardPath")
        println("Initial position: (currentY=$currentY, currentX=$currentX), Initial direction: $currentDirection")

        while (predict) {
            try {
                println("\nAttempting to move from (currentY=$currentY, currentX=$currentX) in direction: $currentDirection")
                val (nextY, nextX) = charGrid.moveTo(currentY, currentX, currentDirection)
                val nextTile = charGrid.get(nextY, nextX)
                println("Moved to (nextY=$nextY, nextX=$nextX), Tile: $nextTile")

                when (nextTile) {
                    '.', '^' -> {
                        guardPath.add(Triple(nextY, nextX, currentDirection)) // Add direction
                        println("Added to path: ($nextY, $nextX) with direction: $currentDirection")
                        currentY = nextY
                        currentX = nextX
                    }

                    '#' -> {
                        currentDirection = turnToRight(currentDirection)
                        println("Hit wall (#), turning to new direction: $currentDirection")
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                println("Out of bounds exception encountered. Stopping prediction.")
                predict = false
            }
        }

        // Map directions to arrow symbols
        val directionToArrow = mapOf(
            CharGrid.Direction.UP to '^',
            CharGrid.Direction.RIGHT to '>',
            CharGrid.Direction.DOWN to 'v',
            CharGrid.Direction.LEFT to '<'
        )

        // Print grid with arrows
        charGrid.forEach { y, x, char ->
            var charToPrint = char
            guardPath.find { it.first == y && it.second == x }?.let { (_, _, direction) ->
                charToPrint = directionToArrow[direction] ?: 'X' // Get arrow for direction
            }
            if (x == charGrid.width - 1) charToPrint = '\n'
            if (char == '^') charToPrint = 'S'
            print(charToPrint)
        }
        println("Final guardPath: $guardPath")
        return guardPath.map { it.first to it.second } // Return positions only
    }

    private fun turnToRight(direction: CharGrid.Direction): CharGrid.Direction {
        return when (direction) {
            CharGrid.Direction.UP -> CharGrid.Direction.RIGHT
            CharGrid.Direction.RIGHT -> CharGrid.Direction.DOWN
            CharGrid.Direction.DOWN -> CharGrid.Direction.LEFT
            CharGrid.Direction.LEFT -> CharGrid.Direction.UP
            else -> throw IllegalArgumentException("Cannot turn right for direction: $direction.")
        }
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        // TODO: Implement Part 2
        return -1
    }
}
