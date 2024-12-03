plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "dev.daschi"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.register("runSolution") {
    group = "application"
    description = "Run the solution for a specific day and year."

    doLast {
        val year = project.findProperty("year")?.toString()
            ?: throw GradleException("Please provide -Pyear=<year>")
        val day = project.findProperty("day")?.toString()
            ?: throw GradleException("Please provide -Pday=<day>")
        val part = project.findProperty("part")?.toString() ?: "both"
        val output = project.findProperty("output")?.toString() ?: "both"

        javaexec {
            val applicationMainClass = "dev.daschi.MainKt"
            mainClass.set(applicationMainClass)
            classpath = sourceSets["main"].runtimeClasspath
            args = listOf(year, day, part, "--output=$output")
        }
    }
}

tasks.register<Test>("testDay") {
    group = "verification"
    description = "Run tests for a specific day and year."

    val year = project.findProperty("year")?.toString()
        ?: throw GradleException("Please provide -Pyear=<year>")
    val day = project.findProperty("day")?.toString()
        ?: throw GradleException("Please provide -Pday=<day>")

    val dayPadded = day.padStart(2, '0')

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    useJUnitPlatform()

    // Include only the tests for the specified day and year
    val packagePath = "dev/daschi/year$year/day$dayPadded"
    // Include only the tests for the specified day and year
    include("**/$packagePath/**")
    // Include all test classes inside the specified package
    filter {
        includeTestsMatching("dev.daschi.year$year.day$dayPadded.*")
    }
}


tasks.register("newDay") {
    group = "setup"
    description = "Create template files for a new day."

    doLast {
        val year = project.findProperty("year")?.toString()
            ?: throw GradleException("Please provide -Pyear=<year>")
        val day = project.findProperty("day")?.toString()
            ?: throw GradleException("Please provide -Pday=<day>")

        val dayNumber = day.toInt()
        val yearNumber = year.toInt()
        val dayPadded = day.padStart(2, '0')

        val packagePrefix = "dev.daschi"
        val packagePath = "$packagePrefix.year$year.day$dayPadded"
        val directoryPrefix = "dev/daschi"
        val packageDir = "src/main/kotlin/$directoryPrefix/year$year/day$dayPadded"
        val testDir = "src/test/kotlin/$directoryPrefix/year$year/day$dayPadded"
        val inputDir = "src/main/resources/inputs/year$year"
        val testInputDir = "src/test/resources/inputs/year$year"
        val outputDir = "outputs/year$year"

        mkdir(packageDir)
        mkdir(testDir)
        mkdir(inputDir)
        mkdir(testInputDir)
        mkdir(outputDir)

        val dayKt = file("$packageDir/Day$dayPadded.kt")
        if (!dayKt.exists()) {
            dayKt.writeText(
                """
                package $packagePath

                import $packagePrefix.util.Input
                import $packagePrefix.util.Solution

                /**
                 * Solution for Day $dayNumber of Advent of Code $year.
                 */
                class Day$dayPadded(input: List<String>? = null) : Solution {
                    override val year = $yearNumber
                    override val day = $dayNumber

                    private val input = input ?: Input.readLines(year, day)

                    /**
                     * Solves Part 1.
                     */
                    override fun part1(): Any? {
                        // TODO: Implement Part 1
                        return null
                    }

                    /**
                     * Solves Part 2.
                     */
                    override fun part2(): Any? {
                        // TODO: Implement Part 2
                        return null
                    }
                }
                """.trimIndent()
            )
        }

        val dayTestKt = file("$testDir/Day${dayPadded}Test.kt")
        if (!dayTestKt.exists()) {
            dayTestKt.writeText(
                """
                package $packagePath

                import dev.daschi.util.Input
                import org.junit.jupiter.params.ParameterizedTest
                import org.junit.jupiter.params.provider.MethodSource
                import kotlin.test.assertEquals

                /**
                 * Tests for Day $dayNumber of Advent of Code $year.
                 */
                class Day${dayPadded}Test {

                    companion object {
                        @JvmStatic
                        fun part1Samples() = listOf(
                            // arrayOf(sampleNumber, expectedResult)
                        )

                        @JvmStatic
                        fun part2Samples() = listOf(
                            // arrayOf(sampleNumber, expectedResult)
                        )
                    }

                    @ParameterizedTest(name = "Part 1 Sample {0}")
                    @MethodSource("part1Samples")
                    fun testPart1Samples(sampleNumber: Int, expected: Any?) {
                        val sampleInput = Input.readLines($yearNumber, $dayNumber, sampleNumber)
                        val day = Day$dayPadded(sampleInput)
                        assertEquals(expected, day.part1())
                    }

                    @ParameterizedTest(name = "Part 2 Sample {0}")
                    @MethodSource("part2Samples")
                    fun testPart2Samples(sampleNumber: Int, expected: Any?) {
                        val sampleInput = Input.readLines($yearNumber, $dayNumber, sampleNumber)
                        val day = Day$dayPadded(sampleInput)
                        assertEquals(expected, day.part2())
                    }
                }
                """.trimIndent()
            )
        }

        val inputFile = file("$inputDir/day$dayPadded.txt")
        if (!inputFile.exists()) {
            inputFile.writeText("// Input for Day $dayNumber of Year $yearNumber")
        }

        val sampleInputFile = file("$testInputDir/day${dayPadded}_sample1.txt")
        if (!sampleInputFile.exists()) {
            sampleInputFile.writeText("// Sample Input 1 for Day $dayNumber of Year $yearNumber")
        }

        println("Template files created for Day $dayNumber of Year $yearNumber.")
    }
}
