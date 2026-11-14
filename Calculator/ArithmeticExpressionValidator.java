import java.util.*;

/**
 * Title: arithmetic expression validator
 * Description: checks if equation is properly formatted + prompts user to reenter if invalid
 * Created: 2026-04-15
 * Last Updated: 2026-04-15
 * Author(s): Melody Wu
*/

public class ArithmeticExpressionValidator{
	// checks if equation is properly formatted
	public static void validation(String s) throws InvalidEquationException{
		// the valid symbols that the equation can possibly contain:
		char[] goodSymbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '*', '/', '%', '^', '(', ')', '\u00F7', '\u00D7', '!', 'l', '\u221A', '.'};
		char[] ops = {'+', '-', '*', '/', '^', '%', '\u00F7', '\u00D7'}; // list of operations
		// valid symbols to start an equation
		char[] goodStarts = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '(', 'l', '\u221A', '.', '+'};
		// valid symbols to end an equation
		char[] goodEnds = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ')', '!'};

		// turning the arrays into array lists
		ArrayList<Character> validSymbols = new ArrayList<>();
		for(char c: goodSymbols){
			validSymbols.add(c);
		}
		ArrayList<Character> operators = new ArrayList<>();
		for(char c: ops){
			operators.add(c);
		}
		ArrayList<Character> validStarts = new ArrayList<>();
		for(char c: goodStarts){
			validStarts.add(c);
		}
		ArrayList<Character> validEnds = new ArrayList<>();
		for(char c: goodEnds){
			validEnds.add(c);
		}

		// if string is empty, throw error
		if(s.isEmpty()){
			throw new InvalidEquationException("Equation can't be empty");
		}

		// if string doesn't start with number or (, -, log, ln, \u221a, or . throw error
		if(!validStarts.contains(s.charAt(0))){
			throw new InvalidEquationException("Equation must start with a number, (, or -, log, ln, \u221a, or .");
		}

		// if string doesn't end with number or ), throw error
		if(!validEnds.contains(s.charAt(s.length()-1))){
			throw new InvalidEquationException("Equation must end with a number, !, or )");
		}
		
		// cycle through all characters in the equation string
		for(int i=0; i<s.length(); i++){
			char curr = s.charAt(i); // character we're currently on

			if(curr == 'l'){

				// ln
				if(i + 2 < s.length() && s.startsWith("ln(", i)){
					i += 1; // skip "ln"
					continue;
				}

				// log
				if(i + 3 < s.length() && s.startsWith("log(", i)){
					i += 2; // skip "log"
					continue;
				}

				throw new InvalidEquationException("Invalid log/ln syntax");
			}

			// decimal validation
			if(curr == '.'){
				// ensure only one decimal in current number
				int left = i-1;
				while(left >= 0 && (Character.isDigit(s.charAt(left)) || s.charAt(left)=='.')){
					if(s.charAt(left)=='.'){
						throw new InvalidEquationException("Number cannot contain multiple decimals");
					}
					left--;
				}
			}

			// check if this character is a valid symbol
			if(!validSymbols.contains(curr)){ 
				// if not a valid symbol, throw an exception
				throw new InvalidEquationException("Contains an invalid symbol (valid symbols are digits and +, -, *, /, %, ^, (, ), \u221A, log, ln, !)");
			}

			// check that consecutive symbols are valid by checking the character before the current one
			if(i!=0){
				// if its ), the character before has to be a valid end
				if((curr==')') && !validEnds.contains(s.charAt(i-1))){
					throw new InvalidEquationException("Equation must have a number, !, or ) before the )");
				}
				if((curr=='!') && !validEnds.contains(s.charAt(i-1))){
					throw new InvalidEquationException("Equation must have a number or ) before the !");
				}
				// can't have 2 consecutive !s
				if(curr=='!' && s.charAt(i-1)=='!'){
					throw new InvalidEquationException("Equation must not have 2 consecutive !s");
				}
				// can't have 2 consecutive operators
				if((operators.contains(curr) && curr!='-' && curr!='+')&&operators.contains(s.charAt(i-1))){
					throw new InvalidEquationException("Equation must not have 2 consecutive operators");
				}
				if(curr=='-' && s.charAt(i-1)=='-'){
					throw new InvalidEquationException("Equation must not have 2 consecutive -'s");
				}
				if(curr=='+'&&s.charAt(i-1)=='+'){
					throw new InvalidEquationException("Equation must not have 2 consecutive +'s");
				}
				// prevent chains like +-+, --, -+, ++, etc.
				if((curr=='+' || curr=='-') &&
					(s.charAt(i-1)=='+' || s.charAt(i-1)=='-')){

					// allow only ONE unary +/- after non +/- operator
					if(i < 2 || operators.contains(s.charAt(i-2))){
						throw new InvalidEquationException("Too many consecutive +/- operators");
					}
				}
				// if the character before is (, curr has to be a valid start
				if(s.charAt(i-1)=='(' && !validStarts.contains(curr)){
					throw new InvalidEquationException("Equation must have a number or (, - after the (");
				}
				if(s.charAt(i-1)=='.' && !Character.isDigit(curr)){
					throw new InvalidEquationException("Decimal must be followed by a digit");
				}
				if((!validStarts.contains(s.charAt(i-1)) || s.charAt(i-1)=='.') && !operators.contains(s.charAt(i-1)) && curr == '.'){
					throw new InvalidEquationException("Invalid decimal placement");
				}
			}
		}

		// check for mismatched parentheses - making sure that # of ( >= # of )
		int balance = 0;
		for(int i=0; i<s.length(); i++){
			char curr = s.charAt(i);
			if(curr=='('){
				balance++;
			}
			if(curr==')'){
				balance--;
			}

			if(balance<0){
				throw new InvalidEquationException("Mismatched parentheses");
			}
		}
		if(balance!=0){ // making sure # of ( = # of )
			throw new InvalidEquationException("Mismatched parentheses");
		}
	}
}

// invalid equation exception
class InvalidEquationException extends Exception{
	public InvalidEquationException(String message){
		super(message);
	}
}