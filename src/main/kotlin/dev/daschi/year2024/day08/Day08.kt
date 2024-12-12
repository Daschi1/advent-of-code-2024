package dev.daschi.year2024.day08

import dev.daschi.util.CharGrid
import dev.daschi.util.Input
import dev.daschi.util.Solution
import kotlin.math.abs

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
        grid.printGrid()
        println(calculateAntinodes())
        return -1
    }

    /**
     * Calculates all positions of antinodes on the grid.
     * Antinodes are points aligned with two antennas of the same frequency,
     * where one antenna is twice as far from the point as the other.
     *
     * @return A list of all (y, x) positions of antinodes, including duplicates.
     */
    private fun calculateAntinodes(): List<Pair<Int, Int>> {
        val allAntinodes = mutableListOf<Pair<Int, Int>>() // List to collect all antinodes

        // Iterate over all unique frequencies present in the grid
        getFrequencies().forEach { frequency ->
            // Find all positions of antennas with the current frequency
            val antennaPositions = grid.findPositions(frequency)

            // Check all pairs of antennas for potential antinodes
            antennaPositions.forEachPair { antenna1, antenna2 ->
                println("pair: $antenna1, $antenna2; arePositionsAligned: ${arePositionsAligned(antenna1, antenna2)}")
                // Ensure the two antennas are aligned
                if (arePositionsAligned(antenna1, antenna2)) {
                    // Calculate the potential antinodes
                    val (antinode1, antinode2) = calculatePotentialAntinodes(antenna1, antenna2)

                    // Add antinodes to the list if they are valid
                    if (isAntinodeValid(antinode1, antenna1, antenna2)) allAntinodes.add(antinode1)
                    if (isAntinodeValid(antinode2, antenna1, antenna2)) allAntinodes.add(antinode2)
                }
            }
        }

        return allAntinodes // Return all collected antinodes
    }

    /**
     * Determines if two positions are aligned either horizontally, vertically, or diagonally.
     *
     * @param pos1 The first position as a pair (y, x).
     * @param pos2 The second position as a pair (y, x).
     * @return True if the positions are aligned, false otherwise.
     */
    private fun arePositionsAligned(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Boolean {
        return true
        return pos1.second == pos2.second || // Same column (vertical alignment)
                pos1.first == pos2.first || // Same row (horizontal alignment)
                abs(pos1.first - pos2.first) == abs(pos1.second - pos2.second) // Diagonal alignment
    }

    /**
     * Calculates two potential antinode positions for a pair of aligned antennas.
     *
     * @param antenna1 The first antenna's position as a pair (y, x).
     * @param antenna2 The second antenna's position as a pair (y, x).
     * @return A pair of antinode positions as pairs (y, x).
     */
    private fun calculatePotentialAntinodes(
        antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val midY = (antenna1.first + antenna2.first) / 2 // Midpoint y-coordinate
        val midX = (antenna1.second + antenna2.second) / 2 // Midpoint x-coordinate

        // Calculate antinodes based on the midpoint
        val antinode1 = Pair(midY - (antenna1.first - midY), midX - (antenna1.second - midX))
        val antinode2 = Pair(midY + (antenna2.first - midY), midX + (antenna2.second - midX))
        return Pair(antinode1, antinode2)
    }

    /**
     * Validates if a candidate antinode satisfies the 2:1 distance condition relative to two antennas.
     *
     * @param candidate The candidate antinode position as a pair (y, x).
     * @param antenna1 The first antenna's position as a pair (y, x).
     * @param antenna2 The second antenna's position as a pair (y, x).
     * @return True if the candidate is a valid antinode, false otherwise.
     */
    private fun isAntinodeValid(
        candidate: Pair<Int, Int>, antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>
    ): Boolean {
        val distanceToAntenna1 = calculateManhattanDistance(candidate, antenna1)
        val distanceToAntenna2 = calculateManhattanDistance(candidate, antenna2)

        // Check if the 2:1 ratio condition is satisfied
        return distanceToAntenna1 == 2 * distanceToAntenna2 || distanceToAntenna2 == 2 * distanceToAntenna1
    }

    /**
     * Calculates the Manhattan distance between two positions.
     *
     * @param pos1 The first position as a pair (y, x).
     * @param pos2 The second position as a pair (y, x).
     * @return The Manhattan distance between the two positions.
     */
    private fun calculateManhattanDistance(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Int {
        return abs(pos1.first - pos2.first) + abs(pos1.second - pos2.second)
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
            for (j in i + 1 until this.size) { // Iterate over pairs without repeating
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
