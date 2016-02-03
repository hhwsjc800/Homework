//============================================================================
// Name        : CS1B (Java) Assignment 2: Adding a Deck
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Jan 18, 2016
// Instructor  : Harden
// Description : This program consists of the classes of Assignment 1 with the
//               addition of another class: Deck. Like the other classes, Deck
//               simulates a deck of regular playing cards. It comes with the 
//               capability to shuffle decks and 'push'/'pop' cards from Decks.
//               Main is used to test the functionality of the standalone 
//               features of the Deck class and also its compatibility with 
//               the Hand class.
//============================================================================

import java.util.Scanner;


public class Foothill
{               
    public static void main(String[] args)
    {                                 
        //======PHASE 1: DECK CLASS================
        Deck myDeck = new Deck();
        int deckSize = myDeck.getNumCards();
        
        System.out.println("=======BEGIN PHASE 1=========");
        System.out.println("Regular deck, no shuffle, 1 pack: ");
        for(int i = 0; i < deckSize; i++)
            System.out.println(myDeck.getTopCard().toString());
        
        myDeck.restock(1);
        myDeck.reorder();
        
        System.out.println("\n\nSame deck after restock and reorder: ");
        for(int i = 0; i < deckSize; i++)
            System.out.println(myDeck.getTopCard().toString());
        
        //3 pack deck tests
        Deck tripDeck = new Deck(3);
        deckSize = tripDeck.getNumCards();
        
        System.out.println("\n\n3 pack deck, no shuffle,: ");
        for(int i = 0; i < deckSize; i++)
            System.out.println(tripDeck.getTopCard().toString());
        
        tripDeck.restock(3);
        tripDeck.reorder();
        
        System.out.println("\n\nSame deck after restock and reorder: ");
        for(int i = 0; i < deckSize; i++)
            System.out.println(tripDeck.getTopCard().toString());
        
        //===PHASE 2: HAND AND DECK CLASS===============
        int numPlayers = 0;
        Scanner inputStream = new Scanner(System.in);
        boolean validInput = false;
        System.out.println("=======BEGIN PHASE 2=========");
        
        //Checks for valid user input
        while(!validInput) 
        {
            System.out.print("Choose a number of players (1-12) : ");
            while (!inputStream.hasNextInt())
            {
                System.out.print("Please enter a a valid integer: ");
                inputStream.next();
            }
            
            numPlayers = inputStream.nextInt();
            
            if (numPlayers < 1 || numPlayers > 12)
                continue;
            else
                validInput = true;
        }
        
        Deck playDeck = new Deck();
        deckSize = playDeck.getNumCards();
        Hand[] players = new Hand[numPlayers];
        
        //initializing hands
        for (int k = 0; k < numPlayers; k++)
            players[k] = new Hand();
                
        //Dealing deck to players
        for (int i = 0; i < deckSize; i ++)
            players[i % numPlayers].takeCard(playDeck.getTopCard());

        //Showing hands
        System.out.println("Showing hands after dealing (1 deck): ");
        for (int k = 0; k < numPlayers; k++)
            System.out.println("\n**Hand " + (k + 1) + "**\n" + 
                                players[k].toString());
        
        //Resetting hands and shuffling deck
        for (int k = 0; k < numPlayers; k++)
            players[k].resetHand();
        
        playDeck.restock(1);
        playDeck.reorder();
        
        //Dealing deck to players
        for (int i = 0; i < deckSize; i ++)
            players[i % numPlayers].takeCard(playDeck.getTopCard());

        //Showing hands
        System.out.println("Showing hands after dealing (1 deck shuffled): ");
        for (int k = 0; k < numPlayers; k++)
            System.out.println("\n**Hand " + (k + 1) + "**\n" + 
                                players[k].toString());
        inputStream.close();

    }
}

