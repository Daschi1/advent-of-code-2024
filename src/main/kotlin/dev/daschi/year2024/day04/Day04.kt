package dev.daschi.year2024.day04

import dev.daschi.util.Input
import dev.daschi.util.Solution

/**
 * Solution for Day 4 of Advent of Code 2024.
 */
class Day04(
    input: List<String> = Input.readLines(2024, 4)
) : Solution {
    override val year = 2024
    override val day = 4

    private val parsedInput = parseInput(input)

    private fun parseInput(input: List<String>): Array<CharArray> {
        val height = input.size
        val width = input[0].length
        val grid = Array(height) { CharArray(width) }
        for (y in 0 until height) {
            val line = input[y]
            for (x in 0 until width) {
                grid[y][x] = line[x]
            }
        }
        return grid
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        val directions = listOf(
            Pair(1, 0), Pair(-1, 0), // Horizontal
            Pair(0, 1), Pair(0, -1), // Vertical
            Pair(1, 1), Pair(-1, -1), // Diagonal down-right, up-left
            Pair(-1, 1), Pair(1, -1)  // Diagonal down-left, up-right
        )
        var result = 0

        val height = parsedInput.size
        val width = parsedInput[0].size
        val word = "XMAS"

        for (y in 0 until height) {
            for (x in 0 until width) {
                for (direction in directions) {
                    if (matchWordInGrid(y, x, direction, word)) {
                        result++
                    }
                }
            }
        }

        return result
    }

    private fun matchWordInGrid(
        yStart: Int,
        xStart: Int,
        direction: Pair<Int, Int>,
        word: String
    ): Boolean {
        val height = parsedInput.size
        val width = parsedInput[0].size
        var y = yStart
        var x = xStart
        println("y: $y, x: $x, direction: $direction")
        for (i in word.indices) {
            if (y !in (0 until height) || x !in (0 until width) || word[i] != parsedInput[y][x]) {
                return false
            }
            y += direction.first
            x += direction.second
        }
        return true
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        val directions = mapOf(
            Pair("down-right", Pair(1, 1)),
            Pair("up-left", Pair(-1, -1)),
            Pair("down-left", Pair(1, -1)),
            Pair("up-right", Pair(-1, 1)),
        )
        var result = 0

        val height = parsedInput.size
        val width = parsedInput[0].size
        val word = "MAS"

        for (y in 0 until height) {
            for (x in 0 until width) {
                for ((name, direction) in directions) {
                    if (matchWordInGrid(y, x, direction, word)) {
                        when (name) {
                            "down-right" ->
                                if (
                                    matchWordInGrid(y + 2, x, directions["up-right"]!!, word) ||
                                    matchWordInGrid(y, x + 2, directions["down-left"]!!, word)
                                ) {
                                    result++
                                    print("y: $y, x: $x, name: $name")
                                    if (matchWordInGrid(y + 2, x, directions["up-right"]!!, word)) {
                                        val toY = y + 2
                                        val toX = x
                                        print("; y: $toY, x: $toX, name: up-right")
                                    }
                                    if ( matchWordInGrid(y, x + 2, directions["down-left"]!!, word)) {
                                        val toY = y
                                        val toX = x + 2
                                        print("; y: $toY, x: $toX, name: up-right")
                                    }
                                    println("---")
                                }

                            "up-left" -> if (
                                matchWordInGrid(y - 2, x, directions["down-left"]!!, word) ||
                                matchWordInGrid(y, x - 2, directions["up-right"]!!, word)
                            ) result++

                            "down-left" -> if (
                                matchWordInGrid(y + 2, x, directions["up-left"]!!, word) ||
                                matchWordInGrid(y, x - 2, directions["down-right"]!!, word)
                            ) result++

                            "up-right" -> if (
                                matchWordInGrid(y - 2, x, directions["down-right"]!!, word) ||
                                matchWordInGrid(y, x + 2, directions["up-left"]!!, word)
                            ) result++
                        }
                    }
                }
            }
        }

        return result
    }
}
