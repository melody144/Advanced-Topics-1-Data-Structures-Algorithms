import java.util.*;

/**
 * Title: Doubly List (problem set 4.3)
 * Description: implementing an unordered list (linked list) class
 * which implements the MyList interface - doubly linked list which allows repeated elements
 * Created: 2025-12-05
 * Last Updated: 2025-12-05
 * Author(s): Melody Wu
*/

public class DoublyList<E> implements MyList<E>{
	private ListNode head;
	private int size;

	public DoublyList(){
		this.head = new ListNode(null, null, null);
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
		ListNode newNode = new ListNode(obj, null, head.next);
		if(this.isEmpty()){
			this.head.prev = newNode;
		} else {
			ListNode next = head.next;
			next.prev = newNode;
		}
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
				prev.next = curr.next; // making the previous node connect to next node
				if(head.prev.equals(curr)){ // if curr is the last element of the list
					head.prev=prev;
				} else {
					ListNode next = curr.next;
					next.prev = prev; // connecting the next node to the previous node
					if(prev.equals(head)){ // unless the previous node is the head
						next.prev = null;
					}
					curr.next=null;
				}
				if(!head.next.equals(curr)){ // if it's not the first element of the list
					curr.prev=null;
				}
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
	 * Making lists displayed like this: [1, 2, 3, 4, etc.] but in reverse order
	 * @return the String
	 */
	public String reverseToString(){
		String s = "["; // string representation of list

		ListNode curr = head.prev;
		while(curr!=null){
			if(curr.prev==null){ // don't add the comma if its the last element of the list
				s+=curr.toString();
			} else{
				s+=curr.toString()+", ";
			}
			curr=curr.prev;
		}
		s+="]"; // end of list
		return s;
	}

	/**
	 * Adds a new item E to the end of the list making it the last item in the collection.
	 * @param obj the Object that we're adding to the end of the list
	 */
	public void append(E obj){
		ListNode newNode = new ListNode(obj, head.prev, null);
		if(this.isEmpty()){
			this.head.next = newNode;
			this.head.prev = newNode;
			this.size++;
			return;
		}
		ListNode oldLast = head.prev;
		oldLast.next = newNode;
		this.head.prev = newNode;
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

		ListNode curr = head.next;
		// removing that node
		head.next = curr.next; // the first element of the list is the next one
		if(head.prev.equals(curr)){ // if there's only one element in the list
			head.prev=null;
		} else {
			ListNode next = curr.next;
			next.prev = null;
			curr.next=null;
		}
		size--;
		return curr.data;
	}

	/**
	 * Removes and returns the last item in the list
	 * @return the value of the last item in the list or null if it's not in the list
	 */
	public E popRear() {
    	if(isEmpty()){
			return null;
		}

		ListNode curr = head.prev;
		// removing that node
		head.prev = curr.prev; // the first element of the list is the next one
		if(head.next.equals(curr)){ // if there's only one element in the list
			head.next=null;
		} else {
			ListNode before = curr.prev;
			before.next = null;
			curr.prev=null;
		}
		size--;
		return curr.data;
	}

	/**
	 * Removes and returns the item in the list at the given position
	 * @param pos of item we're removing
	 * @return the value of the last item in the list or null if it's not in the list
	 */
	public E pop(int pos){
		if(this.isEmpty()){
			return null;
		}

		// make sure that pos is a valid position
		if(pos>=this.size() || pos<0){
			return null;
		}

		ListNode curr = head.next;
		// looping through the list until we get to pos
		for(int i=0; i<pos; i++){
			curr = curr.next;
		}

		if(this.size==1){ // checking if curr is the only element
			head.next=null;
			head.prev=null;
		} else {
			ListNode next = curr.next;
			ListNode prev = curr.prev;
			if(head.next.equals(curr)){ // if curr is the first element
				head.next=next; // connecting head to the 2nd element
				next.prev = null; // making next the 1st element
				curr.next=null; // disconnecting curr
			} else if(head.prev.equals(curr)){ // if curr is the last element
				head.prev = prev; // making prev the last element
				prev.next = null;
				curr.prev = null; // disconnecting curr
			} else {
				// connect prev and next
				prev.next = next;
				next.prev = prev;
				curr.prev = null; // disconnecting curr
				curr.next = null;
			}
		}
		size--;
		return curr.data;
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
		ListNode newNode = new ListNode(obj, curr, curr.next);
		if(this.isEmpty()){
			head.next = newNode;
			head.prev = newNode;
			newNode.prev = null;
			newNode.next = null;
			size++;
			return;
		}

		if(pos==0){ // if insert at start of the list
			ListNode next = head.next;
			head.next = newNode; // connecting newNode
			next.prev = newNode;
			newNode.prev = null;
		} else if(pos==size){ // if at end of the list
			ListNode prev = head.prev;
			head.prev = newNode;
			prev.next = newNode;
			newNode.next = null;
		} else {
			ListNode next = curr.next;
			curr.next = newNode; // connecting newNode
			next.prev=newNode;
		}
		size++;
	}

	/**
	 * If the List is not empty, return the element that is currently at the front of the List.
	 * If the List is empty return null.  The contents of the List is not changed.
	 * @return The object's data at the front of the List or null if the List is empty
	 */
	public E front(){
		if(this.isEmpty()){
			return null;
		}
		return head.next.data;
	}

	/**
	 * If the List is not empty, return the element that is currently at the end of the List.
	 * If the List is empty return null.  The contents of the List is not changed.
	 * @return The object's data at the end of the List or null if the List is empty
	 */
	public E rear(){
		if(this.isEmpty()){
			return null;
		}
		return head.prev.data;
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
		private ListNode prev;

		public ListNode(E data, ListNode prev, ListNode next){
			this.data = data;
			this.prev = prev;
			this.next = next;
		}

		public String toString(){
			return String.valueOf(data);
		}
	}
}