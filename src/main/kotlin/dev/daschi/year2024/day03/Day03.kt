package dev.daschi.year2024.day03

import dev.daschi.util.Input
import dev.daschi.util.Solution

/**
 * Solution for Day 3 of Advent of Code 2024.
 */
class Day03(
    input: List<String> = Input.readLines(2024, 3)
) : Solution {
    override val year = 2024
    override val day = 3

    private val parsedInput = parseInput(input)

    private fun parseInput(input: List<String>): String {
        return input.joinToString(separator = "")
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        val mulInstructions = findMulInstructions(parsedInput)
        var result = 0

        mulInstructions.forEach {
            val cleanedInstruction = it.replace("mul(", "").replace(")", "")
            val splitInstruction = cleanedInstruction.split(",")
            val m = splitInstruction[0].toInt()
            val n = splitInstruction[1].toInt()
            val mul = m * n
            result += mul
        }

        return result
    }

    /**
     * Extracts all substrings from the input string that match the exact pattern "mul(n,m)".
     * The pattern matches:
     * - A literal "mul("
     * - Followed by a number (1 to 3 digits)
     * - A comma ","
     * - Another number (1 to 3 digits)
     * - A closing parenthesis ")"
     *
     * @param input the input string to search for patterns
     * @return a list of all substrings matching the pattern "mul(n,m)"
     */
    private fun findMulInstructions(input: String): List<String> {
        val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        return regex.findAll(input).map { it.value }.toList()
    }


    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        // TODO: Implement Part 2
        return -1
    }
}
