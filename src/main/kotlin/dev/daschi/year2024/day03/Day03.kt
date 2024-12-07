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
        val instructionPatterns = listOf(
            """mul\(\d{1,3},\d{1,3}\)""", // Matches "mul(n,m)"
            """do\(\)""",                 // Matches "do()"
            """don't\(\)"""               // Matches "don't()"
        )
        val instructions = findInstructions(parsedInput, instructionPatterns)

        return processInstructions(instructions)
    }

    /**
     * Extracts all substrings from the input string that match any of the provided instruction patterns.
     *
     * @param input the input string to search for patterns
     * @param instructionPatterns a list of regex patterns representing the instruction formats to match
     * @return a list of all substrings matching any of the provided patterns
     */
    private fun findInstructions(input: String, instructionPatterns: List<String>): List<String> {
        val combinedRegex = Regex(instructionPatterns.joinToString(separator = "|"))
        return combinedRegex.findAll(input).map { it.value }.toList()
    }

    /**
     * Processes a list of instructions and calculates the result based on their meanings.
     *
     * @param instructions the list of matched instructions
     * @return the calculated result
     */
    private fun processInstructions(instructions: List<String>): Int {
        var doMulInstructions = true
        return instructions.fold(0) { result, instruction ->
            when {
                instruction.startsWith("don't()") -> {
                    doMulInstructions = false
                    result
                }

                instruction.startsWith("do()") -> {
                    doMulInstructions = true
                    result
                }

                instruction.startsWith("mul(") && doMulInstructions -> {
                    result + parseMulInstruction(instruction)
                }

                else -> result
            }
        }
    }

    /**
     * Parses a "mul(n,m)" instruction and returns the product of n and m.
     *
     * @param instruction the "mul(n,m)" instruction to parse
     * @return the product of the two integers
     */
    private fun parseMulInstruction(instruction: String): Int {
        val cleanedInstruction = instruction.removePrefix("mul(").removeSuffix(")")
        val (m, n) = cleanedInstruction.split(",").map { it.toInt() }
        return m * n
    }
}
