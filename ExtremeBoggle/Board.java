import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;

/**
 * Title: Board
 * Description: class representing an nxn boggle board
 * Created: 2025-10-27
 * Last Updated: 2025-10-27
 * Author(s): Melody Wu
*/
public class Board{
	private int side; // the side length of the boggle board
	private String[][] board;

	private boolean[][] visited; // 2d array copy of the board, true if the current word has visited the cell, false o/w

	private ArrayList<String> goodWords = new ArrayList<>(); // array of all the possible words in the board
	private static ArrayList<ArrayList<String>> words; // list of the valid words

	public Board(int side, ArrayList<ArrayList<String>> words){
		this.side = side;
		this.words = words;

		board = new String[side][side]; // makes a side x side boggle board
		makeBoard(0,0);

		visited = new boolean[side][side];

		// initialize visited to all false
		for(int i=0; i<side; i++){
			for(int j=0; j<side; j++){
				visited[i][j] = false;
			}
		}

		solve(0,0);
	}
	// recursively generates a boggle board, takes in the x and y coordinates of the letter we are generating
	private void makeBoard(int x, int y){
		// base case
		if(y>=side){ // if y is bigger than side-1, then we end the process
			return;
		}
		if(x>=side){ // if x is bigger than side-1, then we move to the next row
			makeBoard(0, y+1);
			return;
		}
		int l = (int)(Math.random()*26); // generates a random integer between 1 and 26 inclusive
		String letter = String.valueOf((char) ('a'+l)); // converts this to the corresponding letter, 1=a, 2=b, ...

		board[x][y] = letter;

		makeBoard(x+1,y); // recursive step - generates the letter for (x+1, y)
	}
	/**recursively prints the boggle board to whatever file we're printing to
	 * takes in (x,y) coordinate of letter we're currently printing, and the printwriter we're using */
	public void printBoard(int x, int y, PrintWriter out){
		// base case
		if(y>=side){ // if y is bigger than side-1, then we end the process
			return;
		}
		if(x>=side){ // if x is bigger than side-1, then we move to the next row
			out.println();
			printBoard(0, y+1, out);
			return;
		}

		out.print(board[x][y]);

		printBoard(x+1,y, out); // recursive step
	}

	/**recursively draws the boggle board to GUI
	 * takes in (x,y) coordinate of letter we're printing, and the graphics we're using */
	public void drawBoard(int x, int y, Graphics g){
		// base case
		if(y>=side){ // if y is bigger than side-1, then we end the process
			return;
		}
		if(x>=side){ // if x is bigger than side-1, then we move to the next row
			drawBoard(0, y+1, g);
			return;
		}

		// draw the letter in the right place
		g.setColor(Color.black);
		Font font = new Font("Times New Roman", Font.BOLD, 50);
        g.setFont(font);
        g.drawString(board[x][y], (125+100*x+35), (100+100*y+65));

		drawBoard(x+1,y, g); // recursive step
	}

	/**recursively finds all the possible words in this boggle board and adds them to an arraylist
	 * takes in the coordinate of the letter that we're starting on
	 * this function is the start of the process */
	public void solve(int x, int y){
		// base case - if y is out of bounds, then we're done + sort the list alphabetically
		if(y>=side){ 
			Collections.sort(goodWords);
			return;
		}
		if(x>=side){ // if x is out of bounds, move to the next line
			solve(0, y+1);
			return;
		}

		path(x, y, ""); // now, we generate the path starting at (x,y)

		solve(x+1, y); // recursive step
	}

	/**helper function for solve()
	 * recursively finds all the possible words in the specific path, starting at whatever we inputed in the above function
	 * takes in the coordinate of the letter we're on and the string we're adding to, adds the valid words to an arraylist*/
	private void path(int x, int y, String s){
		// a base case - if this coordinate is out of bounds, or already visited, then we stop this path
		if(x>=side||x<0||y>=side||y<0||visited[x][y]){
			return;
		}

		s+=board[x][y]; // appends new letter to current string
		int startLetter = s.charAt(0)-'a'; // turn the start letter of the string to a number, 0=a, 1=b, etc.

		// another base case - if this string can't start a valid word, then we end this path
		if(!goodStart(s, 0, words.get(startLetter).size()-1)) return;

		// if this string is a valid word of length >=3 and we haven't already seen it, add it to the arraylist
		if(s.length()>=3 && isWord(s, 0, words.get(startLetter).size()-1) && !goodWords.contains(s)){
			goodWords.add(s);
		}

		visited[x][y] = true; // we visited this coordinate

		// traverse to all other possible paths - recursive step
		for(int dx=-1; dx<=1; dx++){
			for(int dy=-1; dy<=1; dy++){
				if(dx!=0||dy!=0){
					path(x+dx,y+dy,s);
				}
			}
		}

		visited[x][y] = false; // backtrack to case where we haven't visited this coordinate
	}
	// helper function for solve() - uses a recursive binary search to check if the string s could be a start of a valid word
	public static boolean goodStart(String s, int low, int high){
		int startLetter = s.charAt(0) - 'a'; // turn the start letter of the string to a number, 0=a, 1=b, etc.

		if (words.get(startLetter).isEmpty()) return false; // empty string = false

		// base case - if lower bound > upper bound --> can't be a valid start
		if (low > high) {
        	return false;
    	}

    	int mid = (low + high) / 2;
    	String midWord = words.get(startLetter).get(mid);

    	// check if the middle starts with the string
    	if (midWord.startsWith(s)) {
    	    return true;
    	}

    	int cmp = midWord.compareTo(s); // -1 if midWord comes before s alphabetically, 1 otherwise

    	// recursive step
    	if (cmp < 0) {
        	// midWord < s, so search right half
        	return goodStart(s, mid + 1, high);
    	} else {
        	// midWord > s, so search left half
        	return goodStart(s, low, mid - 1);
    	}
	}
	// helper function for solve() - uses a recursive binary search to check if the string s is a valid word
	public static boolean isWord(String s, int low, int high){
		int startLetter = s.charAt(0) - 'a'; // turn the start letter of the string to a number, 0=a, 1=b, etc.

		if (words.get(startLetter).isEmpty()) return false;

		// base case - if lower bound > upper bound --> can't be a valid start
		if (low > high) {
        	return false;
    	}

    	int mid = (low + high) / 2;
    	String midWord = words.get(startLetter).get(mid);

    	// check if the middle is the string
    	if (midWord.equals(s)) {
    	    return true;
    	}

    	int cmp = midWord.compareTo(s); // -1 if midWord comes before s alphabetically, 1 otherwise

    	// recursive step
    	if (cmp < 0) {
        	// midWord < s, so search right half
        	return isWord(s, mid + 1, high);
    	} else {
        	// midWord > s, so search left half
        	return isWord(s, low, mid - 1);
    	}
	}

	public ArrayList<String> getAllWords(){
		return goodWords;
	}
}