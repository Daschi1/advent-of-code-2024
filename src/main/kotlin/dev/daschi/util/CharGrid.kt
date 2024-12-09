package dev.daschi.util

/**
 * Utility class for working with a 2D character grid.
 *
 * @param lines The list of strings representing rows of the grid.
 * @throws IllegalArgumentException if the input lines are not uniform in length.
 */
class CharGrid(lines: List<String>) {

    private val grid: Array<CharArray>
    val height: Int
    val width: Int

    init {
        require(lines.isNotEmpty()) { "Input lines must not be empty." }
        val rowLengths = lines.map { it.length }
        require(rowLengths.distinct().size == 1) { "All rows must have the same length." }

        height = lines.size
        width = rowLengths.first()
        grid = Array(height) { y -> lines[y].toCharArray() }
    }

    /**
     * Returns the character at the specified position.
     *
     * @param y The row index.
     * @param x The column index.
     * @return The character at the specified position.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    fun get(y: Int, x: Int): Char {
        assertInsideBounds(y, x)
        return grid[y][x]
    }

    /**
     * Checks if the specified position is within the grid bounds.
     *
     * @param y The row index.
     * @param x The column index.
     * @return `true` if the position is within bounds, `false` otherwise.
     */
    fun isInBounds(y: Int, x: Int): Boolean {
        return y in 0 until height && x in 0 until width
    }

    /**
     * Iterates over all positions in the grid, invoking the specified action.
     *
     * @param action A function that takes the row index, column index, and the character at that position.
     */
    fun forEach(action: (y: Int, x: Int, char: Char) -> Unit) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                action(y, x, grid[y][x])
            }
        }
    }

    /**
     * Returns the raw internal grid as a 2D character array.
     *
     * @return The internal 2D character array.
     */
    fun getRawArray(): Array<CharArray> = grid

    /**
     * Private helper method to check if a position is within bounds.
     *
     * @param y The row index.
     * @param x The column index.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    private fun assertInsideBounds(y: Int, x: Int) {
        if (!isInBounds(y, x)) {
            throw IndexOutOfBoundsException("Position (y: $y, x: $x) is out of bounds.")
        }
    }

    /**
     * Prints the grid to the console in a human-readable format.
     */
    fun printGrid() {
        grid.forEach { row -> println(row.joinToString("")) }
    }
}
