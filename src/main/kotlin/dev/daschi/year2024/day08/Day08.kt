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

    private val grid = CharGrid(input)

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        return calculateAntinodes(::calculateAntinodePositions).size
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        return calculateAntinodes(::calculateResonantHarmonicAntinodes).size
    }

    /**
     * Calculates antinodes using the provided strategy.
     *
     * @param antinodeCalculator A function to calculate antinodes for a pair of antennas.
     * @return A list of distinct (y, x) positions of antinodes within grid bounds.
     */
    private fun calculateAntinodes(
        antinodeCalculator: (Pair<Int, Int>, Pair<Int, Int>) -> List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        getFrequencies().forEach { frequency ->
            val positions = grid.findPositions(frequency)
            positions.forEachPair { antenna1, antenna2 ->
                antinodes.addAll(antinodeCalculator(antenna1, antenna2))
            }
        }

        return antinodes.filter { grid.isInBounds(it.first, it.second) }
    }

    /**
     * Calculates resonant harmonic antinodes for a pair of antennas.
     *
     * @param firstAntenna The position (row, col) of the first antenna.
     * @param secondAntenna The position (row, col) of the second antenna.
     * @return A list of all resonant harmonic antinodes within bounds.
     */
    private fun calculateResonantHarmonicAntinodes(
        firstAntenna: Pair<Int, Int>, secondAntenna: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val offset = calculateVectorOffset(firstAntenna, secondAntenna)
        val maxSteps = min(
            if (offset.first != 0) abs(grid.height / offset.first) else 0,
            if (offset.second != 0) abs(grid.width / offset.second) else 0
        )

        return (0..maxSteps).flatMap { step ->
            listOf(
                firstAntenna + offset * step, secondAntenna - offset * step
            )
        }.filter { grid.isInBounds(it.first, it.second) }
    }

    /**
     * Calculates potential antinode positions for a pair of antennas.
     *
     * @param antenna1 The position (row, col) of the first antenna.
     * @param antenna2 The position (row, col) of the second antenna.
     * @return A list containing two potential antinode positions calculated using the vector offset.
     */
    private fun calculateAntinodePositions(
        antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val offset = calculateVectorOffset(antenna1, antenna2)
        return listOf(antenna1 + offset, antenna2 - offset)
    }

    /**
     * Calculates the vector offset between two antenna positions.
     *
     * @param pos1 The position (row, col) of the first antenna.
     * @param pos2 The position (row, col) of the second antenna.
     * @return A pair representing the offset (rowOffset, colOffset) between the two positions.
     */
    private fun calculateVectorOffset(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Pair<Int, Int> {
        return pos1 - pos2
    }

    /**
     * Retrieves all unique frequencies of antennas from the grid.
     *
     * @return A set of unique characters representing antenna frequencies in the grid.
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
     * @param action A lambda that takes two elements from the list and processes them.
     */
    private fun <T> List<T>.forEachPair(action: (T, T) -> Unit) {
        for (i in indices) {
            for (j in i + 1 until size) {
                action(this[i], this[j])
            }
        }
    }

    /**
     * Operator overloading for adding two pairs.
     *
     * @param other The other pair to add.
     * @return A new pair representing the element-wise sum.
     */
    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    /**
     * Operator overloading for subtracting two pairs.
     *
     * @param other The other pair to subtract.
     * @return A new pair representing the element-wise difference.
     */
    private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first - other.first, this.second - other.second)
    }

    /**
     * Operator overloading for multiplying a pair by a scalar.
     *
     * @param scalar The scalar value to multiply with.
     * @return A new pair with each element multiplied by the scalar.
     */
    private operator fun Pair<Int, Int>.times(scalar: Int): Pair<Int, Int> {
        return Pair(this.first * scalar, this.second * scalar)
    }
}
