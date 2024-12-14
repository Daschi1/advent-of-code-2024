package dev.daschi.year2024.day08

import dev.daschi.util.CharGrid
import dev.daschi.util.Input
import dev.daschi.util.Solution
import kotlin.math.abs
import kotlin.math.min

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
        val antinodes = calculateAntinodesPart1()
        return antinodes.size
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        val antinodes = calculateAntinodesPart2()
        return antinodes.size
    }

    /**
     * Part 1: Calculates all antinodes based on the original rules.
     *
     * @return A list of distinct (y, x) positions of antinodes within grid bounds.
     */
    private fun calculateAntinodesPart1(): List<Pair<Int, Int>> {
        val allAntinodes = mutableListOf<Pair<Int, Int>>()

        getFrequencies().forEach { frequency ->
            val positions = grid.findPositions(frequency)
            positions.forEachPair { antenna1, antenna2 ->
                val offset = calculateVectorOffset(antenna1, antenna2)
                allAntinodes.addAll(calculateAntinodePositions(antenna1, antenna2, offset))
            }
        }

        return allAntinodes.distinct().filter { grid.isInBounds(it.first, it.second) }
    }

    /**
     * Part 2: Calculates all antinodes based on resonant harmonics rules.
     *
     * @return A list of distinct (y, x) positions of antinodes within grid bounds.
     */
    private fun calculateAntinodesPart2(): List<Pair<Int, Int>> {
        val allAntinodes = mutableSetOf<Pair<Int, Int>>()

        getFrequencies().forEach { frequency ->
            val positions = grid.findPositions(frequency)
            positions.forEachPair { antenna1, antenna2 ->
                allAntinodes.addAll(
                    calculateResonantHarmonicAntinodes(antenna1, antenna2, grid.width, grid.height)
                )
            }
        }

        return allAntinodes.toList()
    }

    /**
     * Calculates resonant harmonic antinodes for a pair of antennas.
     *
     * @param firstAntenna First antenna position (row, col).
     * @param secondAntenna Second antenna position (row, col).
     * @param width Width of the grid.
     * @param height Height of the grid.
     * @return A list of all resonant harmonic antinodes within bounds.
     */
    private fun calculateResonantHarmonicAntinodes(
        firstAntenna: Pair<Int, Int>, secondAntenna: Pair<Int, Int>, width: Int, height: Int
    ): List<Pair<Int, Int>> {
        val offset = calculateVectorOffset(firstAntenna, secondAntenna)

        val maxVerticalSteps = if (offset.first != 0) abs(height / offset.first) else 0
        val maxHorizontalSteps = if (offset.second != 0) abs(width / offset.second) else 0
        val maxSteps = min(maxVerticalSteps, maxHorizontalSteps)

        return (0..maxSteps).flatMap { step ->
            listOf(
                Pair(
                    firstAntenna.first + step * offset.first,
                    firstAntenna.second + step * offset.second
                ), Pair(
                    secondAntenna.first - step * offset.first,
                    secondAntenna.second - step * offset.second
                )
            )
        }.filter { grid.isInBounds(it.first, it.second) }
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
}
