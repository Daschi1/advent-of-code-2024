package dev.daschi.year2024.day01

import dev.daschi.util.Input
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

/**
 * Tests for Day 1 of Advent of Code 2024.
 */
class Day01Test {

    companion object {
        @JvmStatic
        fun part1Samples() = listOf(
            Arguments.of(sampleNumberToLoad, expectedResult)
        )

        @JvmStatic
        fun part2Samples() = listOf(
            Arguments.of(sampleNumberToLoad, expectedResult)
        )
    }

    @ParameterizedTest(name = "Part 1 Sample {0}")
    @MethodSource("part1Samples")
    fun testPart1Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 1, sampleNumber)
        val day = Day01(sampleInput)
        assertEquals(expected, day.part1())
    }

    @ParameterizedTest(name = "Part 2 Sample {0}")
    @MethodSource("part2Samples")
    fun testPart2Samples(sampleNumber: Int, expected: Any?) {
        val sampleInput = Input.readLines(2024, 1, sampleNumber)
        val day = Day01(sampleInput)
        assertEquals(expected, day.part2())
    }
}
