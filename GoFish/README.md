# **Title:** Extreme Cutthroat Go Fish

# Description: 

Implement the game Go Fish that follows the specifications listed below. This will be a simple turn-based game between a user and up to three AIs. If you are unfamiliar with this game you can watch [this short video](https://youtu.be/hRpXLSMdve0) detailing the rules or play this [online version](https://cardgames.io/gofish/).

# Specification

1. The folder name is gofish*\_XxxxY*   
2. Change **Xxxx** to your last name, and **Y** to your first name’s Initial.  
   1. Example: gofish\_CruteD  
3. Name the main file that runs your code, GoFish

## What You Need to Do

1. You must use two linked list implementations  
   1. One must implement and use a circular linked list.   
2. Normally, GoFish is played with one deck of cards, but you must allow for one to three decks in the game.  
   1. Ask the user at the start of a game for their name and the number of decks they want to use.  
3. The cards’ values must be stored as integers, 1-52, in your data structures but displayed on the screen in the following format, face-value/suit (Ace/Spades, Jack/Heart, 4/Diamonds, 9/Clubs).  
   1. Card values are numbered 1-52, and if using multiple decks, a value should always reference the same face-value/suit, for example, if three decks are chosen, the number one should appear three times in your data structure and each time should reference the same face-value/suit pair such as Ace/Heart. (*Not exact*)  
   2. Card Face-values are 2-10, Jack, Queen, King, and Ace.   
      1. No Jokers  
   3. Card Suits are Spades, Hearts, Clubs, and Diamonds.  
4. The user should be prompted for the number of AI players.  
   1. You must allow for 1-3 AI players.

## Gameplay

1. The player order is randomly chosen.  
   1. The starting player is randomly chosen as well.  
   2. Display player order and starting player on the screen  
2. Each player is dealt seven cards.  
   1. The remaining becomes the draw pile.  
3. If it is the player's turn  
   1. They will type the AI player they want to ask, and the card they are looking for.  
      1. For Example Player 1, Jack  
      2. The player can ask for ***any*** card.  
   2. If the AI player has the card(s), they will be given to the player, and they can go again.  
      1. All cards of that value will be given to the player.  
   3. If the AI player doesn’t have the card, it will display go fish, and a card is added to the player's hand from the draw pile.  
   4. Their turn is now over.  
4. If it is an AI player’s turn  
   1. They will randomly choose a player to ask and randomly choose a card from their hand to ask for.  
   2. Same as with a human player, if the player asked has the card, it will be given to them, and they will go again. If they do not have a card, Go Fish is displayed, a card will be plucked from the draw pile, and their turn ends.  
5. When a player has four cards of a single value, the cards are put into protection and can not be given away.  
6. The game ends when a player has no more cards in their hand.  
7. The winner is the player with the most protected cards when the game ends.

## Extra Credit:

1. GUI (50%)  
   1. Initial User Input  
   2. Gameplay  
2. Smart AI (50%)  
   1. Implement game strategy into the AI players for a more competitive experience.