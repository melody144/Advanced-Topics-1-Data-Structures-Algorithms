import java.util.*;

/**
 * Title: Queue (problem set 5.1)
 * Description: implementing a stack using a linked list
 * Created: 2025-01-14
 * Last Updated: 2025-01-14
 * Author(s): Melody Wu
*/
public class ListStack<E>{
	private UnorderedList<E> stack;

	public ListStack(){
		this.stack = new UnorderedList<>();
	}
	// Adds a new item to the top of the stack. It needs the item and returns nothing
	public void push(E item){
		stack.add(item);
	}
	// Removes the top item from the stack
	public E pop(){
		return stack.pop();
	}
	// Returns the top item from the stack but does not remove it
	public E peek(){
		return stack.front();
	}
	// Tests to see whether the stack is empty
	public boolean isEmpty(){
		return stack.isEmpty();
	}
	//  Returns the number of items on the stack
	public int size(){
		return stack.size();
	}
	// Returns a string of the stack and it's contents, formatted as a list
	public String toString(){
		return stack.toString();
	}
}