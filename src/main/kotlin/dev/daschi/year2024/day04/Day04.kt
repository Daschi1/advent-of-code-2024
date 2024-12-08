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
        yStart: Int, xStart: Int, direction: Pair<Int, Int>, word: String
    ): Boolean {
        val height = parsedInput.size
        val width = parsedInput[0].size
        var y = yStart
        var x = xStart

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
        val height = parsedInput.size
        val width = parsedInput[0].size
        var result = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (isXMASPattern(y, x)) {
                    result++
                }
            }
        }

        return result
    }

    private fun isXMASPattern(y: Int, x: Int): Boolean {
        val height = parsedInput.size
        val width = parsedInput[0].size

        // Check bounds for the diagonal lengths
        if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width) return false

        // Top-left to bottom-right diagonal (\)
        val diagonal1 = listOf(
            parsedInput[y - 1][x - 1], // M
            parsedInput[y][x],         // A
            parsedInput[y + 1][x + 1]  // S
        )

        // Top-right to bottom-left diagonal (/)
        val diagonal2 = listOf(
            parsedInput[y - 1][x + 1], // M
            parsedInput[y][x],         // A
            parsedInput[y + 1][x - 1]  // S
        )

        // Check if either diagonal forms "MAS" or "SAM"
        val isDiagonal1Valid =
            diagonal1 == listOf('M', 'A', 'S') || diagonal1 == listOf('S', 'A', 'M')
        val isDiagonal2Valid =
            diagonal2 == listOf('M', 'A', 'S') || diagonal2 == listOf('S', 'A', 'M')

        // Both diagonals must intersect at the middle "A"
        return isDiagonal1Valid && isDiagonal2Valid
    }
}
