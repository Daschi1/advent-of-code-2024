package dev.daschi.year2024.day07

import dev.daschi.util.Input
import dev.daschi.util.Solution
import kotlin.math.pow


/**
 * Solution for Day 7 of Advent of Code 2024.
 */
class Day07(
    input: List<String> = Input.readLines(2024, 7)
) : Solution {
    override val year = 2024
    override val day = 7

    private val equations = parseInput(input)

    private fun parseInput(input: List<String>): List<Equation> {
        return input.map { line ->
            val resultNumbersSplit = line.split(":")
            val result = resultNumbersSplit[0].toLong()
            val numbers = resultNumbersSplit[1].trimStart().split(" ").map { it.toLong() }
            Equation(result, numbers)
        }
    }

    private data class Equation(val result: Long, val numbers: List<Long>)

    private enum class Operator {
        PLUS, MULTIPLY
    }

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        val availableOperators = listOf(Operator.PLUS, Operator.MULTIPLY)
        return equations.filter { isEquationSolvable(it, availableOperators) }.sumOf { it.result }
    }

    private fun isEquationSolvable(
        equation: Equation, availableOperators: List<Operator>
    ): Boolean {
        // One less operator position than numbers
        val operatorPositions = equation.numbers.size - 1
        val totalCombinations = availableOperators.size.toDouble().pow(operatorPositions).toInt()

        // Iterate through all possible combinations
        for (index in 0 until totalCombinations) {
            // Generate the current combination of operators based on the index
            val combination =
                generateOperatorCombination(index, operatorPositions, availableOperators)

            // Test the current combination
            if (testSolution(equation, combination)) {
                // Solution found, since the current operator combination solves the equation.
                return true
            }
        }

        // No solution found, since all tested operator combinations do not solve the equation.
        return false
    }

    /**
     * Generates a specific combination of operators based on a given index.
     *
     * This function interprets the `index` as a number in a base system where the base is the size of
     * the `availableOperators` list. Each "digit" in this base system corresponds to an operator, and
     * the function calculates the sequence of operators for the specified `numberOfPositions`.
     *
     * @param combinationIndex The numeric representation of the combination (0-based).
     *                         Think of this as the position in a sequence of all possible combinations.
     * @param numberOfPositions The number of operator positions to fill (e.g., one less than the number of numbers in an equation).
     * @param availableOperators The list of available operators (e.g., [PLUS, MULTIPLY]).
     * @return A list of operators representing the combination for the given `combinationIndex`.
     *         The combination is built in reverse order, if order matters, reverse the list!
     */
    private fun generateOperatorCombination(
        combinationIndex: Int, numberOfPositions: Int, availableOperators: List<Operator>
    ): List<Operator> {
        // The remaining part of the index we are converting
        var remainingIndex = combinationIndex
        val operatorCombination = mutableListOf<Operator>()

        // Iterate for each position in the operator combination
        repeat(numberOfPositions) {
            // Use modulo to determine the operator for the current position.
            // Modulo (%) extracts the "last digit" of `remainingIndex` in the current base (availableOperators.size).
            // Example for this use case:
            //   If remainingIndex = 5, availableOperators = [PLUS, MULTIPLY], and base = 2:
            //   5 % 2 = 1 → This corresponds to MULTIPLY (the operator at index 1 in the list).
            // Common base examples:
            //   - Base 10: If remainingIndex = 123 and base = 10, then 123 % 10 = 3 (last digit in decimal).
            //   - Base 2: If remainingIndex = 5 (binary: 101) and base = 2, then 5 % 2 = 1 (last binary digit).
            val operatorForPosition = availableOperators[remainingIndex % availableOperators.size]

            // Add the determined operator to the combination
            operatorCombination.add(operatorForPosition)

            // Use integer division to "remove the last digit" we just processed.
            // This effectively shifts the number to the left in the current base system.
            // Example for this use case:
            //   If remainingIndex = 5, base = 2:
            //   5 / 2 = 2 → The remainingIndex becomes 2, which represents the next part of the combination.
            // Common base examples:
            //   - Base 10: If remainingIndex = 123 and base = 10, then 123 / 10 = 12 (removes the last digit "3").
            //   - Base 2: If remainingIndex = 5 (binary: 101) and base = 2, then 5 / 2 = 2 (removes the last binary digit "1").
            remainingIndex /= availableOperators.size
        }

        return operatorCombination
    }

    private fun testSolution(equation: Equation, operators: List<Operator>): Boolean {
        require(equation.numbers.size - 1 == operators.size) {
            "The number of operations $operators must be exactly one less than the number of numbers ${equation.numbers} in the equation."
        }

        // Set the initial result to the first number in the equation, to act as the initial left part for the operation.
        var result = equation.numbers[0]
        // Iterate over the numbers starting from the second number (index 1) to the last number.
        // Each operation is applied between the current result and the next number in the sequence.
        // The corresponding operator from the `operators` list determines the operation to perform.
        for (i in 1..equation.numbers.lastIndex) {
            val right = equation.numbers[i]
            when (operators[i - 1]) {
                Operator.PLUS -> result += right
                Operator.MULTIPLY -> result *= right
            }
        }

        return result == equation.result
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        // TODO: Implement Part 2
        return -1
    }
}
