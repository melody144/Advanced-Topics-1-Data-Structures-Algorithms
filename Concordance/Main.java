import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * Title: main
 * Description: implements a concordance - alphabetical list of words present in text
 * Created: 2026-04-29
 * Last Updated: 2026-04-29
 * Author(s): Melody Wu
*/

public class Main{
	public static void main(String[]args){
		FileReader fReader = null;
		Scanner inData = null;
		FileWriter fWriter = null;
		PrintWriter out = null;

		// file reader
		try{
			fReader = new FileReader("Alice.txt");
			inData = new Scanner(fReader);
		}
		catch(IOException e){
			System.out.println("Error opening input file: " +e);
			System.exit(1);
		}

		MyListMap<String, MyListMap<Integer, Integer>> concordance = new MyListMap<String, MyListMap<Integer, Integer>>();

		int count=1; // line number
		while(inData.hasNext()){ // checks to see if there is another line
			String line = inData.nextLine(); // want to read entire line to avoid errors
			Pattern pattern = Pattern.compile("[a-zA-Z]+(?:['-][a-zA-Z]+)*");
			Matcher matcher = pattern.matcher(line);

			int i=1; // # word in the line
			while(matcher.find()){
				String key = matcher.group().toLowerCase();
				// if map already contains key, set the info to add the new location
				if(concordance.contains(key)){
					MyListMap<Integer, Integer> val = concordance.get(key);
					val.put(count, i);
					concordance.set(key, val);
				} else { // otherwise, add key to map
					MyListMap<Integer, Integer> val = new MyListMap<Integer, Integer>();
					val.put(count, i);
					concordance.add(key, val);
				}
				i++;
			}
			count++;
		}

		// avl tree of number of times each word occurs
		AVLTree<KeyValuePair<String, Integer>> tree = new AVLTree<KeyValuePair<String, Integer>>();
		for (KeyValuePair<String, MyListMap<Integer, Integer>> pair: concordance) {
		    String word = pair.getFirst();
		    int freq = pair.getSecond().size(); // number of locations of this word

		    tree.insert(new KeyValuePair<>(word, freq));
		}

		// file closer - closing file reader
		try{
			fReader.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}
		
		// printing out info
		System.out.println("Concordance: word (number of times it appears): [all locations (line number:index of word)]");
		for(KeyValuePair<String, MyListMap<Integer, Integer>> pair: concordance){
			System.out.println(pair.getFirst()+" ("+pair.getSecond().size()+"): "+pair.getSecond().toString());
		}
	}
}