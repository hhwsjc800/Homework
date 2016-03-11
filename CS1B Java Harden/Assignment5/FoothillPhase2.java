//LATE SUBMISSION LATE SUBMISSION LATE SUBMISSION LATE SUBMISSION
//============================================================================
// Name        : CS1B (Java) Assignment 5: GUI Cards (LATE) PHASE 2
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Feb 14, 2016
// Instructor  : Harden
// Description : This program consists of a GUI skeleton that could be used
//               for some type of card game with two players. Currently, this
//               consists of a computer player and a human player, each 
//               with a hand size of NUM_CARDS_PER_HAND. There are two mains.
//               The first is a demonstration of a GUI capacity to show a full
//               deck of cards on the screen, and the second is the
//               aforementioned GUI skeleton.

//    
//============================================================================


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

//MAIN FOR PHASE 2 MAIN FOR PHASE 2 // MAIN FOR PHASE 2 // MAIN FOR PHASE 2
public class FoothillPhase2
{
    
    static int NUM_CARDS_PER_HAND = 7;
    static int  NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
    static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
    static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
    
    public static void main(String[] args)
    {
       int k;
       Icon tempIcon;
       GUICard.loadIcons();
       // establish main frame in which program will run
       CardTable myCardTable 
          = new CardTable("CS 1B CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
       myCardTable.setSize(800, 600);
       myCardTable.setLocationRelativeTo(null);
       myCardTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


       // CREATE LABELS ----------------------------------------------------
       for (k = 0; k < NUM_CARDS_PER_HAND; k++)
       {
           tempIcon = GUICard.getIcon(generateRandomCard());
           humanLabels[k] = new JLabel(tempIcon);
           computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
           myCardTable.computerHand.add(computerLabels[k]);
           myCardTable.playerHand.add(humanLabels[k]);
       }
        
       for (k = 0; k < NUM_PLAYERS; k++)
       {
           tempIcon = GUICard.getIcon(generateRandomCard());
           playedCardLabels[k] = new JLabel(tempIcon);
           if (k > 0)
               playLabelText[k] = new JLabel("You", JLabel.CENTER);
           else
               playLabelText[k] = new JLabel("Player " + k + 1, JLabel.CENTER);
               
       }
       // ADD LABELS TO PANELS -----------------------------------------
       for (k = 0; k < NUM_CARDS_PER_HAND; k++)
       {
           myCardTable.computerHand.add(computerLabels[k]);
           myCardTable.playerHand.add(humanLabels[k]);

       }
       // and two random cards in the play region (simulating a computer/hum ply)
     //  code goes here ...
       for (k = 0 ; k < NUM_PLAYERS*2; k++)
       {
           if (k < NUM_PLAYERS)
               myCardTable.playing.add(playedCardLabels[k]);
           else
               myCardTable.playing.add(playLabelText[k - NUM_PLAYERS]);
       }
       // show everything to the user
       myCardTable.setVisible(true);
    }
    

    
    
    
    
    //generateRandomCard uses the random() function from the math library to
    //generate an int modulo 14 (cardVal) and another int modulo 4 (suitVal).
    //cardVal is then converted to a card value character ranging from 2-Joker
    //and suitVal is converted to a suit value using the enum type
    //Suit. Each of these tasks is accomplished using public static methods
    //of the GUICard class.
    public static Card generateRandomCard()
    {
        Card temp = new Card();
        int suitVal, cardVal;
        cardVal = (int) (Math.random()*1000 % 14);
        suitVal =  (int) (Math.random()*1000 % 4);
        temp.set(GUICard.turnIntIntoCardValueChar(cardVal), 
                 GUICard.turnIntIntoCardSuit(suitVal));
        
        return temp;
    }
    

}


// END MAIN FOR PHASE 2 // END MAIN FOR PHASE 2 // END MAIN FOR PHASE 2
    
class CardTable extends JFrame
{
    static int MAX_CARDS_PER_HAND = 57;
    static int MAX_PLAYERS = 2;
    static int DEFAULT_CARDS_PER_HAND = 5;
    static int DEFAULT_PLAYERS = 2;
    
    private int numCardsPerHand;
    private int numPlayers;
    
    public JPanel playerHand, computerHand, playing;
    
    //--method definitions---
    
    //--public instance methods--
    
    //Overloaded constructor for CardTable class. Takes a String, title, for
    //the title of the Jframe object, and two ints: numCardsPerHand and
    //numPlayers. Passes title into super with no filtering, then
    //checks for valid values of numCardsPerHand and numPlayers. If any of
    //these are incorrect, then the function sets the private member fields
    //with corresponding names to default values. Otherwise, they are set
    //to the passed in parameter. Adds 3 JPanels to the JFrame, each 
    //corresponding to a GUI aspect of the cardgame.
    public CardTable(String title, int numCardsPerHand, int numPlayers)
    {
        super(title);
        
        if (numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND)
            this.numCardsPerHand = DEFAULT_CARDS_PER_HAND;
        else
            this.numCardsPerHand = numCardsPerHand;
        
        if (numPlayers <= 0 || numPlayers > MAX_PLAYERS)
            this.numPlayers = DEFAULT_PLAYERS;
        else
            this.numPlayers = numPlayers;
        
        playerHand = new JPanel(new GridLayout(1,1,10,10));
        computerHand = new JPanel(new GridLayout(1,1,10,10));
        playing = new JPanel (new GridLayout (2,2,20,20));
        
        setLayout (new BorderLayout(10,10));
        add(computerHand,BorderLayout.NORTH);
        add(playing,BorderLayout.CENTER);
        add(playerHand,BorderLayout.SOUTH);
        
        computerHand.setBorder(new TitledBorder("Computer Hand"));
        playing.setBorder(new TitledBorder("Playing"));
        playerHand.setBorder(new TitledBorder("Player Hand"));
    }
    
    
    
    
    
    
    
    
    
