package dev.daschi.year2024.day03

import dev.daschi.util.Input
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Tests for Day 3 of Advent of Code 2024.
 */
class Day03Test {

    companion object {
        @JvmStatic
        fun part1Samples() = listOf(
            // sampleNumberToLoad, expectedResult
            Arguments.of(1, 161)
        )

        @JvmStatic
        fun part2Samples() = listOf(
            // sampleNumberToLoad, expectedResult
            Arguments.of(2, 48)
        )
    }

    @ParameterizedTest(name = "Part 1 Sample {0}")
    @MethodSource("part1Samples")
    fun testPart1Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 3, sampleNumber)
        val day = Day03(sampleInput)
        assertEquals(expected, day.part1())
    }

    @ParameterizedTest(name = "Part 2 Sample {0}")
    @MethodSource("part2Samples")
    fun testPart2Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 3, sampleNumber)
        val day = Day03(sampleInput)
        assertEquals(expected, day.part2())
    }
}
