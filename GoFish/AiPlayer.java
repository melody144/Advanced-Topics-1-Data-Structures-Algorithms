import java.util.*;

/**
 * Title: AI Player
 * Description: an AI player within the go fish game.
 * Created: 2025-12-11
 * Last Updated: 2025-12-15
 * Author(s): Melody Wu
*/

public class AiPlayer extends Player{
	public AiPlayer(int num, ArrayList<Integer> cards){
		super("Player "+num, num, cards);
	}

	public boolean isAI(){
		return true;
	}
}