    //Basic accessor for numPlayers. Returns numPlayers private member data
    //as an int
    public int getNumPlayers()
    {
        return numPlayers;
    }
    
    
    
    
    
    
    
    
    
    //Basic accessor functionality for private member numCardsPerHand. Returns
    //the private member data as an int.
    public int getNumCardsPerHand()
    {
        return numCardsPerHand;
    }
}

class GUICard
{
    public static final int NUM_SUITS = 4;
    public static final int MAX_CARDVAL = 14;
    // 14 = A thru K (+ joker optional)
    private static Icon[][] iconCards = new ImageIcon[MAX_CARDVAL][NUM_SUITS]; 
    private static Icon iconBack;
    private static boolean iconsLoaded = false;
    
    private static String cardlValsConvertAssist = "23456789TJQKAX";
    private static String suitValsConvertAssist  = "CDHS";
    private static Card.Suit suitConvertAssist[] =
    {
       Card.Suit.CLUBS,
       Card.Suit.DIAMONDS,
       Card.Suit.HEARTS,
       Card.Suit.SPADES
    };
    
    //---static methods
    
    //loadIcons has the same functionality as the loadIcons function provided
    //in the assignment. However, this loadIcons uses a 2d array to categorize
    //the cards by suit and value, so that when accessed by other functions
    //the index of the card serves as an indicator of its properties.
    public static void loadIcons()
    {
        if (iconsLoaded)
            return;
        
        String imageFileName;
        int intSuit, intVal;

        for (intSuit = 0; intSuit < NUM_SUITS; intSuit++) {
           for (intVal = 0; intVal < MAX_CARDVAL; intVal++ )
           {
              imageFileName = "images/"
                    + turnIntIntoCardValueChar(intVal) 
                    + turnIntIntoCardSuitChar(intSuit)
                    + ".gif";
              iconCards[intVal][intSuit] = new ImageIcon(imageFileName);
           }
        imageFileName = "images/BK.gif";
        iconCards[MAX_CARDVAL-1][NUM_SUITS-1] = new ImageIcon(imageFileName);
        }
        iconBack = iconCards[MAX_CARDVAL-1][NUM_SUITS-1];
        iconsLoaded = true;
        
    }
    
    //Takes an int, k , then returns its value as a coresponding int using
    //the cardlValsConvertAssist string as an index reference.
    public static char turnIntIntoCardValueChar(int k)
    {
    
       if ( k < 0 || k > 13)
          return '?'; 
       return cardlValsConvertAssist.charAt(k);
    }
    
    
    
    
    
    
    
    
    // Takes an int, k, then uses the suitValsConvertAssist as a reference
    //and returns the index of that string correspoinding to the suit
    //passed in.
    public static char turnIntIntoCardSuitChar(int k)
    {
       if ( k < 0 || k > 3)
          return '?'; 
       return suitValsConvertAssist.charAt(k);
    }
    
    
    
    // Takes an int, k, then uses suitConvertAssist[] as a reference
    //and returns the suit of the array corresponding to the index k
    //passed in.
    public static Card.Suit turnIntIntoCardSuit(int k)
    {
        if (k < 0 || k > 3)
            return Card.Suit.CLUBS;
        return suitConvertAssist[k];
    }
    
    
    
    //getIcon returns the Icon of the card in iconCards[][] corresponding to
    //the value of the Card card passed in. Uses two helper functions to
    //decode the card parameter into its constituents so that getIcon
    //can access the correct index of the array.
    public static Icon getIcon(Card card)
    {
       loadIcons(); // will not load twice, so no worries.
       return iconCards[valueAsInt(card)][suitAsInt(card)];
    }
    
    
    
    
    //valueAsInt returns an int corresponding to the value of the passed
    //Card parameter, card. Ranges from 2-14 (corresponding to 2-Joker).
    //Uses suitConverAssist to find the index corresponding
    //to the card val.
    public static int valueAsInt(Card card)
    {
        return cardlValsConvertAssist.indexOf(card.getValue());
    }
    
    
    
    
    
    //suitAsInt returns the value of the passed Card parameter, card, as an int
    //by using the indices of the suitConvertAssit as values to 
    //correspond with the Suit char of card.
    public static int suitAsInt(Card card)
    {
        for (int i = 0; i < NUM_SUITS; i++) 
        {
            if (card.getSuit() == suitConvertAssist[i])
                return i;
        }
        
        return 0;
    }
    
    
    
    
    
    
    //getBackCardIcon simply returns the last index of the iconCards array as 
    //an icon
    public static Icon getBackCardIcon()
    {
        return iconBack;
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
