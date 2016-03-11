#include <iostream>
#include <string>
#include <time.h>
#include <cstdlib>

using namespace std;

//Class declarations
//Card class simulates playing cards
class Card
{
public:
   enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES};

   //mutators
   Card (char value, Suit suit);
   bool set(char value, Suit suit);

   //non mutating public method functions (accessors included)
   string toString() const;
   Suit getSuit() const {return suit;}
   char getValue() const {return value;}
   bool getErrorFlag() const {return errorFlag;}
   bool equals(Card card) const;

private:
   char value;
   Suit suit;
   bool errorFlag;
   static bool isValid(char value, Suit suit);
};

//Hand class simulates a 'hand' of cards. Consists of an array of card
//elements and supports adding and 'dealing' cards to/from the hand.
class Hand
{
public:
   static const int MAX_CARDS = 52;
   
   //constructors
   Hand();

   //mutators
   void resetHand();
   bool takeCard(Card card);
   Card playCard();

   //accessors and non-mutating methods
   string toString() const;
   int getNumCards() const { return numCards; }
   Card inspectCard(int k) const;

private:
   Card myCards[MAX_CARDS];
   int numCards;
};

class Deck
{
public:
   Deck(int numPacks);
   bool init(int numPacks);
   void shuffle();
   Card dealCard();
   int getTopCard() const { return topCard; }
   Card inspectCard(int k) const;

private:
   static const int MAX_PACKS = 6;
   static const int NUM_CARDS_PER_PACK = 52;
   static const int MAX_CARDS_PER_DECK = MAX_PACKS * NUM_CARDS_PER_PACK;

   static Card masterPack[NUM_CARDS_PER_PACK];
   static bool masterPackInit;
   
   Card cards[MAX_CARDS_PER_DECK];
   int topCard;
   int numPacks;
   
   static void allocateMasterPack();
};

////////////////////////////////////////
//                                    //
//     CARD CLASS DEFINITIONS         //
//                                    //
///////////////////////////////////////

//Overloaded ctor
//Default values are set to spec. Constructor calls set function to initialize
//valid overloaded params
Card::Card(char value = 'A', Suit suit = Card::SPADES)
{
   set(value, suit);
}

//Set passes card value and suit to isValid. returns FALSE if value is
//invalid and sets errorFlag to true, leaving other private member data
//untouched. if value passed is valid, errorFlag is true and private
//fields are set to passed values.
bool Card::set(char value, Suit suit)
{
   if (isValid(value, suit)) 
   {
      errorFlag = false;
      this->value = value;
      this->suit = suit;
      return true;
   }

   else 
   {
      errorFlag = true;
      return false;
   }

}

//isValid checks for validity of value only. returns TRUE for valid card
//characters: 2-9, T, J-A 
bool Card::isValid(char value, Suit suit)
{
   if (!isalnum(value))
      return false;

   value = toupper(value);
   if (value != 'A' && value != 'J' && value != 'Q' && value != 'K'
       && value != 'T' && (value > '9' || value < '2'))
      return false;
   return true;
}


