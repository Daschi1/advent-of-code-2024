# Advent of Code Kotlin Framework

This is a Kotlin framework for solving [Advent of Code](https://adventofcode.com/) puzzles. It
supports:

- **Multi-Year Solutions**: Organize solutions by year.
- **Separate Parts Testing**: Test Part 1 and Part 2 independently.
- **Multiple Sample Inputs**: Support for multiple sample inputs.
- **Latest Kotlin and Java Versions**: Uses Kotlin 2.0.21 and Java JDK 21, as well as
  junit-jupiter-params.
- **Custom Gradle Tasks**: For running solutions, tests, and setting up new days.
- **Flexible Output Options**: Output to console, file, or both.

## **Project Structure**

```
advent-of-code/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
├── README.md
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   ├── yearYYYY/
│   │   │   │   ├── dayDD/
│   │   │   │   │   └── DayDD.kt
│   │   │   │   └── ...
│   │   │   └── util/
│   │   │       ├── Input.kt
│   │   │       └── Solution.kt
│   │   └── resources/
│   │       └── inputs/
│   │           ├── yearYYYY/
│   │           │   └── dayDD.txt
│   │           └── ...
│   └── test/
│       ├── kotlin/
│       │   └── yearYYYY/
│       │       └── dayDD/
│       │           └── DayDDTest.kt
│       └── resources/
│           └── inputs/
│               ├── yearYYYY/
│               │   ├── dayDD_sample1.txt
│               │   └── dayDD_sample2.txt
│               └── ...
└── outputs/
    └── yearYYYY/
        └── dayDD.txt
```

## **Getting Started**

### **Prerequisites**

- **Java JDK 21**
- **Gradle**

### **Setup**

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/yourusername/advent-of-code.git
   cd advent-of-code
   ```

2. **Configure Java Version**:

   Ensure your `JAVA_HOME` is set to JDK 21.

### **Adding a New Day**

Use the `newDay` Gradle task to set up templates for a new day:

```bash
./gradlew newDay -Pyear=2024 -Pday=1
```

This will generate:

- **Solution File**: `src/main/kotlin/dev/daschi/year2024/day01/Day01.kt`
- **Test File**: `src/test/kotlin/dev/daschi/year2024/day01/Day01Test.kt`
- **Input File**: `src/main/resources/inputs/year2024/day01.txt`
- **Sample Input File**: `src/test/resources/inputs/year2024/day01_sample1.txt`

### **Running Solutions**

Run the solution for a specific day:

```bash
./gradlew runSolution -Pyear=2024 -Pday=1 -Ppart=both -Poutput=both
```

- **Parameters**:
    - `-Pyear`: Year of the solution.
    - `-Pday`: Day number.
    - `-Ppart`: "1", "2", or "both" (default: "both").
    - `-Poutput`: "console", "file", or "both" (default: "both").

### **Running Tests**

Run tests for a specific day:

```bash
./gradlew testDay -Pyear=2024 -Pday=1
```

### **Customizing Solutions**

Implement your solution in the generated `DayDD.kt` file. Use the `input` provided or inject custom
input for testing.

### **Sample Inputs**

Place sample input files under `src/test/resources/inputs/yearYYYY/`, named as `dayDD_sampleN.txt`,
where `N` is the sample number.

### **Testing Parts Separately**

In your test files, you can test Part 1 and Part 2 separately, and provide multiple samples.

### **Output Options**

Control where the output is sent:

- `--output=console`: Output to console only.
- `--output=file`: Output to file only.
- `--output=both`: Output to both console and file.

---

## **Acknowledgments**

- [Advent of Code](https://adventofcode.com/) by Eric Wastl.
- Kotlin and Gradle documentation for guidance.
