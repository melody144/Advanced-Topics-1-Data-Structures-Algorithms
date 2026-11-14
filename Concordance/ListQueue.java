import java.util.*;

/**
 * Title: ListQueue (problem set 5.2)
 * Description: implementing a queue using alinked list
 * Created: 2025-12-05
 * Last Updated: 2025-12-05
 * Author(s): Melody Wu
*/

public class ListQueue<E> implements MyQueue<E>{
	private DoublyList<E> queue;
	public ListQueue(){
		this.queue = new DoublyList<>();
	}
	// returns the front element, null if empty queue
	public E front(){
		return queue.front();
	}
	// tests whether the queue is empty
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	// returns the number of items in the queue
	public int size(){
		return queue.size();
	}
	// adds a new item to the rear of the queue
	public void enqueue(E item){
		queue.append(item);
	}
	/**
	 * removes the front item from the queue
	 * @return item that was removed, null if empty queue
	 */
	public E dequeue(){
		return queue.pop();
	}
	// returns what is in the queue
	public String toString(){
		return queue.toString();
	}
}