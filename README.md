# Advanced Topics 1: Data Structures & Algorithms

A collection of **six Java projects** developed by **Melody Wu** for an advanced data structures and algorithms course. The programs explore recursive search, linked lists, stacks and queues, maps, balanced trees, object-oriented design, graphical interfaces, and randomized algorithms.

Rather than relying only on Java's built-in collections, several projects implement their own data structures to practice how those structures work internally.

## Projects at a glance

| Folder | Project | Main concepts | Run command (from its folder) |
| --- | --- | --- | --- |
| [`Calculator/`](Calculator/) | GUI calculator | Stacks, expression parsing and validation, Java Swing | `java Main` |
| [`Concordance/`](Concordance/) | Text concordance | Maps, doubly linked lists, AVL trees, text parsing | `java Main` * |
| [`ExtremeBoggle/`](ExtremeBoggle/) | 5 × 5 Boggle solver | Recursion, backtracking, word lookup, Swing | `java BoggleGUI` |
| [`GoFish/`](GoFish/) | Go Fish vs. computer players | Circular linked lists, object-oriented game logic | `java GoFish` |
| [`GradeTracker/`](GradeTracker/) | Student gradebook | Inheritance, weighted averages, recursion, file persistence | `java GradeTracker` * |
| [`MonteCarlo/`](MonteCarlo/) | Estimating π | Random sampling, simulation | `java MonteCarlo` |

