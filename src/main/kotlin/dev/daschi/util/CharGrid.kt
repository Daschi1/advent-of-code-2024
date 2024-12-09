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
     * Enum representing movement directions in the grid with corresponding row and column deltas.
     */
    enum class Direction(val deltaY: Int, val deltaX: Int) {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1), UP_LEFT(-1, -1), UP_RIGHT(
            -1,
            1
        ),
        DOWN_LEFT(1, -1), DOWN_RIGHT(1, 1)
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
     * Sets the character at the specified position.
     *
     * @param y The row index.
     * @param x The column index.
     * @return The character to set at the specified position.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    fun set(y: Int, x: Int, char: Char) {
        assertInsideBounds(y, x)
        grid[y][x] = char
    }

    /**
     * Moves in the given direction from the starting position (y, x) and returns the new coordinates.
     *
     * @param y The starting row index.
     * @param x The starting column index.
     * @param direction The direction to move in.
     * @return A pair of the new row and column coordinates.
     * @throws IndexOutOfBoundsException if the move goes out of bounds.
     */
    fun moveTo(y: Int, x: Int, direction: Direction): Pair<Int, Int> {
        val newY = y + direction.deltaY
        val newX = x + direction.deltaX
        assertInsideBounds(newY, newX)
        return newY to newX
    }

    /**
     * Moves in the given direction from the starting position (y, x) and returns the character at the new position.
     *
     * @param y The starting row index.
     * @param x The starting column index.
     * @param direction The direction to move in.
     * @return The character at the new position.
     * @throws IndexOutOfBoundsException if the move goes out of bounds.
     */
    fun moveAndGet(y: Int, x: Int, direction: Direction): Char {
        val (newY, newX) = moveTo(y, x, direction)
        return get(newY, newX)
    }

    /**
     * Finds all positions of the specified character in the grid.
     *
     * @param char The character to search for.
     * @return A list of pairs (y, x) where the character is found, or an empty list if not found.
     */
    fun findPositions(char: Char): List<Pair<Int, Int>> {
        val positions = mutableListOf<Pair<Int, Int>>()
        forEach { y, x, gridChar ->
            if (gridChar == char) {
                positions.add(y to x)
            }
        }
        return positions
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
     * Prints the grid to the console in a human-readable format.
     */
    fun printGrid() {
        grid.forEach { row -> println(row.joinToString("")) }
    }

    /**
     * Checks if a position is within bounds and throws an exception if it is not.
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
}
