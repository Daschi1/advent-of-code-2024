package dev.daschi.util

/**
 * Interface representing a day's solution.
 */
interface Solution {
    val year: Int
    val day: Int

    /**
     * Solves Part 1 of the day's puzzle.
     * @return The result of Part 1.
     */
    fun part1(): Any?

    /**
     * Solves Part 2 of the day's puzzle.
     * @return The result of Part 2.
     */
    fun part2(): Any?
}
