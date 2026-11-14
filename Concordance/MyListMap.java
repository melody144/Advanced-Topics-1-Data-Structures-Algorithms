import java.util.*;

/**
 * Title: my list map
 * Description: implements a map
 * Created: 2026-04-21
 * Last Updated: 2026-04-21
 * Author(s): Melody Wu
*/

/*********************************************************************************
          No changes should be made to the class signature or constructors.
**********************************************************************************/
public class MyListMap<K extends Comparable<? super K>, E> implements MyMap<K,E> 
{
  private ListNode<K, E> dummy;
  private ListNode<K, E> cursor;
  private int size;

  public MyListMap()
  {
      dummy = new ListNode<K,E>();
      dummy.prev = null;
      dummy.next = null;
      cursor = dummy;
      size = 0;
  }

  public MyListMap(MyMap<K, E> otherMap)
  {
      this();
      for(Iterator<KeyValuePair<K, E>> it = otherMap.iterator(); it.hasNext(); )
      {
          KeyValuePair<K, E> pair = it.next();
          K key   = pair.getFirst();
          E info  = pair.getSecond();
          put(key, info);
      }
  }

  /**
  * Remove all the values from this collection so that it becomes empty.
  */
  public void clear(){
    // resetting map
    dummy = new ListNode<K,E>();
    dummy.prev = null;
    dummy.next = null;
    cursor = dummy;
    size = 0;
  }

  /**
  * @return the number of objects in this collection ;
  */
  public int size(){
    return size;
  }

  /**
  * If this collection is empty, return true otherwise return false.
  * @return A boolean indicating if this collection is or is not empty.
  */
  public boolean isEmpty(){
    return this.size==0;
  }


  /**
  * If this collection is contains the given value return true, otherwise return false.
  * @param The value being searched for in this collection.
  * @return A boolean indicating if this collection contains the value.
  */
  public boolean contains(K key){
    // looping through list to see if it contains this key
    for(KeyValuePair<K, E> pair: this){
      if(pair.getFirst().equals(key)){
        return true;
      }
    }
    return false;
  }


  /**
  * Remove the given item from this collection. If the item is not a member of this
  * collection the operation fails.
  * @return true if the value is removed, false if it is not removed.
  */
  public boolean remove(K key){
    ListNode<K, E> prev=dummy;
    cursor = dummy.next;

    // looping through the linked list until it gets to the wanted node
    while(cursor!=null){
      if(cursor.data.getFirst().equals(key)){
        // removing that node
        prev.next = cursor.next; // making the previous node connect to next node
        
        if(cursor.next==null){ // if curr is the last element of the list
          dummy.prev=prev;
        } else {
          ListNode<K,E> next = cursor.next;
          next.prev = cursor.prev; // connecting the next node to the previous node
          cursor.next=null;
        }
        if(cursor.prev!=null){ // if it's not the first element of the list
          cursor.prev=null;
        }
        size--;
        return true;
      }

      // moving to the next node
      prev = cursor;
      cursor = cursor.next;
    }

    return false;
  }


  /**
  * Create and return an iterator for this collection.
  * @return an iterator for this collection.
  */
  public Iterator< KeyValuePair<K, E> > iterator(){
    return new MyListItr();
  }

  /**
  * Return the Object that is associated with the given key in this map.
  * @param key whose associated value is to be returned.
  * @return the Object associated with the given key if the key is present in the map, null if the key is not in the map.
  * Note: This some implementations will have throw an error if not the key if not in the map.
  */
  public E get(K key){
    // loop through list until get to key and then return value paired with key
    for(KeyValuePair<K, E> pair: this){
      if(pair.getFirst().equals(key)){
        return pair.getSecond();
      }
    }

    throw new KeyError("Map doesn't contain this key"); // throw error if map doesn't contain key
  }

  /**
  * Change the value associated with the given key to the given value.
  * @param key and value that is to be associated with the key in this map.
  * @return true if the key is present in the map, false if it is not.
  */
  public boolean set(K key, E info){
    if(this.isEmpty()){
      return false;
    }

    cursor=dummy.next;
    while(cursor!=null){ // loop until get to key value
      if(cursor.data.getFirst().equals(key)){
        cursor.data.setSecond(info); // setting info
        return true;
      }
      cursor=cursor.next;
    }
    return false;
  }


  /**
  * Insert a key and value into this map (at the end of the list) if the key is not already present.
  * @param key and value the objects being added to this map
  * @return true if the object is added, false if it is not added.
  */
  public boolean put(K key, E info){
    // making sure this key isn't already in the map
    if(this.contains(key)){
      return false;
    }

    // creating the new node
    ListNode<K, E> newNode = new ListNode<>(key, info, dummy.prev, null);
    if(this.isEmpty()){
      this.dummy.next = newNode;
      this.dummy.prev = newNode;
    } else {
      ListNode oldLast = dummy.prev;
      oldLast.next = newNode;
      this.dummy.prev = newNode;
    }
    this.size++;
    return true;
  }

