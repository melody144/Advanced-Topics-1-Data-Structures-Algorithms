import java.util.*;

/**
 * Title: Unordered List
 * Description: implementing an unordered list (linked list) class
 * which implements the MyList interface - allows repeated elements
 * Created: 2025-11-25
 * Last Updated: 2025-11-25
 * Author(s): Melody Wu
*/

public class UnorderedList<E> implements MyList<E>{
	private ListNode head;
	private int size;

	public UnorderedList(){
		this.head = new ListNode(null, null);
		this.size = 0;
	}

	/**
	 * If the List is empty, return true otherwise return false.
	 * @return A boolean indicating if the List is or is not empty.
	 */
	public boolean isEmpty(){
		return this.size==0;
	}

	/**
	 * @return the number of objects in the current List.
	 */
	public int size(){
		return size;
	}

	/**
	 * Searches for the item in the list.
	 * @param value the value of the element we're searching for
	 * @return boolean indicating if the list has that value or not
	 */
	public boolean contains(E value){
		ListNode curr = head.next;
		while(curr!=null){ // loop through all elements of list
			if(curr.data.equals(value)){
				return true;
			}
			curr=curr.next;
		}
		return false;
	}

	/**
	 * Insert an Object at the front in an unordered list.
	 * @param obj the Object being added to the List
	 */
	public void add(E obj){
		ListNode newNode = new ListNode(obj, head.next);
		this.head.next = newNode;
		this.size++;
	}

	/**
	 * If the List is not empty, remove and return the first occurrence of the element from the List.
	 * @param obj the Object being removed from the list
	 * @return The List object's data or null if the List is empty
	 */
	public E remove(E obj){
		if(this.isEmpty()){
			return null;
		}

		ListNode prev = head;
		ListNode curr = head.next;

		// looping through the linked list until it gets to the wanted node
		while(curr!=null){
			if(curr.data.equals(obj)){
				// removing that node
				prev.next = curr.next;
				curr.next = null;
				size--;
				return curr.data;
			}

			// moving to the next node
			prev = curr;
			curr = curr.next;
		}

		return null;
	}

	/**
	 * Making lists displayed like this: [1, 2, 3, 4, etc.]
	 * @return the String
	 */
	public String toString(){
		String s = "["; // string representation of list

		ListNode curr = head.next;
		while(curr!=null){
			if(curr.next==null){ // don't add the comma if its the last element of the list
				s+=curr.toString();
			} else{
				s+=curr.toString()+", ";
			}
			curr=curr.next;
		}
		s+="]"; // end of list
		return s;
	}

	/**
	 * Adds a new item E to the end of the list making it the last item in the collection.
	 * @param obj the Object that we're adding to the end of the list
	 */
	public void append(E obj){
		if(this.isEmpty()){
			head.next= new ListNode(obj, null);
			size++;
			return;
		}

		ListNode curr=head.next;
		// looping until curr is the last element of the list
		while(curr.next!=null){
			curr=curr.next;
		}
		// making the Object the last element of the list
		ListNode newNode = new ListNode(obj, null);
		curr.next=newNode;
		this.size++;
	}

	/**
	 * Returns the position of the item E in the list.
	 * @param value the Object we're finding the position of.
	 * @return the index, -1 if it's not in the list.
	 */
	public int indexOf(E value){
		if(this.isEmpty()){
			return -1;
		}

		int count = 0;

		ListNode curr = head.next;
		// looping through the list until it gets to value
		while(curr!=null){
			if(curr.data.equals(value)){
				return count;
			}
			curr=curr.next;
			count++;
		}

		return -1;
	}

	/**
	 * Returns the item at a specified position in the list
	 * @param pos the position that we're finding
	 * @return the value if it's in the list
	 */
	public E valueAt(int pos){
		// make sure that pos is a valid position
		if(pos>=this.size() || pos<0){
			return null;
		}

		ListNode curr = head.next;
		// looping through the list until we get to pos
		for(int i=0; i<pos; i++){
			curr = curr.next;
		}

		return curr.data;
	}

	/**
	 * Removes and returns the first item in the list
	 * @return the value of the first item in the list or null if it's not in the list
	 */
	public E pop(){
		if(isEmpty()){
			return null;
		}

		E value = valueAt(0);
		return remove(value);
	}

	/**
	 * Adds a new item E to the list at position pos.
	 * @param pos of where we're inserting the object into the list
	 * @param obj the Object we're adding to the list
	 */
	public void insert(int pos, E obj){
		if(pos<0 || pos>size){
			return;
		}

		ListNode curr = head;
		// looping until we get to the element one before pos
		for(int i=0; i<pos; i++){
			curr = curr.next;
		}
		// adding obj at pos
		ListNode newNode = new ListNode(obj, curr.next);
		curr.next=newNode;
		size++;
	}

	/**
	 * If the List is not empty, return the element that is currently at the front of the List.
	 * If the List is empty return null.  The contents of the List is not changed.
	 * @return The object's data at the front of the List or null if the List is empty
	 */
	public E front(){
		return valueAt(0);
	}

	/**
	 * Finds the positions of all occurrences of a value in the list
	 * @param val of the item we're finding in the list
	 * @return ArrayList of indices of all occurrences of val
	 */
	public ArrayList<Integer> indicesOf(E val){
		ArrayList<Integer> indices = new ArrayList<>();

		if(this.isEmpty()){
			return indices;
		}

		int count = 0;

		ListNode curr = head.next;
		// looping through the list and recording all the indices where we get val
		while(curr!=null){
			if(curr.data.equals(val)){
				indices.add(count);
			}
			curr=curr.next;
			count++;
		}

		return indices;
	}

	/**
	 * Removes all occurrences of an item E from the list
	 * @param obj that we're removing all occurrences of
	 */
	public void removeAll(E obj){
		if(this.isEmpty()){
			return;
		}

		while(this.contains(obj)){
			this.remove(obj);
		}
	}

	//private class of nodes
	private class ListNode{
		private E data;
		private ListNode next;

		public ListNode(E data, ListNode next){
			this.data = data;
			this.next = next;
		}

		public String toString(){
			return String.valueOf(data);
		}
	}
}