//returns "[invalid]" string if the card is not valid. otherwise returns
//VALUE of SUIT
string Card::toString() const
{
   if (errorFlag)
      return "[invalid]";

   string ret = string(1, value) + " of ";
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

//checks if two cards are equal in all private member fields.
//returns TRUE if equal, FALSE otherwise
bool Card::equals(Card card) const
{
   if (this->suit == card.suit
      && this->errorFlag == card.errorFlag
      && this->value == card.value)
      return true;
   
   return false;
}

/////////////////////////////////////////////
//                                         //
//       HAND CLASS DEFINITIONS            //
//                                         //
/////////////////////////////////////////////

//default ctor
Hand::Hand()
{
   numCards = 0;
   for (int k = 0; k < MAX_CARDS; k++)
      myCards[k] = Card();
}

//resets hand by setting numcards to 0. Does not touch contents of myCards
//array
void Hand::resetHand()
{
   numCards = 0;
}

//takeCard provides functionality of adding cards to the hand. Returns TRUE
//if there is space for a card. Returns FALSE otherwise. Return value is 
//contingent upon card's validity, NOT if it was or wasn't added to the hand.
//Adds card to hand (myCards array) only if the card is a valid card AND 
//there is space in the hand.
bool Hand::takeCard(Card card) 
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

//Returns the 'top' card from the myHand array. Analogue to 'popping' off of
//a stack. Returns generic invalid card if there are no cards to play.
Card Hand::playCard()
{
   //return invalid card if not possible to play card
   if (numCards <= 0)
     return Card('X', Card::CLUBS);

   //else 'pop' a card from the array
   Card temp = myCards[numCards - 1];
   numCards--;
   return temp;
}

//toString() functionality
string Hand::toString() const
{
   string handStr = "Hand: ";

   for (int k = 0; k < numCards; k++)
      handStr += myCards[k].toString() + " \n";
   return handStr;
}

//Returns the card at the index of the array, provided the index is 
//valid. Valid indices depend on the value of numCards.
Card Hand::inspectCard(int k) const
{
   if (k < 0 || k > numCards || k >= MAX_CARDS || numCards <= 0)
      return Card('X', Card::SPADES);

   return myCards[k];
}

////////////////////////////////////////
//                                    //
//     DECK CLASS DEFINITIONS         //
//                                    //
///////////////////////////////////////

//---static members and member functions-----
bool Deck::masterPackInit = false;

Card Deck::masterPack[NUM_CARDS_PER_PACK];

//Heavily inspired by the deck populator found in the modules.
void Deck::allocateMasterPack()
{  
   char charVal = '2';
   Card::Suit suitVal = static_cast<Card::Suit>(0);

   //initializes deck by suit 
   for (int i = 0; i < 4; i++)
   {    
      charVal = '2';
      suitVal = static_cast<Card::Suit>(i);
      //set 2-9 values for given suit
      for (int k = 0; k < 8; k++)
      {  
         masterPack[(13 * i) + k] = Card(charVal, suitVal);
         charVal++;
      }
            
      //set T-A values for given suit
      masterPack[(13*i) + 8] = Card('T', suitVal);
      masterPack[(13*i) + 9] = Card('J', suitVal);
      masterPack[(13*i) + 10] = Card('Q', suitVal);
      masterPack[(13*i) + 11] = Card('K', suitVal);
      masterPack[(13*i) + 12] = Card('A', suitVal);         
   }    
      masterPackInit = true;
}

//--public member functions---

//Constructor populates the arrays and private field members of Deck class. 
//Default number of packs is 1. Checks for bad values of numPacks. 
Deck::Deck(int numPacks = 1)
{
   //masterPack only initialized once
   if (masterPackInit == false)
      allocateMasterPack();

   if (!init(numPacks))
      topCard = 0;
}

//Returns FALSE if numPacks is invalid. Otherwise, updates numCards
//and initializes the correct amount of cards in the cards[] array
//then returns TRUE.
bool Deck::init(int numPacks = 1)
{
   if (numPacks <= 0 || numPacks > MAX_PACKS)
      return false;

   topCard = numPacks * NUM_CARDS_PER_PACK;

   for (int k = 0; k < numPacks; k++)
   {
      for (int i = 0; i < NUM_CARDS_PER_PACK; i++)
      {
         cards[i + (52 * k)] = masterPack[i];
      }
   }
   return true;
}

//Shuffles cards in the deck using rand.
void Deck::shuffle()
{
   srand (time(NULL));
   Card temp = Card('A', Card::SPADES);
   int randNum = rand() % topCard;

   for (int i = 0; i < topCard; i++)
   {
      randNum = rand() % topCard; //number between 0 and topCard - 1
      temp = cards[i];
      cards[i] = cards[randNum];
      cards[randNum] = temp;
   }

}

//Returns the top card of the deck if there is a card to deal. Otherwise,
//returns an invalid card. 'Pop' function of the cards member.
Card Deck::dealCard()
{
   if (topCard <= 0)
      return Card('X', Card::SPADES);

   Card temp = cards[topCard - 1];
   topCard--;

   return temp;
}

//Returns a card from the cards[] array if the index is valid.
Card Deck::inspectCard(int k) const
{
   if (k < 0 || (k > topCard - 1) || k >= MAX_CARDS_PER_DECK)
      return Card('X', Card::SPADES);

   return cards[k];
}




int main()
{
   //======PHASE 1: DECK CLASS TESTING
   cout << "=====PHASE 1: DECK CLASS=====" << endl;

   //Deck object for phase 1, int object to count numcards in deck
   Deck myDeck(2);
   int numCards = myDeck.getTopCard();

   cout << "==Unshuffled two decks: " << endl;
   for (int i = 0; i < numCards; i++)
      cout << myDeck.dealCard().toString() << endl;

   myDeck.init(2);
   numCards = myDeck.getTopCard();
   myDeck.shuffle();

   cout << "\n\n==Shuffled two decks: " << endl;
   for (int i = 0; i < numCards; i++)
      cout << myDeck.dealCard().toString() << endl;

   myDeck.init(1);
   numCards = myDeck.getTopCard();

   cout << "\n\n==Unshuffled one deck: " << endl;
   for (int i = 0; i < numCards; i++)
      cout << myDeck.dealCard().toString() << endl;

   myDeck.init(1);
   numCards = myDeck.getTopCard();
   myDeck.shuffle();

   cout << "\n\n==Shuffled one deck: " << endl;
   for (int i = 0; i < numCards; i++)
      cout << myDeck.dealCard().toString() << endl;
 

   cout << "========END PHASE 1======= " << endl << endl << endl;

   //======PHASE 2: DECK AND HAND CLASS INTERACTION
   cout << "=====PHASE 2: DECK AND HAND CLASS=====" << endl << endl;
   int numPlayers = 0;
   const int MIN_PLAYERS = 1;
   const int MAX_PLAYERS = 10;

   while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS)
   {
      cout << "Please enter a valid number of players(" << MIN_PLAYERS 
           << "-" << MAX_PLAYERS << "): ";
      cin >> numPlayers;
   }

   Deck playDeck(1);
   numCards = playDeck.getTopCard();
   Hand players[MAX_PLAYERS];

   //Dealing deck to players
   for (int i = 0; i < numCards; i++)
      players[i % numPlayers].takeCard(playDeck.dealCard());
 
   cout << "\nShowing hands after dealing (1 Deck, unshuffled): ";
   for (int k = 0; k < numPlayers; k++)
      cout << "\n**Hand " << (k + 1) << "**\n" << players[k].toString();

   //Resetting hands and deck
   for (int i = 0; i < numPlayers; i++)
      players[i].resetHand();
   playDeck.init(1);
   playDeck.shuffle();
   numCards = playDeck.getTopCard();

   //Dealing deck to players
   for (int i = 0; i < numCards; i++)
      players[i % numPlayers].takeCard(playDeck.dealCard());
 
   cout << "\nShowing hands after dealing (1 Deck, shuffled): ";
   for (int k = 0; k < numPlayers; k++)
      cout << "\n**Hand " << (k + 1) << "**\n" << players[k].toString();

   //for console hang
	int foo;
	cin >> foo;
	return 0;
}
/*
=====ASSIGNMENT 2 RUN============

=====PHASE 1: DECK CLASS=====
==Unshuffled two decks: 
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


==Shuffled two decks: 
6 of Diamonds
J of Diamonds
4 of Hearts
2 of Spades
K of Diamonds
5 of Spades
7 of Spades
Q of Hearts
9 of Spades
J of Diamonds
Q of Hearts
T of Diamonds
7 of Spades
K of Diamonds
Q of Spades
4 of Diamonds
6 of Hearts
5 of Hearts
6 of Hearts
J of Spades
2 of Hearts
T of Spades
7 of Diamonds
K of Hearts
8 of Spades
T of Spades
8 of Hearts
4 of Diamonds
K of Spades
5 of Spades
8 of Spades
Q of Diamonds
5 of Hearts
7 of Hearts
7 of Clubs
J of Hearts
A of Clubs
A of Hearts
3 of Spades
9 of Diamonds
3 of Diamonds
A of Diamonds
7 of Diamonds
T of Diamonds
3 of Hearts
9 of Diamonds
2 of Diamonds
3 of Hearts
5 of Clubs
Q of Diamonds
2 of Clubs
8 of Diamonds
Q of Spades
4 of Hearts
4 of Spades
3 of Clubs
8 of Clubs
Q of Clubs
8 of Clubs
5 of Diamonds
7 of Hearts
8 of Hearts
4 of Clubs
A of Spades
4 of Clubs
6 of Clubs
6 of Clubs
9 of Clubs
K of Hearts
J of Clubs
3 of Spades
J of Spades
2 of Spades
9 of Spades
6 of Spades
T of Hearts
K of Spades
J of Clubs
5 of Clubs
9 of Hearts
K of Clubs
Q of Clubs
5 of Diamonds
6 of Spades
A of Diamonds
A of Spades
A of Hearts
T of Hearts
8 of Diamonds
2 of Diamonds
T of Clubs
T of Clubs
A of Clubs
K of Clubs
2 of Hearts
2 of Clubs
3 of Clubs
J of Hearts
9 of Hearts
6 of Diamonds
9 of Clubs
4 of Spades
3 of Diamonds
7 of Clubs


==Unshuffled one deck: 
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


==Shuffled one deck: 
Q of Spades
7 of Hearts
5 of Clubs
3 of Diamonds
J of Hearts
K of Hearts
6 of Hearts
9 of Spades
4 of Clubs
8 of Clubs
4 of Hearts
7 of Diamonds
J of Diamonds
2 of Spades
6 of Clubs
9 of Clubs
4 of Diamonds
J of Clubs
3 of Spades
J of Spades
Q of Hearts
5 of Spades
9 of Diamonds
2 of Diamonds
K of Spades
K of Diamonds
5 of Hearts
2 of Clubs
A of Clubs
8 of Diamonds
3 of Clubs
6 of Spades
A of Diamonds
A of Spades
A of Hearts
T of Hearts
6 of Diamonds
5 of Diamonds
T of Diamonds
T of Clubs
Q of Diamonds
K of Clubs
2 of Hearts
7 of Clubs
7 of Spades
T of Spades
9 of Hearts
8 of Hearts
3 of Hearts
4 of Spades
8 of Spades
Q of Clubs
========END PHASE 1======= 


=====PHASE 2: DECK AND HAND CLASS=====

Please enter a valid number of players(1-10): -3
Please enter a valid number of players(1-10): 100
Please enter a valid number of players(1-10): 9

Showing hands after dealing (1 Deck, unshuffled): 
**Hand 1**
Hand: A of Spades 
5 of Spades 
9 of Hearts 
K of Diamonds 
4 of Diamonds 
8 of Clubs 

**Hand 2**
Hand: K of Spades 
4 of Spades 
8 of Hearts 
Q of Diamonds 
3 of Diamonds 
7 of Clubs 

**Hand 3**
Hand: Q of Spades 
3 of Spades 
7 of Hearts 
J of Diamonds 
2 of Diamonds 
6 of Clubs 

**Hand 4**
Hand: J of Spades 
2 of Spades 
6 of Hearts 
T of Diamonds 
A of Clubs 
5 of Clubs 

**Hand 5**
Hand: T of Spades 
A of Hearts 
5 of Hearts 
9 of Diamonds 
K of Clubs 
4 of Clubs 

**Hand 6**
Hand: 9 of Spades 
K of Hearts 
4 of Hearts 
8 of Diamonds 
Q of Clubs 
3 of Clubs 

**Hand 7**
Hand: 8 of Spades 
Q of Hearts 
3 of Hearts 
7 of Diamonds 
J of Clubs 
2 of Clubs 

**Hand 8**
Hand: 7 of Spades 
J of Hearts 
2 of Hearts 
6 of Diamonds 
T of Clubs 

**Hand 9**
Hand: 6 of Spades 
T of Hearts 
A of Diamonds 
5 of Diamonds 
9 of Clubs 

Showing hands after dealing (1 Deck, shuffled): 
**Hand 1**
Hand: K of Hearts 
T of Clubs 
2 of Hearts 
2 of Clubs 
J of Clubs 
7 of Clubs 

**Hand 2**
Hand: 8 of Clubs 
3 of Clubs 
7 of Hearts 
4 of Hearts 
3 of Spades 
2 of Diamonds 

**Hand 3**
Hand: K of Diamonds 
9 of Spades 
3 of Diamonds 
T of Spades 
2 of Spades 
Q of Hearts 

**Hand 4**
Hand: 5 of Diamonds 
K of Clubs 
9 of Hearts 
Q of Clubs 
A of Spades 
6 of Spades 

**Hand 5**
Hand: Q of Diamonds 
9 of Clubs 
4 of Spades 
J of Diamonds 
T of Hearts 
K of Spades 

**Hand 6**
Hand: 6 of Clubs 
8 of Spades 
6 of Diamonds 
5 of Clubs 
A of Hearts 
T of Diamonds 

**Hand 7**
Hand: J of Hearts 
8 of Diamonds 
J of Spades 
9 of Diamonds 
5 of Spades 
7 of Spades 

**Hand 8**
Hand: 7 of Diamonds 
4 of Clubs 
4 of Diamonds 
6 of Hearts 
3 of Hearts 

**Hand 9**
Hand: A of Clubs 
8 of Hearts 
5 of Hearts 
A of Diamonds 
Q of Spades 
*/