package dev.daschi

import dev.daschi.util.Solution
import java.io.File

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
    if (args.size < 2) {
        println("Usage: <year> <day> [part] [--output=console|file|both]")
        return
    }

    val year = args[0].toIntOrNull()
    val day = args[1].toIntOrNull()
    val part = args.getOrNull(2) ?: "both"
    val outputOptionArg = args.find { it.startsWith("--output=") }
    val outputOption = outputOptionArg?.substringAfter("=") ?: "both"

    if (year == null || day == null) {
        println("Invalid year or day. Both must be integers.")
        return
    }

    val className = "year$year.day%02d.Day%02d".format(day, day)
    val solution = try {
        val clazz = Class.forName(className).kotlin
        val instance = clazz.objectInstance ?: clazz.constructors.first().call()
        instance as? Solution
            ?: throw IllegalArgumentException("Class does not implement Solution interface.")
    } catch (e: ClassNotFoundException) {
        println("Solution class '$className' not found.")
        return
    } catch (e: Exception) {
        println("Error loading solution: ${e.message}")
        return
    }

    val results = mutableListOf<String>()

    when (part) {
        "1" -> results.add("Year ${solution.year} Day ${solution.day} Part 1: ${solution.part1()}")
        "2" -> results.add("Year ${solution.year} Day ${solution.day} Part 2: ${solution.part2()}")
        "both" -> {
            results.add("Year ${solution.year} Day ${solution.day} Part 1: ${solution.part1()}")
            results.add("Year ${solution.year} Day ${solution.day} Part 2: ${solution.part2()}")
        }

        else -> {
            println("Invalid part: '$part'. Expected '1', '2', or 'both'.")
            return
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
        }
    }
}

/**
 * Saves the results to a file.
 */
fun saveResultsToFile(year: Int, day: Int, results: List<String>) {
    val outputDir = File("outputs/year$year")
    if (!outputDir.exists()) outputDir.mkdirs()
    val outputFile = File(outputDir, "day%02d.txt".format(day))
    outputFile.writeText(results.joinToString("\n"))
    println("Results saved to '${outputFile.path}'")
}