class Card
{               
    //----public class members and accessors
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES};

    public Suit getSuit() {return suit;}
    public char getValue() {return value;}
    public boolean getErrorFlag() {return errorFlag;}
   
    //-----private class members
    private char value;
    private Suit suit;
    private boolean errorFlag;
   
    //Default constructor for Card class sets cards to Ace of Spades.
    public Card()
    {         
        value = 'A';
        suit = Suit.SPADES;
        errorFlag = false;
    }
   
   
   
   
   
   
    //Overloaded ctor for card class calls set function. Passing inval params
    //leaves the private member data unchanged except for errorFlag.
    //Passes two params, value which is a char used to try to init a card, and
    //suit which is a suit used to try to init a card too.
    public Card(char value, Suit suit)
    {             
       set(value, suit);
    }

   
   
   
   
   
    //If valid parameters are passed by the client, the card's private member
    //data is set to the passed values. Otherwise, errorFlag is set to true
    //and the private member fields are untouched
    public boolean set(char value, Suit suit)
    {   
        if (isValid(value, suit)) 
        {          
            errorFlag = false;
            this.value = value;
            this.suit = suit;
            return true;
        }

        else 
        {   
            errorFlag = true;
            return false;
        }

    }
   
   
   
   
   
   
    //Provides a check for the validity of passed values from client when 
    //instantiating cards. Returns true for valid char values, false for 
    //invalid (non playing-card) values
    private static boolean isValid(char value, Suit suit)
    {              
        if (!Character.isLetterOrDigit(value))
            return false;

        value = Character.toUpperCase(value);
        if (value != 'A' && value != 'J' && value != 'Q' && value != 'K'&&
            value != 'T' && (value > '9' || value < '2'))
            return false;
      
        return true;
    }
   
   
   
   
   
   
   
    //toString functionality for cards. If the card has errorFlag true, returns
    //an invalid string. Otherwise, returns the card value and suit as string.
    //ret is a string created in the function that serves as the return string.
    public String toString()
    {           
        if (errorFlag)
            return "[invalid]";
      
        String ret =  Character.toString(value) + " of ";
      
        switch (suit)
        {   
         case CLUBS:
            ret += "Clubs";
            break;
         case DIAMONDS:
            ret += "Diamonds";
            break;
         case HEARTS:
            ret += "Hearts";
            break;
         case SPADES:
            ret += "Spades";
            break;
         default:
            break;
        }
        
        return ret;
    }
   
   
   
   
   

    
    //Returns TRUE if the passed card is equal in all aspects to the calling
    //card
    public boolean equals(Card card)
    {   
        if (this.suit == card.suit
            && this.errorFlag == card.errorFlag
            && this.value == card.value)
            return true;
      
        return false;
    }
   
}

class Hand
{  
    //---public member data and accessors-----
    public static final int MAX_CARDS = 52;
    public int getNumCards() { return numCards; }

    //----private member data----
    private Card[] myCards;
    private int numCards;
   
    //Hand default constructor. No overloaded ctor is provided.
    Hand()
    {           
        myCards = new Card[MAX_CARDS];
        numCards = 0;
        for (int k = 0; k < MAX_CARDS; k++)
            myCards[k] = new Card();
    }
   
    
    
    
    
    
    //'Resets' hand by setting numCards to 0. Does not remove any cards from
    //the myCards array.
    public void resetHand()
    {         
        numCards = 0;
    }
   
    
    
    
    
    
    //Adds a card to the hand (myCard array), provided that there is room and 
    //the card is valid. Returns TRUE as long as there is room in the hand
    //but does not add invalid cards to the hand.
    public boolean takeCard(Card card) 
    {        
        if (numCards >= MAX_CARDS)
            return false;

        if (card.getErrorFlag() == false)
        {             
            myCards[numCards] = card;
            numCards++;
        }

        return true;
    }
   
    
    
    
    
    
    //Returns the Card from the highest index of the array. If there are no 
    //cards in the hand, then returns an invalid Card.
    public Card playCard()
    {      
        if (numCards <= 0)
            return new Card('X', Card.Suit.SPADES);

        Card temp = myCards[numCards - 1];
        numCards--;
       
        return temp;
    }

    
    
    
    
    
    //toString function for Hand class. Will show empty string if there are no
    //cards in the hand. Otherwise returns string of all of the cards in hand.
    public String toString()
    {    
        String handStr = "Hand: ";
      
        if (numCards <= 0)
            return handStr;

        for (int k = 0; k < numCards; k++)
            handStr += myCards[k].toString() + " \n";
      
        return handStr;
    }

    
    
    
    
    
    //If the client passes a valid index, then returns the card from myCards 
    //array at index k. Otherwise, returns a generic invalid card.
    public Card inspectCard(int k)
    {   
        if (k < 0 || (k > numCards - 1) || k >= MAX_CARDS)
            return new Card('X', Card.Suit.SPADES);

        return myCards[k];
    }
}  

