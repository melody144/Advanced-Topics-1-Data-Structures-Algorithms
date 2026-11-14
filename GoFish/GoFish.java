import java.util.*;

/**
 * Title: Go Fish
 * Description: Implementing the game Go Fish, a simple turn-based game between a user and up to three AIs.
 * Created: 2025-12-11
 * Last Updated: 2025-12-15
 * Author(s): Melody Wu
*/

public class GoFish{
	// if card = n,	face value = n%13 - 1 = ace, 11 = jack, 12 = queen, 0 = king
	//				suit value = (n-1)/13 - 0 = spade, 1 = heart, 2 = club, 3 = diamond
	private static UnorderedList<Integer> deck = new UnorderedList<>(); // linked list of the decks of cards
	private static CircularList<Player> players = new CircularList<>(); // circular linked list of players
	private static ArrayList<Player> orderedPlayer = new ArrayList<>();

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[]args){
		System.out.println("What is your name?");
		String name = scan.nextLine();

		// getting number of decks to use
		int numDecks = 0;
		boolean badInput = true;
		// repeating until user inputs 1, 2, or 3
		while(badInput){
			try{
				System.out.println("How many decks of cards do you want to use? (insert 1, 2, or 3)");
				numDecks = scan.nextInt();
				scan.nextLine();
				if(numDecks<1 || numDecks >3){
					System.out.println("Invalid input (not 1, 2, or 3), try again");
				} else {
					badInput = false;
				}
			}
			catch(Exception e){
				System.out.println("Invalid input (not 1, 2, or 3), try again");
				scan.nextLine();
			}
		}


		// shuffling the deck
		ArrayList<Integer> vals = new ArrayList<>(); // array list of vals 1-52 with 3 of each
		for(int i=1; i<=52; i++){
			for(int j=1; j<=numDecks; j++){
				vals.add(i);
			}
		}
		shuffle(vals);
		for(int i=0; i<vals.size(); i++){ // putting the vals into the deck in the order we shuffled
			deck.append(vals.get(i));
		}

		// getting number of CPU players
		int numAI = 0;
		badInput = true;
		// repeating until user inputs 1, 2, or 3
		while(badInput){
			try{
				System.out.println("How many AI players? (insert 1, 2, or 3)");
				numAI = scan.nextInt();
				scan.nextLine();
				if(numAI<1 || numAI >3){
					System.out.println("Invalid input (not 1, 2, or 3), try again");
				} else {
					badInput = false;
				}
			}
			catch(Exception e){
				System.out.println("Invalid input (not 1, 2, or 3), try again");
				scan.nextLine();
			}
		}

		// making the circular linked list of players + ordered array list
		int userPos = (int)(Math.random()*(numAI+1)); // randomly generating the order - where the user goes
		for(int i=0; i<numAI+1; i++){
			ArrayList<Integer> cards = new ArrayList<>();
			// generating the 7 cards of this player
			for(int j=1; j<=7; j++){
				cards.add(deck.front()); // taking the first card from the deck
				deck.pop(); // removing that card
			}

			// adding the player
			if(i==userPos){
				Player player = new Player(name, i, cards);
				players.append(player);
				orderedPlayer.add(player);
			} else {
				AiPlayer player = new AiPlayer(i, cards);
				players.append(player);
				orderedPlayer.add(player);
			}
		}

		System.out.println("");
		System.out.println("Player order: "+players.toString());
		System.out.println("Starting player: "+players.front());
		System.out.println("");

		boolean game = true; // game = false when game is over
		while(game){
			Player player = players.front();
			
			boolean goFish = turn(player);

			player.checkFour();

			// if this player go fished, we move to the next player
			if(goFish){
				System.out.println(player+" has "+player.getNumCards()+" cards and "+player.getNumSets()+" sets of four set aside");
				System.out.println("");

				players.forward();
			}

			for(int i=0; i<players.size(); i++){
				if(players.valueAt(i).noCards()){
					game=false;
					System.out.println(players.valueAt(i)+" ran out of cards");
					System.out.println("Game ended");
					System.out.println("");
					break;
				}
			}
		}

		// generating which player won
		ArrayList<Integer> winners = new ArrayList<>();
		winners.add(0);
		for(int i=1; i<orderedPlayer.size(); i++){
			int most = winners.get(0);
			Player winner = orderedPlayer.get(most);
			Player compare = orderedPlayer.get(i);
			if(compare.getNumSets()>winner.getNumSets()){ // if this player has the most # sets
				winners.clear();
				winners.add(i);
			} else if(compare.getNumSets()==winner.getNumSets()){ // if this player has the same as the max # sets so far
				winners.add(i);
			}
		}

		// printing out winner(s)
		for(int i=0; i<winners.size(); i++){
			if(i!=0){
				System.out.print(", ");
			}
			System.out.print(orderedPlayer.get(winners.get(i)));
		}
		System.out.println(" won");
		System.out.println("");

		System.out.println("Number of sets of each person:");
		for(int i=0; i<orderedPlayer.size(); i++){
			System.out.println(orderedPlayer.get(i)+": "+orderedPlayer.get(i).getNumSets()+" sets");
		}
	}

	// shuffle an arraylist
	public static void shuffle(ArrayList<Integer> list){
		for(int i=0; i<list.size(); i++){
			int rand = (int)(Math.random()*(list.size()-i)+i); // for each index i, generate a random index that's larger and then swap the two
			int temp = list.get(i);
			list.set(i, list.get(rand));
			list.set(rand, temp);
		}
	}

	/**
	 * method for a turn of a player
	 * @return if the player go fished
	 */
	public static boolean turn(Player player){
		int num = -1;
		int cardNum = -1;

		if(player.isAI()){
			// generating random number >= 0 and < the number of players in total, excluding the number of the ai
			num = (int)(Math.random()*(players.size()-1));
			if(num>=player.getNum()){
				num++;
			}

			// getting random card from hand to ask for
			cardNum = player.getRandCard();

		} else {
			System.out.println(player.getCards());

			// getting number of the player the user wants to ask
			boolean badInput = true;
			// repeating until user inputs a valid number
			while(badInput){
				try{
					System.out.println("Type in the number of the player you want to ask");
					num = scan.nextInt();
					scan.nextLine();
					if(num<0 || num>players.size() || num==player.getNum()){
						System.out.println("Invalid input (not in range or it's your number), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not in range or it's your number), try again");
					scan.nextLine();
				}
			}

			// getting number of the card the user wants
			badInput = true;
			// repeating until user inputs a valid number
			while(badInput){
				try{
					System.out.println("Type in the number of the card you want (King = 0, Ace = 1, Jack = 11, Queen = 12)");
					cardNum = scan.nextInt();
					scan.nextLine();
					if(cardNum<0 || cardNum>12){
						System.out.println("Invalid input (not an integer between 0 and 12 inclusive), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not an integer between 0 and 12 inclusive), try again");
					scan.nextLine();
				}
			}
		}

		Player victim = orderedPlayer.get(num); // player we're asking for the cards

		System.out.println(player+" asked "+victim+" for a "+ player.numToVal(cardNum));

		ArrayList<Integer> moreCards = victim.ask(cardNum); // obtaining all their cards of the face value
		player.add(moreCards);

		// if player wasn't given any cards (i.e. told to gofish)
		if(moreCards.size()==0){
			goFish(player);
			return true;
		} else {
			System.out.println(player + " got " + moreCards.size() + " " + player.numToVal(cardNum)+"(s)");
		}

		return false;
	}

	public static void goFish(Player player){
		if(deck.isEmpty()){
			System.out.println("No more cards in deck");
			return; // can't go fishing if there's no deck
		}
		System.out.println(player+" went fishing");
		int top = deck.pop();
		ArrayList<Integer> drawn = new ArrayList<>();
		drawn.add(top);
		player.add(drawn);
	}
}