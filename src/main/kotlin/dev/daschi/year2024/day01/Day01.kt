package dev.daschi.year2024.day01

import dev.daschi.util.Input
import dev.daschi.util.Solution
import kotlin.math.abs

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
    override fun part1(): Any {
        val firstSorted = parsedInput.first.sorted()
        val secondSorted = parsedInput.second.sorted()
        if (firstSorted.size != secondSorted.size) {
            throw IllegalArgumentException("First and second are part not the same size.")
        }

        val distance = IntArray(firstSorted.size)
        for (i in firstSorted.indices) {
            distance[i] = abs(firstSorted[i] - secondSorted[i])
        }

        return distance.sum()
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        val first = parsedInput.first
        val second = parsedInput.second
        if (first.size != second.size) {
            throw IllegalArgumentException("First and second are part not the same size.")
        }

        val secondEachCount = second.groupBy { it }.mapValues { it.value.size }
        var similarityScore = 0
        for (i in first.indices) {
            val currentNumber = first[i]
            val countInSecond = secondEachCount[currentNumber] ?: 0
            similarityScore += currentNumber * countInSecond
        }

        return similarityScore
    }
}