\* `Concordance` and `GradeTracker` have small compilation issues in the uploaded version; see [Known compilation issues](#known-compilation-issues) below before running them.

## Requirements

- **Java Development Kit (JDK)** with `java` and `javac` on your path. The projects were checked using **OpenJDK 21**.
- A terminal (Command Prompt, PowerShell, Terminal, or a Java IDE).
- A desktop environment for the **Calculator** and **ExtremeBoggle** Swing interfaces.
- **No third-party Java libraries** are required.

Check your installation:

```bash
java -version
javac -version
```

## Download and run

Clone this repository:

```bash
git clone https://github.com/melody144/Advanced-Topics-1-Data-Structures-Algorithms.git
cd Advanced-Topics-1-Data-Structures-Algorithms
```

Alternatively, choose **Code → Download ZIP** on GitHub, extract the ZIP, and open the extracted folder in a terminal.

Each project is a **separate Java program**, with classes in the default package. Compile **from inside the individual project folder**, rather than compiling every folder together: different projects contain classes with the same names (such as `Main`, `UnorderedList`, and `MyList`).

For example, to run the calculator:

```bash
cd Calculator
javac *.java
java Main
```

For another project, return to the repository root, enter its folder, compile with `javac *.java`, and run the class identified in the table above. The sections below provide exact commands and any required files.

## Project details and file guide

### 1. Calculator — graphical scientific calculator

A desktop calculator built with **Java Swing**. It has basic arithmetic, scientific operations, decimal-to-binary/hexadecimal conversion, and an in-session calculation history.

**Key files**

| File | Purpose |
| --- | --- |
| `Main.java` | Swing windows, calculator modes, button actions, and history export |
| `ArithmeticExpressionValidator.java` | Checks entered expressions before evaluation |
| `ArithmeticExpressionSolver.java` | Evaluates arithmetic expressions |
| `ListStack.java` | Stack implementation built from a linked list |
| `UnorderedList.java` | Linked-list implementation used by the stack |
| `MyStack.java`, `MyList.java` | Data-structure interfaces |
| `README.md` | Original assignment specification |

**Run:**

```bash
cd Calculator
javac *.java
java Main
```

Use the **View** menu to switch between basic, scientific, and conversion calculators. The **History** menu can save calculations to `history.txt` in the current working directory. Requires a graphical desktop.

### 2. Concordance — word-frequency and position index

Reads *Alice in Wonderland* from `Alice.txt` and builds an index of words, their occurrence counts, and the positions where they appear. Word matching is case-insensitive and handles internal apostrophes and hyphens. The code uses a custom map and constructs an AVL tree of word-frequency entries; the displayed concordance is produced by iterating the map.

**Key files**

| File | Purpose |
| --- | --- |
| `Main.java` | Reads and tokenizes the text, records word locations, and prints the concordance |
| `Alice.txt` | Input text; must stay in the working directory |
| `MyListMap.java`, `MyMap.java` | Custom map and its interface |
| `KeyValuePair.java` | Generic key/value records |
| `AVLTree.java` | Self-balancing binary search tree |
| `DoublyList.java`, `UnorderedList.java` | Linked-list implementations |
| `ListStack.java`, `ListQueue.java` | Linked-list-backed stack and queue |
| `MyList.java`, `MyStack.java`, `MyQueue.java` | Data-structure interfaces |
| `README.md` | Original assignment specification |

**Run (after applying the one-line generic type fix below):**

```bash
cd Concordance
javac *.java
java Main
```

The result is printed to the terminal and may be very long. On macOS/Linux, redirect it to a file with `java Main > concordance-output.txt`. On Windows PowerShell, the same redirection can be used. Run from `Concordance/` so `Alice.txt` can be found.

### 3. ExtremeBoggle — recursive 5 × 5 word search

Generates a randomized 5 × 5 board and searches for dictionary words by following adjacent cells, including diagonals, without reusing a cell within a word path. It uses recursive board generation and word search and provides a **Swing GUI**.

**Key files**

| File | Purpose |
| --- | --- |
| `BoggleGUI.java` | Main application entry point and Swing window |
| `ColorPanel.java` | Draws the game board and controls/results interface |
| `ExtremeBoggle.java` | Loads the dictionary, creates boards, and saves results |
| `Board.java` | Board representation and recursive search logic |
| `word-list.txt` | Included dictionary; required at runtime |
| `README.md` | Original assignment specification |

**Run:**

```bash
cd ExtremeBoggle
javac *.java
java BoggleGUI
```

The program reads `word-list.txt` and writes generated boards and word lists to `output.txt` in this folder. New boards are generated randomly, so results differ between runs. Requires a graphical desktop; running on a headless server will not display the GUI.

### 4. GoFish — multiplayer card game

A terminal-based **Go Fish** game against **1–3 computer opponents**, with support for **1–3 decks**. The program practices circular linked lists and turn-based game state management.

**Key files**

| File | Purpose |
| --- | --- |
| `GoFish.java` | Main game loop, player setup, deck construction, and turns |
| `Player.java` | Player state, hands, and card-set logic |
| `AiPlayer.java` | Computer-player class |
| `CircularList.java` | Circular linked list used to rotate through players |
| `UnorderedList.java` | Linked list used for the card deck |
| `MyList.java` | List interface |
| `README.md` | Original game specification |

**Run:**

```bash
cd GoFish
javac *.java
java GoFish
```

Follow the terminal prompts to enter a name, number of decks (1–3), number of AI opponents (1–3), and actions on your turns.

### 5. GradeTracker — persistent student gradebook

A terminal-based grade tracker that stores courses and assessments, calculates weighted grades and GPAs, and demonstrates **inheritance**, **recursion**, and file persistence.

**Key files**

| File | Purpose |
| --- | --- |
| `GradeTracker.java` | Main entry point, menus, save/load, and reports |
| `Course.java` | Base course model and grade-calculation logic |
| `AdvancedCourse.java` | Course subtype with an adjusted GPA scale |
| `HonorsCourse.java` | Course subtype with an adjusted GPA scale |
| `Assessment.java` | Assessment records (name, category, score, due date) |
| `README.md` | Original assignment specification and grading scales |

**Run (after applying the two import fixes below):**

```bash
cd GradeTracker
javac *.java
java GradeTracker
```

Enter a student name to open or create a gradebook, then follow the numbered menu options. The program stores student data in `<student-name>.gdtr` and can create a summary report named `<student-name>summary.gdtr`, in the current working directory. **The entry point is `GradeTracker.java`, not `Main.java`.**

### 6. MonteCarlo — numerical estimate of π

A small simulation that generates random points in the square from −1 to 1 on each axis, counts points inside the unit circle, and uses the proportion to estimate π:

`pi ≈ 4 × (points inside circle) / (total points)`

**Files:** `MonteCarlo.java` contains the complete program.

**Run:**

```bash
cd MonteCarlo
javac MonteCarlo.java
java MonteCarlo
```

Enter the number of random points to generate. The program prints **every generated point**, the count inside the circle, and the π estimate; start with a modest number to avoid excessive terminal output. You can choose to repeat the simulation.

## Known compilation issues

The original ZIP was checked with `javac` from **OpenJDK 21**. **Calculator, ExtremeBoggle, GoFish, and MonteCarlo compiled successfully.** Two projects require these small source changes; this README does not change the Java files.

**Concordance:** In `Concordance/MyMap.java`, the interface declares an unconstrained key type, but `KeyValuePair` requires a comparable key. Change:

```java
public interface MyMap<K, E> extends Iterable<KeyValuePair<K, E>>
```

to:

```java
public interface MyMap<K extends Comparable<? super K>, E> extends Iterable<KeyValuePair<K, E>>
```

**GradeTracker:** Add the following import at the beginning of **both** `GradeTracker/AdvancedCourse.java` and `GradeTracker/HonorsCourse.java`:

```java
import java.util.ArrayList;
```

After making these changes, run `javac *.java` again in each folder. Both fixes were tested in separate working copies: the two projects then compiled, and their console entry points ran successfully. Other runtime scenarios and full game interactions have not been exhaustively tested.

## Notes

- Each project has its **own** `README.md` containing the original assignment instructions; those specifications describe intended features and should not be treated as guarantees that every feature is fully implemented.
- Programs that read or write local files depend on the **current working directory**. Run each from its own folder unless you also change the file paths in the code.
- Generated `.class` files and runtime output files are not required to read the source; they can be removed after testing.
- This repository is an educational course-project collection, not a packaged Java library or a single build-system application.

---

**Author:** Melody Wu  
**Language:** Java  
**Repository:** [melody144/Advanced-Topics-1-Data-Structures-Algorithms](https://github.com/melody144/Advanced-Topics-1-Data-Structures-Algorithms)
