# **Title:** Extreme Boggle

# Description: 

This isn’t your average Boggle. In this project, you’ll build a recursive word finder for a custom 5×5 version of the classic game—aptly named Extreme Boggle.

Unlike traditional Boggle (4×4), our version expands the challenge with a 5×5 board, meaning more letters, more possibilities, and more recursion.

You are required to implement two distinct recursive functions:

1. Board Generator – A function that creates a valid Extreme Boggle board using recursion.  
2. Word Solver – A function that recursively searches the board to find all valid words from a provided dictionary.

# Specification

1. The directory name is xYyyy*ExtremeBoggle.*   
2. Change **Yyyy** to your last name, and **x** to your first name’s Initial.  
   1. Example: wWildcatExtremeBoggle

## What You Need to Do

1. You will need to read in and store a list of acceptable words from a file.  
   1. [word-list.txt](https://drive.google.com/uc?export=download&amp;id=15yI4X5fMFEaEmSXokaPA5vCX_uMT7ZCd)  
   2. Store the words however you deem appropriate  
2. Create a function that recursively generates a Boggle Grid.  
   1. Typically a grid is 4x4, however, ours will be 5x5  
   2. Example [boggle\_grids.txt](https://drive.google.com/uc?export=download&amp;id=1fQ6Rwle3mBMC8bw9xa3N257UHH33hMS7)  
3. Find, store, and print all of the words that can be made based on the current grid and acceptable words.  
   1. Minimum word length \= 3 letters  
   2. No cell may be reused in the same word path  
   3. Adjacency rules: horizontal, vertical, or diagonal  
      1. Letters must be touching, no skipping over letters.  
   4. See these two videos for a visual explanation.  
      1. [Video 1](https://youtu.be/4TsvAfukeS8)  
      2. [Video 2](https://youtu.be/BJAdXnGAb7k)  
   5. Print the generated grid and the found words list to a file.  
      1. See the linked Sample Output File for an example of your output.

## Extra Credit:

1. All functions are recursive (50%)  
   1. Grid Creation and all helper functions  
   2. Word Search and all helper functions  
2. GUI (50%)  
   1. Create a GUI that lets the user:  
   2. Generate a new grid  
   3. Display the grid visually  
   4. Trigger the solver  
   5. View results in a scrollable output area