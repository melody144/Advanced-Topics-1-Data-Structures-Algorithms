import java.util.*;

/**
 * Title: arithmetic expression solver
 * Description: solves for the equation
 * Created: 2026-04-15
 * Last Updated: 2026-04-15
 * Author(s): Melody Wu
*/

public class ArithmeticExpressionSolver{

	// preprocesses equation for unary minuses (- and implicit multiplication
	// (- --> (0-
	// implicit multiplication --> add *
	// returns processed result
	public static String preprocess(String s){
		// leading + or -
		if(s.charAt(0) == '-'){
			s="~"+s.substring(1);
		} else if(s.charAt(0) == '+'){
			s=s.substring(1);
		}

		for(int i=1; i<s.length(); i++){
			char curr = s.charAt(i);
			char prev = s.charAt(i-1);
			// check implicit multiplication
			if( ( (curr=='(' || curr=='l' || curr=='\u221A') && (
					prev==')' || Character.isDigit(prev) || prev=='!' || prev == '.')) || 
				((Character.isDigit(curr) || curr == '.') && 
					(prev==')' || prev=='!'))
			){
				s=s.substring(0, i)+"*"+s.substring(i);
			}
			// check unary minus
			if(prev=='(' || prev == '+' || prev == '-' || prev == '*' || prev == '/' || prev == '%' || prev == '^' || prev == '\u00D7' || prev == '\u00F7'){
				if(curr=='-'){
					s=s.substring(0,i)+"~"+s.substring(i+1);
				} else if(curr=='+'){
					s=s.substring(0, i)+s.substring(i+1);
					i--;
				}
			}
		}
		return s;
	}

	// solves for an equation s and returns double value
	public static double solve(String s){
		s = preprocess(s);

		char[] ops = {'+', '-', '*', '/', '^', '%', '\u00F7', '\u00D7', '~'}; // operations
		int[] opVals = {0, 0, 1, 1, 2, 1, 1, 1, 3}; // value of their importance by PEMDAS

		// turn ops into an array list
		ArrayList<Character> operators = new ArrayList<>();
		for(char c: ops){
			operators.add(c);
		}

		// stack of the values and operations in the equation s
		ListStack<Double> values = new ListStack<>();
		ListStack<Character> operations = new ListStack<>();
		ListStack<String> functions = new ListStack<>();
		ListStack<Integer> functionDepths = new ListStack<>();
		int depth = 0;

		// going through the equation and finding the end result
		for(int i=0; i<s.length(); i++){
			char curr = s.charAt(i);

			if(s.startsWith("log(", i)){
				depth++;
				functions.push("log");
				functionDepths.push(depth);
				operations.push('(');
				i+=3;
				continue;
			}

			if(s.startsWith("ln(", i)){
				depth++;
				functions.push("ln");
				functionDepths.push(depth);
				operations.push('(');
				i+=2;
				continue;
			}

			if(s.startsWith("\u221A(", i)){
				depth++;
				functions.push("\u221A");
				functionDepths.push(depth);
				operations.push('(');
				i+=1;
				continue;
			}

			// if this symbol is a digit, then we go to the end of the number and then that's the value of the number in the equation
			if(Character.isDigit(curr) || curr=='.'){
				StringBuilder num = new StringBuilder();

				while(i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i)=='.')){
					num.append(s.charAt(i));
					i++;
				}

				values.push(Double.parseDouble(num.toString()));
				i--;
			} else if(curr=='('){
				depth++;
				operations.push(curr);
			} else if(curr==')'){ // when we end a parentheses, evaluate everything in it
				while(!operations.isEmpty() && operations.peek()!='('){
					applyTopOperation(values, operations);
				}
				operations.pop();

				if(!functions.isEmpty() && functionDepths.peek()==depth){ // apply function if present
					String fn = functions.pop();
					functionDepths.pop();

					double val = values.pop();

					if(fn.equals("log")){
						if(val<=0){
							throw new ArithmeticException("Log undefined for non-positive numbers");
						}
						values.push(Math.log10(val));
					} else if(fn.equals("ln")){
						if(val<=0){
							throw new ArithmeticException("Log undefined for non-positive numbers");
						}
						values.push(Math.log(val));
					} else if(fn.equals("\u221A")){
						if(val<0){
							throw new ArithmeticException("sqrt undefined for negative numbers");
						}
						values.push(Math.sqrt(val));
					}
				}

				depth--;
			} else if(curr=='!'){
				values.push(factorial(values.pop()));
			} else if(operators.contains(curr)){
				// evalute the previous operation if it follows order of operations
				while(!operations.isEmpty() && 
					operations.peek()!='(' && 
					(opVals[operators.indexOf(curr)] < opVals[operators.indexOf(operations.peek())] ||
						(opVals[operators.indexOf(curr)] == opVals[operators.indexOf(operations.peek())] && curr != '^')
					)
				){
					applyTopOperation(values, operations);
				}
				operations.push(curr);
			}
		}

		// finding the value of everything after cycling
		while(!operations.isEmpty()){
			applyTopOperation(values, operations);
		}

		return values.pop();
	}

	// finding the value of a (op) b
	public static double applyOp(double b, double a, char op){
		if(op=='+'){
			return a+b;
		} else if(op=='-'){
			return a-b;
		} else if(op=='*' || op=='\u00D7'){
			return a*b;
		} else if(op=='^'){
			// check if a^b returns a number
			if(Double.isNaN(Math.pow(a,b))){
				throw new ArithmeticException("Negative base with non-integer exponent");
			}
			return Math.pow(a,b);
		} else if(op=='/' || op=='\u00F7'){
			// checking for division + modulo by zero
			if(b==0){
				throw new ArithmeticException("Division by zero");
			}
			return (double)a/b;
		} else if(op=='%'){
			if(b==0){
				throw new ArithmeticException("Modulo by zero");
			}
			return a%b;
		}
		return 0;
	}

	private static void applyTopOperation(ListStack<Double> values, ListStack<Character> operations) {
	    char op = operations.pop();
	    if (op == '~') {
	        values.push(-values.pop()); 
	    } else {
	        values.push(applyOp(values.pop(), values.pop(), op));
	    }
	}

	// factorial method
	public static double factorial(double n){
		if(n < 0){
			throw new ArithmeticException("Factorial of negative number");
		}

		if(n != (int)n){
			throw new ArithmeticException("Factorial only defined for integers");
		}

		double result = 1;

		for(int i = 2; i <= (int)n; i++){
			result *= i;
		}

		return result;
	}
}