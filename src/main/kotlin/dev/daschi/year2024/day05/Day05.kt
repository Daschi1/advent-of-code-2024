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
        //.slice(4..4)
        return pageUpdates.filterNot(::isPageUpdateValid).map(::reorderPageUpdate)
            .map(::getPageUpdateMiddleNumber).sum()
    }

    private fun reorderPageUpdate(pageUpdate: List<Int>): List<Int> {
        println("pageUpdate: $pageUpdate")
        val relevantDependencies = mutableMapOf<Int, List<Int>>()
        for (page in pageUpdate) {
            val filteredDependencies = getDependencies(page).filter { it in pageUpdate }
            relevantDependencies[page] = filteredDependencies
        }
        println("relevantDependencies: $relevantDependencies")

        val relevantDependants = mutableMapOf<Int, List<Int>>()
        for (page in pageUpdate) {
            val filteredDependants = getDependents(page).filter { it in pageUpdate }
            relevantDependants[page] = filteredDependants
        }
        println("relevantDependants: $relevantDependants")

        val inDegrees = mutableMapOf<Int, Int>()
        relevantDependants.keys.forEach { inDegrees[it] = 0 }
        relevantDependants.values.flatten().forEach { inDegrees[it] = inDegrees[it]!! + 1 }
        println("inDegrees: $inDegrees")

        val sortedOrder = mutableListOf<Int>()
        val queue = ArrayDeque<Int>()

        // Start with all nodes with in-degree = 0
        inDegrees.filter { it.value == 0 }.keys.forEach { queue.add(it) }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            sortedOrder.add(current)

            // Decrease the in-degree of all dependants
            for (dependant in relevantDependants[current] ?: emptyList()) {
                val newInDegree = inDegrees.getValue(dependant) - 1
                inDegrees[dependant] = newInDegree
                if (newInDegree == 0) {
                    queue.add(dependant)
                }
            }
        }
        println("sortedOrder: $sortedOrder")

        return sortedOrder
    }

    private fun getDependents(page: Int): List<Int> {
        return pageOrderingRules.filter { it.dependency == page }.map { it.dependent }
    }
}
