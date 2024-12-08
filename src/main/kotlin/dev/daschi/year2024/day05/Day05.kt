package dev.daschi.year2024.day05

import dev.daschi.util.Input
import dev.daschi.util.Solution

/**
 * Solution for Day 5 of Advent of Code 2024.
 */
class Day05(
    input: List<String> = Input.readLines(2024, 5)
) : Solution {
    override val year = 2024
    override val day = 5

    private val pageOrderingRules = parsePageOrderingRules(input)
    private val pageUpdates = parsePageUpdates(input)

    private fun parsePageOrderingRules(input: List<String>): List<PageOrderingRule> {
        return input.takeWhile { it.isNotEmpty() } // Process lines until an empty line is encountered
            .map { line ->
                val split = line.split("|")
                val dependency = split[0].toInt()
                val dependent = split[1].toInt()
                PageOrderingRule(dependency, dependent)
            }
    }

    private fun parsePageUpdates(input: List<String>): List<List<Int>> {
        return input.dropWhile { it.isNotEmpty() } // Skip lines until the first empty line
            .drop(1) // Drop the empty line itself
            .map { line ->
                line.split(",").map { it.toInt() }
            }
    }

    private data class PageOrderingRule(val dependency: Int, val dependent: Int)

    /**
     * Solves Part 1.
     */
    override fun part1(): Any {
        return pageUpdates.filter(::isPageUpdateValid).map(::getPageUpdateMiddleNumber).sum()
    }

    private fun getPageUpdateMiddleNumber(pageUpdate: List<Int>): Int {
        require(pageUpdate.size % 2 != 0) { "Size of pageUpdate $pageUpdate must be odd." }
        return pageUpdate[pageUpdate.size / 2]
    }

    private fun isPageUpdateValid(pageUpdate: List<Int>): Boolean {
        return pageUpdate.all { isPageInValidPosition(pageUpdate, it) }
    }

    private fun isPageInValidPosition(pageUpdate: List<Int>, page: Int): Boolean {
        val pageIndex = pageUpdate.indexOf(page)
        require(pageIndex != -1) { "Page number '$page' is not present in pageUpdate $pageUpdate." }

        val dependencies = getDependencies(page)
        return dependencies.all { dependency ->
            val dependencyIndex = pageUpdate.indexOf(dependency)
            dependencyIndex == -1 || dependencyIndex < pageIndex
        }
    }

    private fun getDependencies(page: Int): List<Int> {
        return pageOrderingRules.filter { it.dependent == page }.map { it.dependency }
    }

    /**
     * Solves Part 2.
     */
    override fun part2(): Any {
        return pageUpdates.filterNot(::isPageUpdateValid).map(::reorderPageUpdate)
            .map(::getPageUpdateMiddleNumber).sum()
    }

    private fun reorderPageUpdate(pageUpdate: List<Int>): List<Int> {
        // Build the dependants map: page -> list of pages that depend on it
        val relevantDependants = pageUpdate.associateWith { page ->
            getDependents(page).filter { it in pageUpdate }
        }

        // Calculate in-degrees: count incoming edges for each page
        val inDegrees =
            pageUpdate.associateWith { 0 }.toMutableMap() // Initialize all in-degrees to 0
        relevantDependants.values.flatten().forEach { dependant ->
            inDegrees[dependant] =
                inDegrees.getValue(dependant) + 1 // Increment in-degree for each dependant
        }

        // Perform topological sort
        return topologicalSort(relevantDependants, inDegrees)
    }

    /**
     * Performs topological sort on the given update using dependants and in-degrees.
     */
    private fun topologicalSort(
        relevantDependants: Map<Int, List<Int>>, inDegrees: MutableMap<Int, Int>
    ): List<Int> {
        val sortedOrder = mutableListOf<Int>()
        val queue = ArrayDeque<Int>()

        // Add all nodes with in-degree = 0 to the queue
        inDegrees.filter { it.value == 0 }.keys.forEach { queue.add(it) }

        // Process nodes in the queue
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst() // Get the next node to process
            sortedOrder.add(current) // Add it to the sorted order

            // Reduce in-degree for all dependants and add them to the queue if their in-degree becomes 0
            for (dependant in relevantDependants[current] ?: emptyList()) {
                val newInDegree = inDegrees.getValue(dependant) - 1
                inDegrees[dependant] = newInDegree
                if (newInDegree == 0) {
                    queue.add(dependant)
                }
            }
        }

        return sortedOrder
    }

    private fun getDependents(page: Int): List<Int> {
        return pageOrderingRules.filter { it.dependency == page }.map { it.dependent }
    }
}
