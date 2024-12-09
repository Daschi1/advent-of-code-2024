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
        val guardPath = predictGuardPath()

        // Make distinct positions based only on coordinates (ignoring direction)
        val distinctPositions = guardPath.map { it.first to it.second }.distinct()
        return distinctPositions.size
    }

    private fun predictGuardPath(): List<Triple<Int, Int, CharGrid.Direction>> {
        val guardPath = mutableListOf<Triple<Int, Int, CharGrid.Direction>>()
        val visited =
            mutableSetOf<Pair<Pair<Int, Int>, CharGrid.Direction>>() // To store visited positions with directions

        var currentDirection = CharGrid.Direction.UP
        var (currentY, currentX) = charGrid.findPositions('^').first()
        guardPath.add(Triple(currentY, currentX, currentDirection)) // Add the starting position
        visited.add(Pair(Pair(currentY, currentX), currentDirection)) // Mark as visited

        var predict = true
        while (predict) {
            try {
                val (nextY, nextX) = charGrid.moveTo(currentY, currentX, currentDirection)
                val nextTile = charGrid.get(nextY, nextX)

                when (nextTile) {
                    '.', '^' -> {
                        // Add new position to path
                        currentY = nextY
                        currentX = nextX
                        val currentState = Pair(Pair(currentY, currentX), currentDirection)
                        if (!visited.add(currentState)) {
                            throw IllegalStateException("Loop detected at position (y: $currentY, x: $currentX) facing $currentDirection.")
                        }
                        guardPath.add(Triple(currentY, currentX, currentDirection))
                    }

                    '#' -> {
                        // Turn right on obstacle
                        currentDirection = turnToRight(currentDirection)
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                // Stop prediction when out of bounds
                predict = false
            }
        }

        return guardPath
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
        val potentialLoops = calculatePotentialLoops()
        println(potentialLoops)
        println(potentialLoops.size)
        println(potentialLoops.distinct())
        println(potentialLoops.distinct().size)
        return potentialLoops.distinct().size
    }

    private fun calculatePotentialLoops(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val originalGuardPath = predictGuardPath()

        for (step in originalGuardPath) {
            val oldChar = charGrid.get(step.first, step.second)
            if (oldChar == '^') {
                // Placing an obstacle at the start is not allowed
                continue
            }
            charGrid.set(step.first, step.second, '#')
            charGrid.printGrid()
            println("y: ${step.first}, x: ${step.second}")
            try {
                val modifiedPath = predictGuardPath()

                // Map directions to arrow symbols
                val directionToArrow = mapOf(
                    CharGrid.Direction.UP to '^',
                    CharGrid.Direction.RIGHT to '>',
                    CharGrid.Direction.DOWN to 'v',
                    CharGrid.Direction.LEFT to '<'
                )

//                // Print grid with arrows
//                charGrid.forEach { y, x, char ->
//                    var charToPrint = char
//                    modifiedPath.find { it.first == y && it.second == x }
//                        ?.let { (_, _, direction) ->
//                            charToPrint =
//                                directionToArrow[direction] ?: 'X' // Get arrow for direction
//                        }
//                    if (char == '^') charToPrint = 'S'
//                    print(charToPrint)
//                    if (x == charGrid.width - 1) print('\n')
//                }
            } catch (ignored: IllegalStateException) {
                println(ignored.message)
                result.add(Pair(step.first, step.second))
            }
            charGrid.set(step.first, step.second, oldChar)
            println("----------------------")
        }

        return result
    }
}
