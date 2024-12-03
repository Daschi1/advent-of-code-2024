package dev.daschi.util

import java.io.InputStream

/**
 * Utility object for reading input files.
 */
object Input {

    /**
     * Reads input lines for the specified year and day.
     *
     * @param year The year of the Advent of Code event.
     * @param day The day number.
     * @param sampleNumber The sample number (null for actual input).
     * @return A list of input lines.
     * @throws IllegalArgumentException if the input file is not found.
     */
    fun readLines(year: Int, day: Int, sampleNumber: Int? = null): List<String> =
        getInputStream(year, day, sampleNumber).bufferedReader().readLines()

    /**
     * Reads the entire input text for the specified year and day.
     *
     * @param year The year of the Advent of Code event.
     * @param day The day number.
     * @param sampleNumber The sample number (null for actual input).
     * @return The input text.
     * @throws IllegalArgumentException if the input file is not found.
     */
    fun readText(year: Int, day: Int, sampleNumber: Int? = null): String =
        getInputStream(year, day, sampleNumber).bufferedReader().readText()

    private fun getInputStream(year: Int, day: Int, sampleNumber: Int?): InputStream {
        val fileName = fileName(year, day, sampleNumber)
        val resourcePath = if (sampleNumber == null) {
            "/inputs/$fileName"
        } else {
            "/inputs/$fileName"
        }
        return Input::class.java.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException(
                "Input file '$fileName' not found. Expected at 'src/main/resources$resourcePath' or 'src/test/resources$resourcePath'."
            )
    }

    private fun fileName(year: Int, day: Int, sampleNumber: Int?): String {
        val dayPadded = day.toString().padStart(2, '0')
        return if (sampleNumber == null) {
            "year$year/day$dayPadded.txt"
        } else {
            "year$year/day${dayPadded}_sample$sampleNumber.txt"
        }
    }
}