  /**
  * Insert a key and value into this map (in order) if the key is not already present.
  * @param key and value the objects being added to this map
  * @return true if the object is added, false if it is not added.
  */
  public boolean add(K key, E info){
    // making sure this key isn't already in the map
    if(this.contains(key)){
      return false;
    }

    if(this.isEmpty()){
      ListNode<K, E> newNode = new ListNode<>(key, info, null, null);
      this.dummy.next = newNode;
      this.dummy.prev = newNode;
      this.size++;
      return true;
    }

    cursor=dummy.next;
    // if key is less than the first element:
    if(key.compareTo(cursor.data.getFirst())<0){
      ListNode<K, E> newNode = new ListNode<>(key, info, null, cursor);
      this.dummy.next=newNode;
      cursor.prev = newNode;
      size++;
      return true;
    }

    ListNode<K, E> prev = cursor;
    cursor = cursor.next;

    while(cursor!=null){ // loop through map
      // add the key where its greater than node before but less than node after
      if(key.compareTo(prev.data.getFirst())>0 && key.compareTo(cursor.data.getFirst())<0){
        ListNode<K, E> newNode = new ListNode<>(key, info, prev, cursor);
        prev.next = newNode;
        cursor.prev = newNode;
        size++;
        return true;
      }

      // go to next node
      prev = cursor;
      cursor = cursor.next;
    }

    // otherwise, add at end of the list
    ListNode<K, E> newNode = new ListNode<>(key, info, prev, null);
    prev.next = newNode;
    dummy.prev = newNode;
    size++;
    return true;
  }

  /**
   * Making lists displayed like this: [(key:value), (key:value), ...]
   * @return the String
   */
  public String toString(){
    String s = "["; // string representation of list

    cursor=dummy.next;
    while(cursor!=null){
      if(cursor.next==null){ // don't add the comma if its the last element of the list
        s+=cursor.toString();
      } else{
        s+=cursor.toString()+", ";
      }
      cursor=cursor.next;
    }
    s+="]"; // end of list
    return s;
  }

  /**
   * @return a list of all keys in this map
   */
  public ArrayList<K> keys(){
    ArrayList<K> keys = new ArrayList<>();
    for(KeyValuePair<K, E> pair: this){
      keys.add(pair.getFirst());
    }
    return keys;
  }

  /**
   * @return a list of all values in this map
   */
  public ArrayList<E> values(){
    ArrayList<E> vals = new ArrayList<>();
    for(KeyValuePair<K, E> pair: this){
      vals.add(pair.getSecond());
    }
    return vals;
  }

  /**
   * @return a list of all key value pairs in this map
   */
  public ArrayList<KeyValuePair<K,E>> items(){
    ArrayList<KeyValuePair<K,E>> items = new ArrayList<>();
    for(KeyValuePair<K, E> pair: this){
      items.add(pair);
    }
    return items;
  }

  /**
   * merges 2 maps together. doesn't modify original maps. if a key exists in both maps,
   * keep the value in m1.
   * @param m2 - other map we're merging
   * @return new merged map
   */
  public MyListMap<K,E> merge(MyListMap<K, E> m2){
    MyListMap<K,E> map = new MyListMap();
    // loop through both maps and add the values to the new map
    for(KeyValuePair<K, E> pair: this){
      map.put(pair.getFirst(), pair.getSecond());
    }
    for(KeyValuePair<K,E> pair: m2){
      map.put(pair.getFirst(), pair.getSecond());
    }
    return map;
  }


/*********************************************************************************
         Inner Class: Inorder iterator for the list map
**********************************************************************************/
    private class MyListItr implements Iterator<KeyValuePair<K, E>>
    {
      private ListNode<K, E> curr;

      public MyListItr(){
        curr = dummy.next;
      }

      public boolean hasNext(){
        return curr!=null;
      }

      public KeyValuePair<K, E> next(){
        if(!hasNext()){
          throw new NoSuchElementException();
        }

        KeyValuePair<K, E> data = curr.data;
        curr = curr.next;
        return data;
        
      }
    }

/*********************************************************************************
         Inner Class: Nodes for the list map
**********************************************************************************/
  private static class ListNode<K extends Comparable<? super K>, E>
  {
    private KeyValuePair<K, E> data;
    private ListNode<K, E> next;
    private ListNode<K, E> prev;

    public ListNode(K key, E info, ListNode<K, E> prev, ListNode<K, E> next){
      this.data = new KeyValuePair<>(key, info);
      this.next = next;
      this.prev = prev;
    }

    public ListNode(){
      this.data = null;
      this.next = null;
      this.prev = null;
    }

    public String toString(){
      return "("+data.getFirst()+":"+data.getSecond()+")";
    }

    public KeyValuePair<K, E> getData(){
      return data;
    }
  }

  private static class KeyError extends RuntimeException{
    public KeyError(String message){
      super(message);
    }
  }
}
