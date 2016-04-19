//============================================================================
// Name        : CS1B (Java) Assignment 10: Extra Credit
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : March 14, 2016
// Instructor  : Harden
// Description : This program consists of the classes of Assignment 1 with the
//               addition of global classes provided by the spec. These global
//               classes, insert, remove, and removeAll, interact with 
//               Card Linked List's instantiated from the Linked List Java
//               template class. Main in this program is used to demonstrate
//               the functionality of these added features as well as the
//               functionality of a user-defined showList method.
//============================================================================

import java.util.*;

public class Foothill
{               
    public static void main(String[] args)
    {
        int k;
        final int numCards = 10;
        int firstCard = 0, lastCard = numCards - 1;
        LinkedList<Card> myList = new LinkedList<Card>();
        Card cardArray[] = new Card[numCards];

        
        for (k = 0; k < numCards; k++)
        {
           cardArray[k] = generateRandomCard(); //instantiate rand card

           //double insert into card array
           insert(myList, cardArray[k]); 
           insert(myList, cardArray[k]);
        }

        System.out.print("***Initial List***");
        showList(myList);

        for (k = 0; k < numCards/2; k++)
        {
           while (remove(myList, cardArray[k]))
           {
              //loops until removes all copies of cardArray[k]
           }
        }

        System.out.print("***Roughly Half Cards Removed List***");
        showList(myList);

        if (removeAll(myList, cardArray[firstCard]))
           System.out.print("Removed all instances of " + 
                         cardArray[firstCard].toString() + " in the list.\n");
        else
           System.out.print("Couldn't find " + cardArray[firstCard].toString() 
                + " in the list! (expected)\n");

        if (removeAll(myList, cardArray[lastCard]))
           System.out.print("Removed all instances of " 
           + cardArray[lastCard].toString() + " in the list (expected) \n\n");
        else
           System.out.print("Failed to find " + cardArray[lastCard].toString() 
                + " in the list (happens rarely due to randomness)\n\n");
        
        //Explanation: sometimes in the initial removal we can have many
        //more cards than expected removed. For this main, we are using
        //numCards - 1 as our index for the last card. If, by chance,
        //cardArray[lastCard] is identical to a card in the first half of the 
        //cardArray (say cardArray[0] and cardArray[lastCard] are the same), 
        //then the first removal of cards would remove cardArray[lastCard] from
        //the list but obviously not from the array. Hence upon attempting
        //to removeAll for the last card of the array (which is what happens 
        //here), we can sometimes fail to find it.
        
        //In the expected case, the cards in the last position of the array
        //is distinct from the cards in the first half, so it is not
        //removed upon the first removal.

        System.out.print("***Final List***");
        showList(myList);

        return;

       
    }

    // "global" static Foothill methods 
    static Card generateRandomCard()
    {
       // if firstTime = true, use clock to seed, else fixed seed for debugging
       Card.Suit suit;
       char val;

       int suitSelector, valSelector;

       // get random suit and value
       suitSelector = (int) (Math.random() * 4);
       valSelector = (int) (Math.random() * 13);

       // pick suit
       suit = turnIntIntoSuit(suitSelector);
       val = turnIntIntoVal(valSelector);

       return new Card(val, suit);
    }

    // note:  this method not needed if we use int for suits instead of enum
    static Card.Suit turnIntIntoSuit(int k)
    {
       return Card.Suit.values()[k];  // 
    }

    static char turnIntIntoVal(int k)
    {
       String legalVals = "23456789TJQKA";
       
       if (k < 0 | k >= legalVals.length())
          return '?';
       return legalVals.charAt(k);
    }
    
    








    //showList is a static void function that takes a LinkedList<Card> myList
    //as its single parameter. By use of a locally defined Card Linked List
    //iterator, it iterates through the items of myList and outputs 
    //each Card element to the console by an implicit call to the Card
    //class toString method.
    static void showList(LinkedList<Card> myList)
    {
        ListIterator<Card> iter;
        
        System.out.println( "\n\n_____Here's the List_______\n" );
        for (iter = myList.listIterator(); iter.hasNext(); )
           System.out.println( "[" + iter.next() + "] ");
        System.out.println( "\n\n_____That's all!_______\n" );
    }
    








