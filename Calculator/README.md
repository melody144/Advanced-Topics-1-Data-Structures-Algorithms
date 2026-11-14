## **GUI Calculator**

**Description:** Create a **GUI-based calculator application** using Java Swing that builds upon the logic from the previous **Arithmetic Expression Validator & Solver** assignment. Your calculator should be capable of evaluating mathematical expressions entered by the user while appropriately validating input and handling errors.

Unlike the previous assignment, this version must use a **graphical user interface (GUI)** instead of console input/output. Your application should support multiple calculator modes through the use of menus and view switching.

Your calculator will have **three modes/views**:

* **Basic Calculator View**  
* **Scientific Calculator View**  
* **Conversion Calculator View**

Your application should maintain a **calculation history** that tracks previous calculations performed during the session.

**Basic Calculator Requirements:**

* Evaluate arithmetic expressions using proper order of operations  
* Support the following operators:

> \+, \-, \*, /, %, ^, (, )

*Note: For this assignment, the ^ symbol represents exponentiation (power), NOT Java’s bitwise XOR operator.*

* Display the result of the expression  
* Validate expressions before solving  
* Handle invalid input gracefully without crashing the application  
* Display clear error messages to the user when invalid expressions are entered  
  * You may want to use pop-up dialogue alert boxes for error messages,

**Scientific Calculator Requirements:**

The Scientific Calculator must include **all functionality of the Basic Calculator** in addition to:

* Square Root  
* Square (x²)  
* Cube (x³)  
* Logarithm (log)  
* Natural Logarithm (ln)  
* Factorial (\!)

You should appropriately validate scientific operations (e.g., square root of negative numbers, factorials of negative values, invalid logarithms, etc.) and provide meaningful error messages.

**Conversion Calculator Requirements:**

* Decimal to Binary conversion  
* Decimal to Hexadecimal conversion  
* Validate inputs appropriately  
* Display conversion results clearly within the GUI

**Menu Requirements:**

Your GUI must include a functional menu system with the following options:

* **Clear Calculation History**  
* **Print/Save Calculation History to File**  
* **Switch to Basic Calculator View**  
* **Switch to Scientific Calculator View**  
* **Switch to Conversion Calculator View**

**GUI Requirements:**

* Your program must use **Java Swing**  
* Use appropriate GUI components such as:  
  * JFrame  
  * JPanel  
  * JButton  
  * JTextField  
  * JTextArea  
  * JLabel  
  * JMenuBar, JMenu, and JMenuItem  
* Your interface should be organized, readable, and user-friendly  
* Appropriate event listeners and handlers must be implemented  
* The application should not crash from invalid user input

**Important:** You may NOT use built-in calculator applications, external math engines, or third-party expression evaluation libraries. Your calculator logic should be implemented by **you**.

**Submission:** Save your project in a directory named **xYyyProblemSet8\_1**, where x is your first initial and Yyy is your last name (e.g., **wWildcatProblemSet8\_1**). Name your main file **Main.java**.