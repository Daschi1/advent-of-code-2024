package dev.daschi.year2024.day02

import dev.daschi.util.Input
import dev.daschi.util.Solution
import kotlin.math.abs

/**
 * Solution for Day 2 of Advent of Code 2024.
 */
class Day02(
    input: List<String> = Input.readLines(2024, 2)
) : Solution {
    override val year = 2024
    override val day = 2

    private val parsedInput = parseInput(input)

    private fun parseInput(input: List<String>): List<IntArray> {
        return input.map { s -> s.split(" ").map { it.toInt() }.toIntArray() }
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        val safeReports = mutableListOf<IntArray>()

        for (report in parsedInput) {
            if (!report.isStrictlyMonotonic()) {
                continue
            }
            if (!report.hasStepDifferenceInRange(1, 3)) {
                continue
            }
            safeReports.add(report)
        }

        return safeReports.size
    }

    /**
     * Checks if the elements of the array are strictly increasing (each element is less than the next).
     *
     * @return `true` if the array is strictly increasing, otherwise `false`.
     */
    private fun IntArray.isStrictlyIncreasing(): Boolean {
        return this.pairwiseCheck { a, b -> a < b }
    }

    /**
     * Checks if the elements of the array are strictly decreasing (each element is greater than the next).
     *
     * @return `true` if the array is strictly decreasing, otherwise `false`.
     */
    private fun IntArray.isStrictlyDecreasing(): Boolean {
        return this.pairwiseCheck { a, b -> a > b }
    }

    /**
     * Checks if the array is strictly monotonic, meaning it is either strictly increasing or strictly decreasing.
     *
     * @return `true` if the array is strictly monotonic, otherwise `false`.
     */
    private fun IntArray.isStrictlyMonotonic(): Boolean {
        return this.isStrictlyIncreasing() || this.isStrictlyDecreasing()
    }

    /**
     * Checks if the difference between consecutive elements in the array is at least the given minimum value.
     *
     * @param minimum The minimum step difference allowed between consecutive elements.
     * @return `true` if all consecutive differences are greater than or equal to the minimum, otherwise `false`.
     */
    private fun IntArray.hasMinimumStepDifference(minimum: Int): Boolean {
        return this.pairwiseCheck { a, b -> abs(a - b) >= minimum }
    }

    /**
     * Checks if the difference between consecutive elements in the array is at most the given maximum value.
     *
     * @param maximum The maximum step difference allowed between consecutive elements.
     * @return `true` if all consecutive differences are less than or equal to the maximum, otherwise `false`.
     */
    private fun IntArray.hasMaximumStepDifference(maximum: Int): Boolean {
        return this.pairwiseCheck { a, b -> abs(a - b) <= maximum }
    }

    /**
     * Checks if the difference between consecutive elements in the array is within a specified range.
     *
     * @param minimum The minimum step difference allowed between consecutive elements.
     * @param maximum The maximum step difference allowed between consecutive elements.
     * @return `true` if all consecutive differences are within the range [minimum, maximum], otherwise `false`.
     */
    private fun IntArray.hasStepDifferenceInRange(minimum: Int, maximum: Int): Boolean {
        return this.hasMinimumStepDifference(minimum) && this.hasMaximumStepDifference(maximum)
    }

    /**
     * A helper function that applies a given predicate to all consecutive pairs of elements in the array.
     *
     * @param predicate A lambda function that takes two integers (a, b) and returns a Boolean.
     * @return `true` if the predicate returns `true` for all consecutive pairs, otherwise `false`.
     */
    private inline fun IntArray.pairwiseCheck(predicate: (Int, Int) -> Boolean): Boolean {
        return this.asIterable().zipWithNext().all { (a, b) -> predicate(a, b) }
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        val safeReports = mutableListOf<IntArray>()

        for (report in parsedInput) {
            for (i in report.indices) {
                // Create the partial report by excluding the element at index `i`
                val partialReport =
                    report.copyOfRange(0, i) + report.copyOfRange(i + 1, report.size)

                if (!partialReport.isStrictlyMonotonic()) {
                    continue
                }
                if (!partialReport.hasStepDifferenceInRange(1, 3)) {
                    continue
                }

                // If a partial report is valid, add the original report to the safeReports list and exit the loop early.
                // Only one valid partial report is needed to mark the original report as safe.
                safeReports.add(report)
                break
            }
        }

        return safeReports.size
    }
}
