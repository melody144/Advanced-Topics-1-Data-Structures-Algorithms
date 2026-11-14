/**
 * Title: key value pair
 * Description: implements a key value pair
 * Created: 2026-04-21
 * Last Updated: 2026-04-21
 * Author(s): Melody Wu
*/

public class KeyValuePair<K extends Comparable<? super K>, E> implements Comparable<KeyValuePair<K, E>>
{
       private K key;
       private E info;

       public KeyValuePair()
       {
          key  = null;
          info = null;
       }

       public KeyValuePair(K theKey, E theInfo)
       {
          key  = theKey;
          info = theInfo;
       }

       public K getFirst()
       { return key; }

       public E getSecond()
       { return info; }

       public void setFirst(K newKey)
       {   key = newKey;}

       public void setSecond(E newInfo)
       {   info = newInfo; }

       public String toString()
       {return key + "   " + info;}

       public int compareTo(KeyValuePair<K, E> other) {
        return this.key.compareTo(other.key);
       }
}

