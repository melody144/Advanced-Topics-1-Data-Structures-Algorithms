import java.util.*;

/**
 * Title: Player
 * Description: A human player within the Go Fish game
 * Created: 2025-12-11
 * Last Updated: 2025-12-15
 * Author(s): Melody Wu
*/

public class Player{
	private String name;
	private int num; // which number player they are
	private ArrayList<Integer> cards = new ArrayList<>();

	private boolean[] valsInHand = new boolean[13]; // list of the face values of the cards in this player's hand, true if in, false if not 
	private ArrayList<Integer> cardVals = new ArrayList<>(); // list of the face values of the cards in this player's hand

	private ArrayList<int[]> setsOfFour = new ArrayList<>(); // arraylist of sets of 4 cards of the same value

	public Player(String name, int num, ArrayList<Integer> cards){
		this.name = name;
		this.num = num;
		this.cards = cards;

		this.checkFour();
		this.genValsInHand();
	}

	/**
	 * returns if this player has the card val in the list
	 * @param val of card looking for
	 * @return whether val is in the hand
	 */
	public boolean contains(int val){
		return cards.contains(val);
	}

	/**
	 * returns if this player has this face val in the list
	 * @param val of the face value we're looking for
	 * @return whether val is in the hand
	 */
	public boolean containsFaceVal(int val){
		// ensuring in range
		if(val<0 || val>12){
			return false;
		}
		if(val==0){
			val=13;
		}
		return contains(val) || contains(val+13) || contains(val+26) || contains(val+39);
	}

	/**
	 * returns number of cards with a certain face value
	 * @param val of the face value we're looking for
	 * @return number of cards with that face value
	 */
	public int numVal(int val){
		if(val<0||val>12){ // ensuring in range
			return 0;
		}
		if(!this.containsFaceVal(val)){ // return 0 if not in list
			return 0;
		}

		int count = 0;
		for(int i: cards){
			if(i%13==val){
				count++;
			}
		}

		return count;
	}

	// updates the boolean array valsInHand and cardVals
	private void genValsInHand(){
		for(int i=0; i<13; i++){
			this.valsInHand[i]=containsFaceVal(i);
		}

		cardVals.clear();
		for(int i=0; i<13; i++){
			if(valsInHand[i]){
				cardVals.add(i+1);
			}
		}
	}

	/**
	 * removes all instances of a face value in the hand
	 * @param val of face value of cards we're removing
	 * @return array list of cards we removed, empty if we didn't remove anything
	 */
	public ArrayList<Integer> removeAll(int val){
		if(val==0){
			val=13;
		}
		ArrayList<Integer> removed = new ArrayList<>();
		for(int i=0; i<=3; i++){
			while(cards.contains(val+13*i)){
				removed.add(val+13*i);
				cards.remove(cards.indexOf(val+13*i));
			}
		}

		genValsInHand();

		return removed;
	}

	/**
	 * method for when this player is asked if they have a certain face value
	 * @param val they're being asked
	 * @return array list of the cards you are giving, empty if you don't have any cards of that face value
	 */
	public ArrayList<Integer> ask(int val){
		ArrayList<Integer> giving = this.removeAll(val); // array list of the cards we're giving up
		return giving;
	}

	/**
	 * adding more cards to our hand
	 * @param moreCards - arraylist of the cards we're adding to our hand
	 */
	public void add(ArrayList<Integer> moreCards){
		for(int i: moreCards){
			cards.add(i);
		}

		genValsInHand();
	}

	/**
	 * checking if there are 4 cards of the same face value in the hand
	 */
	public void checkFour(){
		for(int i=0; i<13; i++){
			while(this.numVal(i)>=4){
				this.set(i); // setting aside 4 cards with the same face value
				System.out.println(this.name+" set aside 4 "+numToVal(i)+"s");
			}
		}
	}

	/**
	 * setting aside 4 cards of the same face value
	 * @param val of the 4 cards we're setting asside
	 */
	public void set(int val){
		if(val<0||val>12){ // ensuring in range
			return;
		}
		if(numVal(val)<4){ // making sure there are at least 4 of them
			return;
		}

		int[] four = new int[4];

		// finding the four cards and removing from hand
		int index = 0;
		for(int i=0; i<cards.size(); i++){
			if(index>=4){
				break;
			}
			if(cards.get(i)%13==val){
				four[index]=cards.get(i);
				index++;
				cards.remove(i);
				i--;
			}
		}

		setsOfFour.add(four); // setting them aside
		genValsInHand();
	}

	public String toString(){
		return name;
	}

	/** @return if this player is AI */
	public boolean isAI(){
		return false;
	}
	/** @return if this player ran out of cards*/
	public boolean noCards(){
		return cards.size()==0;
	}

	// getters
	public String getName(){
		return this.name;
	}
	public int getNum(){
		return this.num;
	}
	public int getNumCards(){
		return this.cards.size();
	}
	public int getNumSets(){
		return this.setsOfFour.size();
	}
	// generate random card value in hand
	public int getRandCard(){
		int rand = (int)(Math.random()*cards.size());
		return cards.get(rand)%13;
	}
	// printing the cards in this players hand
	public String getCards(){
		String str = "Your Cards: ";
		for(int i=0; i<cards.size(); i++){
			int x = cards.get(i);
			if(i!=0){
				str+=", ";
			}
			str+=numToVal(x%13)+"/"+numToSuit((x-1)/13);
		}
		return str;
	}
	/**
	 * turns the face value number into what the card is called
	 * @param val of face value
	 * @return the name of that face value card
	 */
	public static String numToVal(int val){
		if(val>=2 && val<=10){
			return ""+val;
		} else if(val==1){
			return "Ace";
		} else if(val==11){
			return "Jack";
		} else if(val==12){
			return "Queen";
		} else if(val==0){
			return "King";
		}

		return null;
	}
	/**
	 * turns the number into the suit
	 * @param val of suit
	 * @return the name of that suit
	 */
	public static String numToSuit(int val){
		if(val==0){
			return "Spades";
		} else if(val==1){
			return "Hearts";
		} else if(val==2){
			return "Clubs";
		} else if(val==3){
			return "Diamonds";
		}

		return null;
	}
}