    //insert takes a LinkedList<Card> myList and a Card x as a parameter.
    //It iterates through the list and compares x to each element of the 
    //card list. If it finds a Card in myList that is bigger than 
    //x, it sets the iterator to the element immediately preceding x 
    //and adds x to myList at the position of the iterator.
    static void insert(LinkedList<Card> myList, Card x)
    {
       ListIterator<Card> iter;
       Card listX;

       for (iter = myList.listIterator(); iter.hasNext(); )
       {
         listX = iter.next();
         if ( x.compareTo(listX) < 0 )
         {
            iter.previous(); // back up one
            break;
         }
       }
       iter.add(x);
    }









    //remove is a boolean function that takes in two parameters: a 
    //LinkedList<Card> myList, and a Card x. It iterates through myList
    //using an iterator, iter. Upon the first occurrence of x in myList,
    //it removes whatever element of myList that had the same value of
    //x when compared using the Card class compareTo method. If it finds
    //a Card in myList this way, the function returns TRUE. Otherwise,
    //if no instance of x is found in myList, returns FALSE.
    static boolean remove(LinkedList<Card> myList, Card x)
    {
       ListIterator<Card> iter;
       Card listX;

       for (iter = myList.listIterator(); iter.hasNext(); )
       {
           listX = iter.next();
           if (x.compareTo(listX) == 0)
          {
             iter.remove();
             return true;   // we found, we removed, we return
          }
       }
       return false;
    }
    








    //removeAll is a boolean function that takes in two parameters: a 
    //LinkedList<Card> myList and a Card x. It instantiates a local boolean
    //variable, removal, as FALSE. Afterwards, removeAll makes continuous calls
    //to the remove function on myList using myList and x, respectively,
    //as parameters. If remove is ever successfully called, then the removal
    //boolean value is set to TRUE. If remove fails a call, then the removal
    //boolean value should be set to FALSE. Returns the value of removal.
    static boolean removeAll(LinkedList<Card> myList, Card x)
    {
        boolean removal = false;
        while ( remove(myList, x) )
            removal = true;
        
        return removal;
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
    
    protected static char[] valueRanks = { '2', '3', '4', '5', '6', '7', '8', 
            '9', 'T', 'J', 'Q', 'K', 'A'};
    protected static Suit[] suitRanks = {Suit.CLUBS, Suit.DIAMONDS, 
            Suit.HEARTS, Suit.SPADES};
    protected static final int NUM_VALS = 13; 
    

    // sort member methods
    public int compareTo(Card other)
    {
       if (this.value == other.value)
          return ( getSuitRank(this.suit) - getSuitRank(other.suit) );

       return ( 
             getValueRank(this.value) 
             - getValueRank(other.value) 
             );
    }

    public static int getSuitRank(Suit st)
    {
       int k;

       for (k = 0; k < 4; k++) 
          if (suitRanks[k] == st)
             return k;

       // should not happen
       return 0;
    }

    public  static int getValueRank(char val)
    {
       int k;

       for (k = 0; k < NUM_VALS; k++) 
          if (valueRanks[k] == val)
             return k;

       // should not happen
       return 0;
    }
    
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
/************RUN OF ASSIGNMENT 10***********************

***Initial List***

_____Here's the List_______

[2 of Spades] 
[2 of Spades] 
[5 of Hearts] 
[5 of Hearts] 
[6 of Diamonds] 
[6 of Diamonds] 
[6 of Hearts] 
[6 of Hearts] 
[9 of Clubs] 
[9 of Clubs] 
[Q of Clubs] 
[Q of Clubs] 
[Q of Hearts] 
[Q of Hearts] 
[Q of Spades] 
[Q of Spades] 
[K of Spades] 
[K of Spades] 
[A of Hearts] 
[A of Hearts] 


_____That's all!_______

***Roughly Half Cards Removed List***

_____Here's the List_______

[5 of Hearts] 
[5 of Hearts] 
[6 of Diamonds] 
[6 of Diamonds] 
[6 of Hearts] 
[6 of Hearts] 
[9 of Clubs] 
[9 of Clubs] 
[A of Hearts] 
[A of Hearts] 


_____That's all!_______

Couldn't find Q of Clubs in the list! (expected)
Removed all instances of 6 of Diamonds in the list (expected) 

***Final List***

_____Here's the List_______

[5 of Hearts] 
[5 of Hearts] 
[6 of Hearts] 
[6 of Hearts] 
[9 of Clubs] 
[9 of Clubs] 
[A of Hearts] 
[A of Hearts] 


_____That's all!_______


***********************/