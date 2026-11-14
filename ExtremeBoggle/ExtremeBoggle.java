import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;

/**
 * Title: Extreme Boggle
 * Description: Recursive word finder for a custom 5×5 version of the classic game—aptly named Extreme Boggle.
 * Created: 2025-10-27
 * Last Updated: 2025-10-27
 * Author(s): Melody Wu
*/

public class ExtremeBoggle{
	private static ArrayList<ArrayList<String>> goodWords;
	private Board board;
	private int numBoard; // how many boards we've generated

	private FileReader fReader;
	private Scanner inData;
	private FileWriter fWriter;
	private PrintWriter out;

	public ExtremeBoggle(){
		fReader=null;
		inData=null;
		fWriter = null;
		out = null;

		// file reader - read the word list of valid words
		try{
			fReader = new FileReader("word-list.txt");
			inData = new Scanner(fReader);
		}
		catch(IOException e){
			System.out.println("Error opening input file: " + e);
			System.exit(1);
		}

		goodWords = new ArrayList<>(); // array of arraylist of all the good words, sorted + separated alphabetically
		for(int i=0; i<26; i++){ // initializing all the individual arraylists
			goodWords.add(new ArrayList<>());
		}

		int count=1; // counts the line number
		while(inData.hasNext()){ // checks to see if there is another line
			String line = inData.nextLine(); // want to read entire line to avoid errors
			int letterStart = line.charAt(0) - 'a'; // gets the letter that the word starts with as a number, 1=a, 2=b, etc.
			goodWords.get(letterStart).add(line); // add word to array
			count++;
		}

		// file closer - closing file reader
		try{
			fReader.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}

		board = new Board(5, goodWords); // starts by generating a board
		numBoard = 1;

		outputFile(false); // overwrite the output file
	}

	// method that generates a new board
	public void makeNewBoard(){
		board = new Board(5, goodWords);
		numBoard++;
		outputFile(true); // writes to the output file but don't overwrite what's already there
	}

	public Board getBoard(){
		return board;
	}

	// writes to the output file, input true if you don't want to overwrite what's already there, false otherwise
	public void outputFile(boolean append){
		//file writer
		try{
			fWriter= new FileWriter("output.txt", append);
			out = new PrintWriter(fWriter);
		}
		catch(IOException e){
			System.out.println("Error opening output file: " + e);
			System.exit(1);
		}

		out.println("BOGGLE BOARD " + numBoard +":");
		board.printBoard(0,0, out); // print boggle board

		out.println("");

		ArrayList<String> allWords = board.getAllWords();
		out.println("TOTAL WORDS FOUND: " + allWords.size());
		// print all the valid words + total #
		for(String w: allWords){
			out.println(w);
		}
		out.println("");

		// file closer - close file writer
		try{
			fWriter.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}
	}
}