//Deck class as mentioned in the initial comments simulates one or many decks
//of playing cards. It is intended to be the source of all cards in the program
class Deck
{
    //Limits on Deck capacity
    private static final int MAX_PACKS = 6;
    private static final int NUM_CARDS_PER_PACK = 52;
    private static final int MAX_CARDS_PER_DECK = MAX_PACKS*NUM_CARDS_PER_PACK;
    
    //private static member data
    private static Card[] staticPack;
    private static boolean staticPackInit = false;
    
    //private member fields
    private Card[] cards;
    private int numCards;
    private int numPacks;
    
    //---generic accessors----
    public int getNumCards() {return numCards;}
    
    
    
    
    
    
    
    //--member functions------
    
    //Populates the staticPack 'master pack' with ONE copy of a full deck of 
    //playing cards. Takes no parameters and returns no parameters, but 
    //initializes staticPack.
    private static void allocateStaticPack()
    {
        Card.Suit suits[] = {Card.Suit.CLUBS, Card.Suit.DIAMONDS,
                             Card.Suit.HEARTS, Card.Suit.SPADES};
        
        staticPack = new Card[NUM_CARDS_PER_PACK];
        char charVal = '2';
        //initializes deck by suit 
        for (int i = 0; i < 4; i++)
        {   
            charVal = '2';
            //set 2-9 values for given suit
            for (int k = 0; k < 8; k++)
            {
                staticPack[(13 * i) + k] = new Card(charVal, suits[i]);
                charVal++;
            }
            
            //set T-A values for given suit
            staticPack[(13*i) + 8] = new Card('T', suits[i]);
            staticPack[(13*i) + 9] = new Card('J', suits[i]);
            staticPack[(13*i) + 10] = new Card('Q', suits[i]);
            staticPack[(13*i) + 11] = new Card('K', suits[i]);
            staticPack[(13*i) + 12] = new Card('A', suits[i]);         
        }
        staticPackInit = true;
    }
    
    
    
    
    
    
    //default constructor for Deck class. No throw guarantee.
    Deck()
    {
        //if no numPacks provided, numPacks = 1 
        numPacks = 1;
        
        //staticPack should only be initialized once
        if (staticPackInit == false)
            allocateStaticPack();
        
        //fills the deck with cards
        restock (numPacks);
    }
    
    
    
    
    
    
    
    //overloaded constructor for Deck class. Takes an int numPacks
    //requested then tries to fill the cards[] array with numPacks
    //of cards. If the number is invalid, numCards is set to 0. 
    //All other private member fields are untouched.
    Deck(int numPacks)
    {
        //staticPack should only be initialized once
        if (staticPackInit == false)
            allocateStaticPack();
        
        //fills the deck with cards. Update numCards if successful
        if (!restock (numPacks))
            numCards = 0;
    }
    
    
    
    
    
    
    
