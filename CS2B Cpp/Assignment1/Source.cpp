#include <iostream>
#include <string>
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

int main()
{
   Card  card1('T', Card::SPADES), card2('X', Card::CLUBS), 
      card3('J', Card::SPADES), card4('5', Card::DIAMONDS), 
      card5('A', Card::HEARTS), card6('3', Card::DIAMONDS);

   Hand myHand;

   //======PHASE 1: CARD CLASS TESTING==================
   cout << "=====PHASE 1: CARD CLASS TESTING=====" << endl;

   //Testing toString() functionality for valid and invalid cards
   cout << "--Initial Card objects---" << endl
        << "Card 1: :" << card1.toString() << endl 
        << "Card 2: :" << card2.toString() << endl
        << "Card 3: :" << card3.toString() << endl
        << "Card 4: :" << card4.toString() << endl
        << "Card 5: :" << card5.toString() << endl
        << "Card 6: :" << card6.toString() << endl << endl;

   //Test of Card::equals()
   if (card1.equals(card3))
      cout << "Cards were equal\n\n";
   else
      cout << "Cards were not equal\n\n";


   //Testing set() functionality, making a 'good' card 'bad' then turning it
   //'good' again. Display changes to console.
   cout << "card 3 after setting to value Y, suit diamonds: " << endl;
   card3.set('Y', Card::DIAMONDS);
   cout << card3.toString() << endl << endl;

   cout << "card 3 after setting to 7 clubs" << endl;
   card3.set('7', Card::CLUBS);
   cout << card3.toString() << endl << endl;
   
   cout << "========END PHASE 1======= " << endl;

   //=======PHASE 2: HAND CLASS PUBLIC METHOD TESTS==========
   cout << "=====PHASE 2: HAND CLASS TESTING=====" << endl;

   //Test of Hand::getNumcards() and Hand::takeCard() functionality.
   //Gather a full hand then display the entire hand and number of cards in
   //hand using toString() and numCards().
   for (; myHand.getNumCards() < Hand::MAX_CARDS;)
   {
      myHand.takeCard(card1);
      myHand.takeCard(card2);
      myHand.takeCard(card4);
      myHand.takeCard(card6);
   }
   cout << myHand.toString() << endl << endl;
   cout << myHand.getNumCards() << " is num cards in hand" << endl <<endl;

   //Testing inspectCard functionality of Hand class with both valid and 
   //invalid indices.
   cout << "inspectCard(3): " << myHand.inspectCard(3).toString()   << endl
      << "inspectCard(1): "   << myHand.inspectCard(1).toString()   << endl
      << "inspectCard(-10): " << myHand.inspectCard(-10).toString() << endl
      << "inspectCard(52): "  << myHand.inspectCard(52).toString()  << endl
      << "inspectCard(51): "  << myHand.inspectCard(51).toString()  << "\n\n";

   //Test of Hand::playCard() functionality. Playing all cards in the hand 
   //(displaying each card played as a string) then outputting contents of 
   //hand and number of cards in hand to console.
   cout << "Emptying hand: " << endl;
   for (; myHand.getNumCards() > 0;)
      cout << myHand.playCard().toString() << endl;

   cout << myHand.getNumCards() << " is num cards in hand" << endl
        << myHand.toString() << endl << endl;

   //Testing Hand::resetHand() functionality by loading an arbitrary number
   //of cards into hand, then resetting hand and displaying contents.
   cout << "Loading cards in hand, then emptying : \n";
   for (; myHand.getNumCards() < Hand::MAX_CARDS / 2; )
      myHand.takeCard(card6);
   myHand.resetHand();
   cout << "inspectCard(5): " << myHand.inspectCard(5).toString() << endl
        << "insepctCard(0): " << myHand.inspectCard(0).toString() << endl
        << myHand.toString()  << endl;

	cout << "\nfinished";
   
   //for console hang
	int foo;
	cin >> foo;
	return 0;
}
/*
=====ASSIGNMENT 1 RUN============

== == = PHASE 1: CARD CLASS TESTING == == =
--Initial Card objects-- -
Card 1 :  T of Spades
Card 2 :  [invalid]
Card 3 :  J of Spades
Card 4 :  5 of Diamonds
Card 5 :  A of Hearts
Card 6 :  3 of Diamonds

Cards were not equal

card 3 after setting to value Y, suit diamonds :
[invalid]

card 3 after setting to 7 clubs
7 of Clubs

== == == == END PHASE 1 == == == =
== == = PHASE 2: HAND CLASS TESTING == == =
Hand : T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades
   5 of Diamonds
   3 of Diamonds
   T of Spades


   52 is num cards in hand

   inspectCard(3) : T of Spades
   inspectCard(1) : 5 of Diamonds
   inspectCard(-10) : [invalid]
   inspectCard(52) : [invalid]
   inspectCard(51) : T of Spades

   Emptying hand :
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
3 of Diamonds
5 of Diamonds
T of Spades
0 is num cards in hand
Hand :

Loading cards in hand, then emptying :
inspectCard(5) : [invalid]
insepctCard(0) : [invalid]
Hand :

   finished */