package dev.daschi.year2024.day01

import dev.daschi.util.Input
import dev.daschi.util.Solution
import java.util.Arrays

/**
 * Solution for Day 1 of Advent of Code 2024.
 */
class Day01(
    input: List<String> = Input.readLines(2024, 1)
) : Solution {
    override val year = 2024
    override val day = 1

    private val parsedInput = parseInput(input)

    private fun parseInput(input: List<String>): Pair<IntArray, IntArray> {
        val size = input.size
        val first = IntArray(size)
        val second = IntArray(size)
        input.forEachIndexed { i, s ->
            val split = s.split("   ")
            first[i] = split[0].toInt()
            second[i] = split[1].toInt()
        }
        return Pair(first, second)
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any? {
        // TODO: Implement Part 1

        println(parsedInput.first.contentToString())
        println(parsedInput.second.contentToString())

        return null
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any? {
        // TODO: Implement Part 2
        return null
    }
}