    //Shuffles the cards in the cards[] array using the random number generator
    //Calls Math.random() which is multiplied by 1000 then modded by numCards
    //and cards are swapped around in the cards[] array until reorder goes 
    //through the entire cards[] array. A variable called randNum is used to
    //store the random number from the generator.
    public void reorder()
    {
        Card temp = new Card('A', Card.Suit.SPADES);
        int randNum = (int) Math.random() * 1000;
        
        for (int i = 0; i < numCards; i++)
        {
            randNum = (int) (Math.random() * 1000); //update randNum    
            temp = cards[i];
            cards[i] = cards[(randNum) % numCards];
            cards[(randNum) % numCards] = temp;
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    //Helper method for Deck constructor. Takes an int numPacks, checks for the 
    //validity of numPacks, then if numPacks is valid, repopulates the 
    //cards[] member field with 52x the number of packs specified and returns
    //TRUE. Uses staticPack refs to populate the array (since any Deck object 
    //creation implies that staticPack is already populated).
    //If numPacks is invalid, returns FALSE without changing private
    //member fields. Restock also updates numCards accordingly.
    public boolean restock(int numPacks)
    {
        if (numPacks <= 0 || numPacks > MAX_PACKS)
            return false;
        
        numCards = numPacks * NUM_CARDS_PER_PACK;
        cards = new Card[numCards];
        for (int i = 0; i < numPacks; i++)
        {
            for (int k = 0; k < NUM_CARDS_PER_PACK; k++)
            {
                cards[k + (52*i)] = staticPack[k];
                
            }
        }

        return true;
    }
    
    
    
    
    
    
    
    
    //getTopCard returns the 'top' card of the cards[] array, similar to a 
    //'popping' the value from the array. Does not get rid of the card,
    //but updates the number of cards in the deck correspondingly. Uses
    //a variable called temp to create a reference to the card, and returns
    //temp if possible. If there are no cards in the deck, returns a generic
    //invalid card.
    public Card getTopCard()
    {
        if (numCards <= 0)
            return new Card('X', Card.Suit.SPADES);
        
        Card temp = cards[numCards - 1];
        numCards--;
        
        return temp;
    }
    
    
    
    
    
    
    
    //If the client passes a valid index int k, returns card from cards[]
    //array at k. Otherwise, returns a generic invalid card.
    public Card getCard(int k)
    {   
        if (k < 0 || (k > numCards - 1) || k >= MAX_CARDS_PER_DECK)
            return new Card('X', Card.Suit.SPADES);

        return cards[k];
    }
    
}
/************RUN OF ASSIGNMENT 2***********************

=======BEGIN PHASE 1=========
Regular deck, no shuffle, 1 pack: 
A of Spades
K of Spades
Q of Spades
J of Spades
T of Spades
9 of Spades
8 of Spades
7 of Spades
6 of Spades
5 of Spades
4 of Spades
3 of Spades
2 of Spades
A of Hearts
K of Hearts
Q of Hearts
J of Hearts
T of Hearts
9 of Hearts
8 of Hearts
7 of Hearts
6 of Hearts
5 of Hearts
4 of Hearts
3 of Hearts
2 of Hearts
A of Diamonds
K of Diamonds
Q of Diamonds
J of Diamonds
T of Diamonds
9 of Diamonds
8 of Diamonds
7 of Diamonds
6 of Diamonds
5 of Diamonds
4 of Diamonds
3 of Diamonds
2 of Diamonds
A of Clubs
K of Clubs
Q of Clubs
J of Clubs
T of Clubs
9 of Clubs
8 of Clubs
7 of Clubs
6 of Clubs
5 of Clubs
4 of Clubs
3 of Clubs
2 of Clubs


Same deck after restock and reorder: 
5 of Diamonds
J of Clubs
3 of Spades
A of Hearts
T of Hearts
2 of Spades
K of Diamonds
2 of Hearts
4 of Spades
5 of Spades
7 of Spades
T of Spades
T of Diamonds
9 of Clubs
J of Spades
J of Hearts
4 of Clubs
A of Spades
5 of Hearts
2 of Diamonds
6 of Clubs
3 of Hearts
Q of Hearts
8 of Diamonds
9 of Hearts
5 of Clubs
9 of Diamonds
A of Diamonds
Q of Clubs
6 of Hearts
8 of Hearts
7 of Diamonds
7 of Hearts
K of Hearts
6 of Diamonds
8 of Clubs
Q of Spades
7 of Clubs
6 of Spades
A of Clubs
9 of Spades
3 of Clubs
4 of Diamonds
T of Clubs
2 of Clubs
3 of Diamonds
Q of Diamonds
8 of Spades
K of Spades
J of Diamonds
4 of Hearts
K of Clubs


3 pack deck, no shuffle,: 
A of Spades
K of Spades
Q of Spades
J of Spades
T of Spades
9 of Spades
8 of Spades
7 of Spades
6 of Spades
5 of Spades
4 of Spades
3 of Spades
2 of Spades
A of Hearts
K of Hearts
Q of Hearts
J of Hearts
T of Hearts
9 of Hearts
8 of Hearts
7 of Hearts
6 of Hearts
5 of Hearts
4 of Hearts
3 of Hearts
2 of Hearts
A of Diamonds
K of Diamonds
Q of Diamonds
J of Diamonds
T of Diamonds
9 of Diamonds
8 of Diamonds
7 of Diamonds
6 of Diamonds
5 of Diamonds
4 of Diamonds
3 of Diamonds
2 of Diamonds
A of Clubs
K of Clubs
Q of Clubs
J of Clubs
T of Clubs
9 of Clubs
8 of Clubs
7 of Clubs
6 of Clubs
5 of Clubs
4 of Clubs
3 of Clubs
2 of Clubs
A of Spades
K of Spades
Q of Spades
J of Spades
T of Spades
9 of Spades
8 of Spades
7 of Spades
6 of Spades
5 of Spades
4 of Spades
3 of Spades
2 of Spades
A of Hearts
K of Hearts
Q of Hearts
J of Hearts
T of Hearts
9 of Hearts
8 of Hearts
7 of Hearts
6 of Hearts
5 of Hearts
4 of Hearts
3 of Hearts
2 of Hearts
A of Diamonds
K of Diamonds
Q of Diamonds
J of Diamonds
T of Diamonds
9 of Diamonds
8 of Diamonds
7 of Diamonds
6 of Diamonds
5 of Diamonds
4 of Diamonds
3 of Diamonds
2 of Diamonds
A of Clubs
K of Clubs
Q of Clubs
J of Clubs
T of Clubs
9 of Clubs
8 of Clubs
7 of Clubs
6 of Clubs
5 of Clubs
4 of Clubs
3 of Clubs
2 of Clubs
A of Spades
K of Spades
Q of Spades
J of Spades
T of Spades
9 of Spades
8 of Spades
7 of Spades
6 of Spades
5 of Spades
4 of Spades
3 of Spades
2 of Spades
A of Hearts
K of Hearts
Q of Hearts
J of Hearts
T of Hearts
9 of Hearts
8 of Hearts
7 of Hearts
6 of Hearts
5 of Hearts
4 of Hearts
3 of Hearts
2 of Hearts
A of Diamonds
K of Diamonds
Q of Diamonds
J of Diamonds
T of Diamonds
9 of Diamonds
8 of Diamonds
7 of Diamonds
6 of Diamonds
5 of Diamonds
4 of Diamonds
3 of Diamonds
2 of Diamonds
A of Clubs
K of Clubs
Q of Clubs
J of Clubs
T of Clubs
9 of Clubs
8 of Clubs
7 of Clubs
6 of Clubs
5 of Clubs
4 of Clubs
3 of Clubs
2 of Clubs


Same deck after restock and reorder: 
7 of Hearts
A of Clubs
K of Hearts
6 of Spades
7 of Spades
K of Hearts
8 of Hearts
8 of Diamonds
9 of Hearts
A of Spades
7 of Diamonds
6 of Clubs
K of Diamonds
T of Spades
Q of Hearts
A of Clubs
9 of Spades
5 of Hearts
9 of Clubs
2 of Spades
7 of Hearts
3 of Clubs
9 of Spades
T of Clubs
6 of Spades
4 of Hearts
3 of Hearts
J of Diamonds
6 of Hearts
J of Hearts
8 of Clubs
6 of Clubs
7 of Spades
2 of Hearts
6 of Hearts
2 of Hearts
4 of Clubs
6 of Hearts
A of Clubs
3 of Diamonds
4 of Clubs
T of Diamonds
4 of Diamonds
2 of Clubs
K of Clubs
7 of Diamonds
8 of Spades
K of Diamonds
5 of Diamonds
8 of Diamonds
T of Spades
T of Diamonds
J of Clubs
Q of Clubs
J of Clubs
J of Hearts
Q of Diamonds
5 of Clubs
J of Spades
2 of Spades
T of Hearts
K of Spades
3 of Spades
7 of Clubs
5 of Hearts
T of Clubs
A of Spades
K of Diamonds
3 of Hearts
3 of Clubs
7 of Clubs
8 of Hearts
4 of Hearts
2 of Diamonds
4 of Diamonds
2 of Spades
9 of Hearts
A of Hearts
7 of Spades
Q of Hearts
T of Hearts
5 of Spades
2 of Diamonds
5 of Clubs
5 of Spades
J of Diamonds
2 of Clubs
A of Hearts
7 of Diamonds
5 of Hearts
4 of Clubs
Q of Diamonds
Q of Spades
5 of Spades
Q of Diamonds
J of Spades
5 of Diamonds
2 of Diamonds
K of Spades
T of Hearts
J of Spades
9 of Diamonds
A of Hearts
4 of Hearts
J of Hearts
T of Spades
6 of Diamonds
8 of Spades
9 of Hearts
A of Diamonds
Q of Spades
4 of Spades
3 of Diamonds
Q of Clubs
4 of Diamonds
A of Diamonds
6 of Clubs
8 of Clubs
5 of Diamonds
K of Hearts
K of Clubs
4 of Spades
3 of Diamonds
9 of Diamonds
9 of Spades
8 of Hearts
3 of Spades
7 of Clubs
K of Clubs
6 of Spades
3 of Hearts
8 of Spades
4 of Spades
2 of Hearts
A of Spades
T of Clubs
J of Diamonds
9 of Clubs
9 of Diamonds
8 of Diamonds
3 of Spades
K of Spades
A of Diamonds
3 of Clubs
Q of Clubs
7 of Hearts
Q of Hearts
2 of Clubs
6 of Diamonds
9 of Clubs
5 of Clubs
8 of Clubs
6 of Diamonds
J of Clubs
T of Diamonds
Q of Spades
=======BEGIN PHASE 2=========
Choose a number of players (1-12) : 11
Showing hands after dealing (1 deck): 

**Hand 1**
Hand: A of Spades 
3 of Spades 
5 of Hearts 
7 of Diamonds 
9 of Clubs 


**Hand 2**
Hand: K of Spades 
2 of Spades 
4 of Hearts 
6 of Diamonds 
8 of Clubs 


**Hand 3**
Hand: Q of Spades 
A of Hearts 
3 of Hearts 
5 of Diamonds 
7 of Clubs 


**Hand 4**
Hand: J of Spades 
K of Hearts 
2 of Hearts 
4 of Diamonds 
6 of Clubs 


**Hand 5**
Hand: T of Spades 
Q of Hearts 
A of Diamonds 
3 of Diamonds 
5 of Clubs 


**Hand 6**
Hand: 9 of Spades 
J of Hearts 
K of Diamonds 
2 of Diamonds 
4 of Clubs 


**Hand 7**
Hand: 8 of Spades 
T of Hearts 
Q of Diamonds 
A of Clubs 
3 of Clubs 


**Hand 8**
Hand: 7 of Spades 
9 of Hearts 
J of Diamonds 
K of Clubs 
2 of Clubs 


**Hand 9**
Hand: 6 of Spades 
8 of Hearts 
T of Diamonds 
Q of Clubs 


**Hand 10**
Hand: 5 of Spades 
7 of Hearts 
9 of Diamonds 
J of Clubs 


**Hand 11**
Hand: 4 of Spades 
6 of Hearts 
8 of Diamonds 
T of Clubs 

Showing hands after dealing (1 deck shuffled): 

**Hand 1**
Hand: 6 of Clubs 
A of Spades 
J of Clubs 
5 of Clubs 
K of Clubs 


**Hand 2**
Hand: 8 of Spades 
A of Hearts 
T of Spades 
K of Hearts 
4 of Hearts 


**Hand 3**
Hand: 8 of Diamonds 
7 of Hearts 
7 of Clubs 
4 of Spades 
4 of Clubs 


**Hand 4**
Hand: 3 of Hearts 
7 of Spades 
7 of Diamonds 
6 of Hearts 
9 of Diamonds 


**Hand 5**
Hand: 2 of Diamonds 
3 of Diamonds 
8 of Clubs 
Q of Spades 
Q of Clubs 


**Hand 6**
Hand: Q of Diamonds 
4 of Diamonds 
5 of Spades 
8 of Hearts 
2 of Hearts 


**Hand 7**
Hand: K of Diamonds 
K of Spades 
Q of Hearts 
5 of Hearts 
A of Clubs 


**Hand 8**
Hand: 2 of Clubs 
2 of Spades 
6 of Spades 
5 of Diamonds 
J of Diamonds 


**Hand 9**
Hand: J of Hearts 
A of Diamonds 
9 of Spades 
9 of Clubs 


**Hand 10**
Hand: 9 of Hearts 
T of Diamonds 
6 of Diamonds 
3 of Clubs 


**Hand 11**
Hand: T of Clubs 
T of Hearts 
3 of Spades 
J of Spades 

***********************/