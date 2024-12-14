package dev.daschi.year2024.day08

import dev.daschi.util.CharGrid
import dev.daschi.util.Input
import dev.daschi.util.Solution

/**
 * Solution for Day 8 of Advent of Code 2024.
 */
class Day08(
    input: List<String> = Input.readLines(2024, 8)
) : Solution {
    override val year = 2024
    override val day = 8

    private val grid = parseInput(input)

    private fun parseInput(input: List<String>): CharGrid {
        return CharGrid(input)
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        val antinodes = calculateAntinodes()
        return antinodes.size
    }

    /**
     * Retrieves all unique antinodes from the grid.
     *
     * @return A list of distinct (row, col) positions of antinodes within grid bounds.
     */
    private fun calculateAntinodes(): List<Pair<Int, Int>> {
        val allAntinodes = mutableListOf<Pair<Int, Int>>()

        // Iterate through all unique frequencies in the grid
        getFrequencies().forEach { frequency ->
            val positions = grid.findPositions(frequency)

            // For each pair of antennas of the same frequency, calculate potential antinodes
            positions.forEachPair { antenna1, antenna2 ->
                val offset = calculateVectorOffset(antenna1, antenna2)
                allAntinodes.addAll(calculateAntinodePositions(antenna1, antenna2, offset))
            }
        }

        // Filter unique antinodes within bounds
        return allAntinodes.distinct().filter { grid.isInBounds(it.first, it.second) }
    }

    /**
     * Calculates the vector offset between two antenna positions.
     *
     * @param pos1 First antenna position (row, col).
     * @param pos2 Second antenna position (row, col).
     * @return The offset vector (rowOffset, colOffset).
     */
    private fun calculateVectorOffset(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(pos1.first - pos2.first, pos1.second - pos2.second)
    }

    /**
     * Calculates potential antinode positions for a pair of antennas and their offset vector.
     *
     * @param antenna1 First antenna position (row, col).
     * @param antenna2 Second antenna position (row, col).
     * @param offset The vector offset between the two antennas.
     * @return A list of two potential antinode positions.
     */
    private fun calculateAntinodePositions(
        antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>, offset: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        return listOf(
            Pair(antenna1.first + offset.first, antenna1.second + offset.second),
            Pair(antenna2.first - offset.first, antenna2.second - offset.second)
        )
    }

    /**
     * Retrieves all unique frequencies of antennas from the grid.
     *
     * @return A set of unique frequencies found in the grid.
     */
    private fun getFrequencies(): Set<Char> {
        val frequencies = mutableSetOf<Char>()
        grid.forEach { _, _, char ->
            if (char != '.') { // Ignore empty cells
                frequencies.add(char)
            }
        }
        return frequencies
    }

    /**
     * Extension function to iterate over all unique pairs of elements in a list.
     *
     * @param action A lambda that takes two elements from the list.
     */
    private fun <T> List<T>.forEachPair(action: (T, T) -> Unit) {
        for (i in 0 until this.size - 1) {
            for (j in i + 1 until this.size) {
                action(this[i], this[j])
            }
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
