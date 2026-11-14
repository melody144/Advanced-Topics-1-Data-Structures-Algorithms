# Title: Grade Tracker

# Description:

Write a program that acts as a personal grade tracker for a student. The system should allow the student to create and manage multiple courses, each with its own grading schema (assessment categories and weights). The tracker should calculate weighted averages for each course, assign letter grades, and compute the student’s overall GPA based on the courses they have entered. The application must be robust, supporting record editing, deletion, and report generation. Data must be saved so that the tracker restores to its last state when reopened.

# Specifications:

1. Save your project in a directory named **xYyyGradeTracker**, where **x** is your first initial, and **Yyy** is your last name, for example, **wWildcatGradeTracker**. Submit your project directory via your Shared Google Drive.  
2. Name the main file that runs your code, Main.java.

# What You Need to Do:

1. Manage Courses  
   1. Students must be able to add, edit, and delete courses.  
   2. Each course must include:  
      1. Course name  
      2. Course Type (Regular, Advanced, or Honors)  
      3. Assessment categories with weights (e.g., Tests 40%, Homework 30%, Projects 30%)  
      4. List of assessments  
2. Assessment Management  
   1. Students can add, edit, and delete assessments within a course  
   2. Each assessment record should include  
      1. Name of the assessment  
      2. Category (must match one of the course’s categories)  
      3. Score earned and total possible points  
      4. Due date  
3. Searching & Sorting  
   1. The program must sort assessments by due date.  
   2. The program must search for assessments by name.  
4. Calculations  
   1. Course averages must be calculated based on weighted categories  
   2. Each course average should map to a letter grade using the provided chart.  
   3. GPA must be calculated across all courses.  
   4. Both course averages and GPA calculations must use recursion.  
5. Inheritance & Modularity  
   1. The system should be designed modularly. Part of your task is to identify appropriate places to separate functionality into multiple classes.  
   2. Your design must include an approach that distinguishes between different course types (Regular, Advanced, Honors), as they contribute differently to GPA. Think carefully about how shared behavior can be organized and reused effectively.  
6. Error Handling  
   1. Ensure that the system handles edge cases such as invalid scores, missing weights, or duplicate assessments.  
   2. Use clear messages to inform users of errors.  
7. Reporting  
   1. Each course report must contain: course name, category weights, assessments (with due dates, scores, and categories), course average, and final letter grade.  
   2. A student summary report must include all courses, final grades, and overall GPA.  
   3. Save reports to a file, using a custom extension (e.g., .grbk, .cas).  
   4. The file should be formatted in a way that is easy for a person to read and interpret.  
8. Persistence  
   1. Your program must be able to restore to its last state upon reopening.  
   2. Use a separate file with a custom extension, different from the reports.  
   3. The file should be formatted to make it easy for your program to read and interpret.  
9. GPA Calculation Scale

| Letter Grade | Regular (Unweighted) | Advanced (+0.3, cap 4.3) | Honors (+0.5, cap 4.5) |
| :---- | :---- | :---- | :---- |
| A | 4.0 | 4.3 | 4.5 |
| A- | 3.7 | 4.0 | 4.2 |
| B+ | 3.3 | 3.6 | 3.8 |
| B | 3.0 | 3.3 | 3.5 |
| B- | 2.7 | 3.0 | 3.2 |
| C+ | 2.3 | 2.6 | 2.8 |
| C | 2.0 | 2.3 | 2.5 |
| C- | 1.7 | 2.0 | 2.2 |
| D+ | 1.3 | 1.6 | 1.8 |
| D | 1.0 | 1.3 | 1.5 |
| D- | 0.7 | 1.0 | 1.2 |
| F | 0.0 | 0.0 | 0.0 |

10. Letter Grade Chart

| Letter Grade | Starting Numerical Grade |
| :---- | :---- |
| F | 0 |
| D- | 60 |
| D | 63 |
| D+ | 67 |
| C- | 70 |
| C | 73 |
| C+ | 77 |
| B- | 80 |
| B | 83 |
| B+ | 87 |
| A- | 90 |
| A | 93 |
| A+ | 97 |

## Extra Credit:

1. Create a graphical user interface (GUI) for your Grade Tracker.