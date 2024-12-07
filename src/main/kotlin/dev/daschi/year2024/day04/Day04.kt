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

//        for (y in 0 until height) {
//            for (x in 0 until width) {
//                print(parsedInput[y][x])
//            }
//            print("\n")
//        }
//        println("---")

        for (y in 0 until height) {
            for (x in 0 until width) {
                for (direction in directions) {
                    if (matchWordInGrid(y, x, direction.first, direction.second, word)) {
                        result++
                    }
                }
            }
        }

        return result
    }

    private fun matchWordInGrid(yStart: Int, xStart: Int, dy: Int, dx: Int, word: String): Boolean {
        val height = parsedInput.size
        val width = parsedInput[0].size
        var y = yStart
        var x = xStart
        val traversed = mutableListOf<Pair<Int, Int>>()
        for (i in word.indices) {
            traversed.add(Pair(y, x))
            if (y !in (0 until height) || x !in (0 until width) || word[i] != parsedInput[y][x]) {
                return false
            }
            y += dy
            x += dx
        }
//        println(traversed)
        return true
    }

    /*private fun printGrid(grid: Array<CharArray>) {
        for (row in grid) {
            println(row.joinToString(""))
        }
    }*/

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        // TODO: Implement Part 2
        return -1
    }
}
