package dev.daschi.year2024.day08

import dev.daschi.util.Input
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Tests for Day 8 of Advent of Code 2024.
 */
class Day08Test {

    companion object {
        @JvmStatic
        fun part1Samples() = listOf(
            // sampleNumberToLoad, expectedResult
            Arguments.of(1, 14)
        )

        @JvmStatic
        fun part2Samples() = listOf(
            // sampleNumberToLoad, expectedResult
            Arguments.of(1, 34)
        )
    }

    @ParameterizedTest(name = "Part 1 Sample {0}")
    @MethodSource("part1Samples")
    fun testPart1Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 8, sampleNumber)
        val day = Day08(sampleInput)
        assertEquals(expected, day.part1())
    }

    @ParameterizedTest(name = "Part 2 Sample {0}")
    @MethodSource("part2Samples")
    fun testPart2Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 8, sampleNumber)
        val day = Day08(sampleInput)
        assertEquals(expected, day.part2())
    }
}
