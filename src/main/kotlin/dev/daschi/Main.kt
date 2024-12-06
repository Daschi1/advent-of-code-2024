package dev.daschi

import dev.daschi.util.Solution
import java.io.File
import kotlin.reflect.full.primaryConstructor
import kotlin.system.exitProcess

/**
 * Main function to run the solution.
 *
 * Command-line arguments:
 * - year (required)
 * - day (required)
 * - part (optional): "1", "2", or "both" (default: "both")
 * - outputOption (optional): "--output=console|file|both" (default: "both")
 */
fun main(args: Array<String>) {
    try {
        if (args.size < 2) {
            println("Usage: <year> <day> [part] [--output=console|file|both]")
            exitProcess(1)
        }

        val year = args[0].toIntOrNull() ?: run {
            println("Invalid year. Must be an integer.")
            exitProcess(1)
        }
        val day = args[1].toIntOrNull() ?: run {
            println("Invalid day. Must be an integer.")
            exitProcess(1)
        }
        val dayPadded = day.toString().padStart(2, '0')

        val part = args.getOrNull(2) ?: "both"
        val outputOptionArg = args.find { it.startsWith("--output=") }
        val outputOption = outputOptionArg?.substringAfter("=") ?: "both"

        val className = "dev.daschi.year$year.day$dayPadded.Day$dayPadded"
        val solution = loadSolution(className)

        val results = mutableListOf<String>()

        when (part) {
            "1" -> results.add("Year ${solution.year} Day $dayPadded Part 1: ${solution.part1()}")
            "2" -> results.add("Year ${solution.year} Day $dayPadded Part 2: ${solution.part2()}")
            "both" -> {
                results.add("Year ${solution.year} Day $dayPadded Part 1: ${solution.part1()}")
                results.add("Year ${solution.year} Day $dayPadded Part 2: ${solution.part2()}")
            }

            else -> {
                println("Invalid part: '$part'. Expected '1', '2', or 'both'.")
                exitProcess(1)
            }
        }

        when (outputOption) {
            "console" -> results.forEach(::println)
            "file" -> saveResultsToFile(year, day, results)
            "both" -> {
                results.forEach(::println)
                saveResultsToFile(year, day, results)
            }

            else -> {
                println("Invalid output option: '$outputOption'. Expected 'console', 'file', or 'both'.")
                exitProcess(1)
            }
        }
    } catch (e: Exception) {
        println("An error occurred:")
        e.printStackTrace()
        exitProcess(1)
    }
}

/**
 * Loads the solution class for the specified day and year.
 *
 * @param className The fully qualified class name of the solution.
 * @return An instance of the solution class implementing the Solution interface.
 * @throws Exception if the class cannot be loaded or instantiated.
 */
fun loadSolution(className: String): Solution {
    return try {
        val clazz = Class.forName(className).kotlin
        val primaryConstructor = clazz.primaryConstructor
            ?: throw IllegalArgumentException("No primary constructor found for class '$className'")

        // Check if all parameters have default values
        if (primaryConstructor.parameters.all { it.isOptional }) {
            // Use callBy with an empty map to utilize default parameter values
            val instance = primaryConstructor.callBy(emptyMap())
            instance as? Solution
                ?: throw IllegalArgumentException("Class '$className' does not implement Solution interface.")
        } else {
            throw IllegalArgumentException("Primary constructor of class '$className' requires parameters without default values.")
        }
    } catch (e: Exception) {
        println("Error loading solution class '$className': ${e.message}")
        e.printStackTrace()
        throw e // Rethrow the exception to be caught in main
    }
}

/**
 * Saves the results to a file.
 */
fun saveResultsToFile(year: Int, day: Int, results: List<String>) {
    val dayPadded = day.toString().padStart(2, '0')
    val outputDir = File("outputs/year$year/day$dayPadded")
    if (!outputDir.exists()) outputDir.mkdirs()
    val outputFile = File(outputDir, "output.txt")
    outputFile.writeText(results.joinToString("\n"))
    println("Results saved to '${outputFile.path}'")
}
