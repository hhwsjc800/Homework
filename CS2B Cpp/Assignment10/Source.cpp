#include <iostream>
#include <string>

using namespace std;
class Card
{
public:
   enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };

   //mutators
   Card(char value = 'A', Suit suit = SPADES);
   bool set(char value, Suit suit);

   //non mutating public method functions (accessors included)
   string toString() const;
   Suit getSuit() const { return suit; }
   char getValue() const { return value; }
   bool getErrorFlag() const { return errorFlag; }
   bool equals(Card card) const;

private:
   char value;
   Suit suit;
   bool errorFlag;
   static bool isValid(char value, Suit suit);
};


class Node
{

public:
   Node *next;
   Node() { next = NULL; }
   virtual ~Node() { delete next; }
   virtual string toString() const { return "(generic node) "; }
  // virtual Node& operator<<(const Node &rhs);

};

class CardNode : public Node
{
private:
   Card data;

public:
   CardNode() { data = Card(); }
   CardNode(Card x) { data = x; }
   string toString() const { return data.toString(); }
   Card getData() const { return data;}
};

class Queue
{
protected: 
   Node *front, *back;

public: 
   Queue() { front = back = NULL; }
   virtual ~Queue() {};
   void add(Node *newNode);
   Node *remove();
   virtual string toString() const;

   class EmptyQueueException {};
};

class CardQueue : public Queue
{
public:
   ~CardQueue();
   void add(Card addCard);
   Card &remove();

   class CardQueueEmptyException {};
};

//----Node class definitions--------


//----Queue class definitions
void Queue::add(Node *newNode)
{
   //something is wrong
   if (newNode == NULL)
      return;

   if (front == NULL)
   {
      front = newNode;
      front->next = newNode;
      back = newNode;
      newNode->next = NULL;
      return;
   }

   back->next = newNode;
   back = back->next;
   return;

}

Node *Queue::remove()
{
   if (front == back)
      throw EmptyQueueException();

   Node *temp = front;
   front = front->next;

   if (front == back)
      back = NULL;

   return temp;
}

string Queue::toString() const
{
   string retString;
   Node *nodePtr = front;

   while (nodePtr!= NULL)
   {
      retString += nodePtr->toString();
      nodePtr = nodePtr->next;
   }

   return retString;
}

//--------CardQueue class definitions----
void CardQueue::add(Card addCard)
{
   CardNode *newCard = new CardNode(addCard);
   this->Queue::add(newCard);
   return;
}

Card &CardQueue::remove()
{
   if (front == back)
      throw CardQueueEmptyException();
   CardNode *retVal = (CardNode*)this->Queue::remove();
   
   return retVal->getData();
}


//Class declarations
//Card class simulates playing cards


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
Card::Card(char value, Suit suit)
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
  

int main()
{
   const int numNodes = 10;

   CardNode *nodeArr[numNodes];

   for (int i = 0; i < numNodes; i++)
      nodeArr[i] = new CardNode(Card('J',(Card::Suit) i));
 
   CardQueue myQueue;

   for (int i = 0; i < numNodes; i++)
   {
      myQueue.add(nodeArr[i]->getData());
      cout << "\n--current queue:--\n";
      cout << myQueue.toString() << endl << endl;
   }
   cout << "end adding\n";

   CardNode *nodePtr;
   /*try
   {
      while ((nodePtr = myQueue.remove()) != NULL)
         cout << nodePtr->toString();
   }
   catch (...)
   {
      cout << "\n===queue got empty===\n";
   }*/
   int foo;
   cin >> foo;

   return 0;
}