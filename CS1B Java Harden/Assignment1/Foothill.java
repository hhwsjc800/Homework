//============================================================================
// Name        : CS1B (Java) Assignment 1: Card Class, Hand Class
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Jan 11, 2016
// Instructor  : Harden
// Description : This program consists of two classes: a Card class and 
//               a Hand class. Card objects simulate regular playing cards, 
//               while Hand objects are meant to simulate hands of playing
//               cards. Code in main is designed to test the functionality
//               of the class implementation. Main instantiates a few Card
//               objects and one Hand object, then goes through a series of
//               tests of the various member functions of each class.
//============================================================================

public class Foothill
{                          
    public static void main(String[] args)
    {                                 
        Card card1, card2, card3, card4, card5, card6;
        card1 = new Card('T', Card.Suit.SPADES);
        card2 = new Card('X', Card.Suit.DIAMONDS);
        card3 = new Card('1', Card.Suit.HEARTS);
        card4 = new Card();
        card5 = new Card('Q', Card.Suit.CLUBS);
        card6 = new Card('9', Card.Suit.DIAMONDS);

        Hand myHand = new Hand();
      
        //-----PHASE 1 CODE----------
        System.out.println("========PHASE 1: CARD CLASS=======\n");
        //Equals function test
        if (card1.equals(card3))
           System.out.println( "they were equal");
        else
           System.out.println("they were different \n");
      
        //Card class toString test
        System.out.println("----initial vals----");
        System.out.print(card1.toString() + "\n" + card2.toString() + "\n" + 
                         card3.toString() + "\n" + card4.toString() + "\n" + 
                         card5.toString() + "\n" + card6.toString() + "\n\n");
      
        //Card class set test, errorFlag test
        System.out.println("card 3 after setting to value Y, suit diamonds: " );
        card3.set('Y', Card.Suit.DIAMONDS);
        System.out.println(card3.toString() + "\n\n");

        System.out.println("card 3 after setting to 7 clubs:");
        card3.set('7', Card.Suit.CLUBS);
        System.out.println(card3.toString() + "\n");
      
        //------PHASE 2 CODE--------
        System.out.println("========PHASE 2: HAND CLASS=======\n");
        //Testing takeCard and toString functionality of Hand class
        for (; myHand.getNumCards() < Hand.MAX_CARDS;)
        {              
            myHand.takeCard(card5);
            myHand.takeCard(card3);
            myHand.takeCard(card4);
            myHand.takeCard(card2);
            myHand.takeCard(card6);
        }
        System.out.println(myHand.toString());
      
        //Testing inspectCard functionality of Hand class
        System.out.println("testing inspectCard: ");  
        System.out.println( myHand.inspectCard(5).toString() + "\n" 
          + myHand.inspectCard(-1).toString() +"\n"
          + myHand.inspectCard(52).toString() +"\n"
          + myHand.inspectCard(51).toString() +"\n");

        //Testing playCard functionality of Hand class
        System.out.println("dealing:");
        for (; myHand.getNumCards() > 0;)
           System.out.println(myHand.playCard().toString());
     
        System.out.println("\nnum cards in hand: " + myHand.getNumCards());
      
        //Testing resetHand, empty hand
        myHand.resetHand();
        System.out.println(myHand.toString());
        return; 
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
   
   
   
   
   
   
    //Overloaded ctor for card class calls set function. Passing inval parameters
    //leaves the private member data unchanged except for errorFlag
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
    //an invalid string. Otherwise, returns the card value and suit as string
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

/************RUN OF ASSIGNMENT 1***********************

========PHASE 1: CARD CLASS=======

they were different 

----initial vals----
T of Spades
[invalid]
[invalid]
A of Spades
Q of Clubs
9 of Diamonds

card 3 after setting to value Y, suit diamonds: 
[invalid]


card 3 after setting to 7 clubs:
7 of Clubs

========PHASE 2: HAND CLASS=======

Hand: Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 
Q of Clubs 
7 of Clubs 
A of Spades 
9 of Diamonds 

testing inspectCard: 
7 of Clubs
[invalid]
[invalid]
9 of Diamonds

dealing:
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs
9 of Diamonds
A of Spades
7 of Clubs
Q of Clubs

num cards in hand: 0
Hand: 


***********************/