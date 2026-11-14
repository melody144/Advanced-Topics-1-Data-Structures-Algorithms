**Title:** Concordance

**Description:** Implement a concordance. A concordance is an alphabetical list of the words (especially the important ones) present in a text, usually with citations of the passages in which they are found, according to Google. Follow all of the requirements listed below. 

### **Specification**

1. The folder name is concordance*\_XxxxY*   
2. Change **Xxxx** to your last name and **Y** to your first name’s Initial.  
   1. Example: concordance\_CruteD  
3. Name the main file that runs your code, Main.java

#### **What You Need to Do**

1. Read and parse the included .txt file.  
2. You must use the map you implemented in this chapter and the AVL tree you implemented in chapter 6\.  
3. The map should use the words as the keys.  
   1. Keys should ignore punctuations  
      1. Hyphenated words should appear as one word.  
         1. For example: “all-inclusive”, “year-end”, “follow-up”, “sign-in”, etc.  
      2. Words with apostrophe should appear as one word  
         1. For example: “that's”, “it's”, “can't”, “don't”, etc.  
   2. Keys should not be case sensitive  
      1. For example: “The” and “the”, “First” and “first”  
4. The AVL tree should store the number of times the word appears in the text.  
   1. Hint: You may want to use a AVLTree of KeyValuePairs  
5. The map values should store the locations of the words.  
   1. Locations should include the line number and the place in the line.  
      1. For example, line 96 reads: “The west was won with the efforts of the cowboy settlers that migrated in that direction.” The map would include “96:1”, “96:6”, and “96:9” as values for the word “the” in the map.  
6. Print the end results in a nice readable fashion.