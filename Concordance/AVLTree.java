import java.util.*;

/**
 * Title: AVL tree (problem set 6.3)
 * Description: implementing an AVL tree
 * Created: 2026-02-06
 * Last Updated: 2026-02-06
 * Author(s): Melody Wu
*/

public class AVLTree<E extends Comparable<? super E>> implements Iterable<E>{
	public TreeNode root;
	private int size;
	// TODO: add height
	// TODO: check balanced

	public AVLTree(){
		this.root = null;
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
	 * adds a new node to the binary tree
	 * @param item we're inserting into tree
	 */
	public void insert(E item){
		// making sure this item isn't already in tree
		if(this.find(item)){
			return;
		}

		// if tree is empty, make the item into the root
		if(this.isEmpty()){
			this.root = new TreeNode(item, null, null, null);
			this.size++;
			return;
		}

		// finding where to put the item in the sorted place
		TreeNode curr = this.root;
		while(curr!=null){
			// if the item is smaller than current node, go to left child
			// inserting when the child is null
			if(curr.data.compareTo(item)>0){
				if(curr.lChild==null){
					TreeNode t = new TreeNode(item, curr, null, null);
					curr.lChild = t;
					this.size++;
					check(t); // making sure the tree is balanced
					return;
				}
				curr=curr.lChild;
			// otherwise go to right child and insert if child is null
			} else if(curr.data.compareTo(item)<0){
				if(curr.rChild==null){
					TreeNode t = new TreeNode(item, curr, null, null);
					curr.rChild = t;
					this.size++;
					check(t); // making sure tree is balanced
					return;
				}
				curr = curr.rChild;
			}
		}
	}

	/**
	 * tests to see if the specified item is in the tree
	 * @param item we're finding
	 * @return true if it's in the tree, false o/w
	 */
	public boolean find(E item){
		if(this.isEmpty()){
			return false;
		}

		// iterating through tree until get to item, going left if the current is bigger, right o/w
		TreeNode curr = this.root;
		while(curr!=null){
			if(curr.data.compareTo(item)==0){
				return true;
			} else if(curr.data.compareTo(item)>0){
				curr = curr.lChild;
			} else if(curr.data.compareTo(item)<0){
				curr = curr.rChild;
			}
		}
		return false;
	}

	/**
	 * deleteing item from the binary tree
	 * @param item we're deleting
	 */
	public void delete(E item){
		if(this.isEmpty()||!this.find(item)){
			return;
		}

		// finding the node to delete
		TreeNode curr = this.root;
		while(curr!=null&&!curr.data.equals(item)){
			if(curr.data.compareTo(item)>0){
				curr = curr.lChild;
			} else if(curr.data.compareTo(item)<0){
				curr = curr.rChild;
			}
		}

		delete(curr);
	}

	/**
	 * deleteing item from the binary tree
	 * @param curr node we're deleting from tree
	 */
	public void delete(TreeNode curr){
		if(curr==null){
			return;
		}
		// if the node has no children - directly remove
		if(curr.lChild==null && curr.rChild==null){
			if(curr==this.root){
				root=null;
				size--;
				return;
			}
			TreeNode parent = curr.parent;
			if(parent.lChild==curr){
				parent.lChild = null;
			} else {
				parent.rChild = null;
			}
			curr.parent = null;
			size--;
			check(parent); // making sure tree is balanced
			return;
		} else if(curr.lChild==null || curr.rChild==null){ // 1 child
			// the child of the node
			TreeNode child = (curr.lChild==null) ? curr.rChild : curr.lChild;
			// if curr is the root, then make the child into the root
			if(curr==this.root){
				curr.lChild = null;
				curr.rChild = null;
				root = child;
				child.parent=null;
				size--;
				return;
			}
			
			TreeNode parent = curr.parent;
			if(parent.lChild==curr){
				parent.lChild=child;
			} else {
				parent.rChild = child;
			}
			child.parent = parent;
			curr.parent = null;
			curr.lChild = null;
			curr.rChild = null;
			size--;
			check(parent); // making sure tree is balanced
			return;
		}

		// 2 children - find rightmost node on left side

		TreeNode want = curr.lChild;
		while(want.rChild!=null){
			want=want.rChild;
		}
		// replace data of node we're deleting w/ data of ^ node
		curr.data = want.data;
		delete(want);
	}

	// returns the height of a certain node, -1 if null
	private int height(TreeNode node){
		if(node==null){
			return -1;
		}
		return node.height;
	}

	// updates the height of node, 1 more than the maximum of its children
	private void updateHeight(TreeNode node){
		node.height = Math.max(height(node.lChild),height(node.rChild)) + 1;
	}

	// returns the balance factor of a node
	private int balance(TreeNode node){
		if(node==null){
			return 0;
		}
		return height(node.lChild) - height(node.rChild);
	}

	// check if a node is balanced and balancing it if it isn't
	private void check(TreeNode node){
		while(node != null){

			// update the height of that node
			updateHeight(node);
			int bf = balance(node); // balance factor

			// left heavy
			if(bf>1){
				// right right rotation
				if(balance(node.lChild)>=0){
					right(node);
				} else { // otherwise left right rotation
					left(node.lChild);
					right(node);
				}
			} else if (bf<-1){
				// right left rotation
				if(balance(node.rChild)>=0){
					right(node.rChild);
					left(node);
				} else {
					// left left rotation
					left(node);
				}
			}

			node = node.parent; // move up the tree
		}
	}

	/**
	 *   x       y
	 *  /   -->   \
	 * y           x
	 * 
	 */ 
	private void right(TreeNode node){
		TreeNode left = node.lChild;
		// if the left child has a right child, attach the right child to node
		TreeNode rc = left.rChild;

		left.rChild = node; // node becomes the right chhild of left
		node.lChild = rc;
		if(rc!=null){
			rc.parent = node;
		}

		TreeNode parent = node.parent;
		left.parent = parent;
		node.parent = left;

		// making the parent of node point to left
		if(parent == null){
			root = left;
		} else if (parent.lChild == node){
			parent.lChild = left;
		} else {
			parent.rChild = left;
		}

		updateHeight(node);
		updateHeight(left);
	}

	/**
	 *   x         y
	 *    \  -->  /
	 *     y     x
	 * 
	 */ 
	private void left(TreeNode node){
		TreeNode right = node.rChild;
		// if the right child has a left child, attach the left child to node
		TreeNode lc = right.lChild;

		right.lChild = node; // node becomes the left child of right
		node.rChild = lc;
		if(lc!=null){
			lc.parent = node;
		}

		TreeNode parent = node.parent;
		right.parent = parent;
		node.parent = right;

		// making the parent of node point to right
		if(parent == null){
			root = right;
		} else if (parent.lChild == node){
			parent.lChild = right;
		} else {
			parent.rChild = right;
		}

		updateHeight(node);
		updateHeight(right);
	}

	/**
	 * Returns a string of the binary tree and it's contents, 
	 * formatted as a list of pairs, in-order, top to bottom, left to right.
	 * For example: [(null, 4), (4, 10), (4, 11), (10, 7), (10, 9), (11, 8)]
	 * @return the String
	 */
	public String toString(){
		if(this.isEmpty()){
			return "[]";
		}

		String s = "[(null, "+root.toString()+")"; // string representation of list - adding the root to the string already

		ListQueue<TreeNode> q = new ListQueue<>(); // queue's FiFo helps iterate through the tree
		if(root.lChild != null){
			q.enqueue(root.lChild);
		}
		if(root.rChild != null){
			q.enqueue(root.rChild);
		}

		// iterating through tree
		while(!q.isEmpty()){
			TreeNode curr = q.dequeue();
			s+=", (";
			if(curr.parent == null){
				s+="null";
			} else {
				s+=curr.parent.toString();
			}
			s+=", ";
			if(curr.data == null){
				s+="null";
			} else {
				s+=curr.toString();
			}
			s+=")";
			// top to bottom, left to right
			if(curr.lChild != null){
				q.enqueue(curr.lChild);
			}
			if(curr.rChild != null){
				q.enqueue(curr.rChild);
			}
		}

		
		s+="]"; // end of list
		return s;
	}

	public TreeNode getRoot(){
		return root;
	}

	public Iterator<E> iterator(){
		return new TreeItr();
	}

	private class TreeItr implements Iterator<E>{
		// keeps track of next nodes
		private ListStack<TreeNode> stack = new ListStack<>();
		private TreeNode current = root; // keep track of node we’re on now
		
		public boolean hasNext(){
			return (!stack.isEmpty() || current != null);
		}

		public E next(){
			E item = null;
			if(this.hasNext()){
				while(current != null){
					stack.push(current);
					current = current.lChild;
				}
				TreeNode node = stack.pop();
				item = node.data;

				current = node.rChild;
			}

			return item;
		}
	}


	// private class of nodes
	public class TreeNode{
		private int height;
		private E data;
		private TreeNode parent;
		private TreeNode lChild;
		private TreeNode rChild;

		public TreeNode(E data, TreeNode parent, TreeNode lChild, TreeNode rChild){
			this.height = 0;
			this.data = data;
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
		}

		public E getData(){
			return data;
		}
		public int getHeight(){
			return height;
		}
		public TreeNode getLChild(){
			return lChild;
		}
		public TreeNode getRChild(){
			return rChild;
		}

		public String toString(){
			return String.valueOf(data);
		}
	}
}