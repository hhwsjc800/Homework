#include <iostream>
#include <string>
#include <vector>
#include <time.h>
#include "iTunes.h"
#include "Foothill_Sort.h"
using namespace std;

int operator+(const int &LHS, const iTunesEntry &RHS)
{
   return LHS + RHS.getTime();
}

int operator+(const iTunesEntry &LHS, const int &RHS)
{
   return LHS.getTime() + RHS;
}

ostream &operator<<(ostream &os, const iTunesEntry &obj)
{
   os << obj.getTitle() << " by " << obj.getArtist() << "(" << obj.getTime()
      << ")";
   return os;
}


class Sublist
{
public:
   Sublist(vector<iTunesEntry> *orig = NULL) 
      : sum(0), originalObjects (orig) { }
   Sublist addItem( const int& indexOfItemToAdd ) const;
   void showSublist() const;
   int getSum() const { return sum; }

private:
   int sum;
   vector<iTunesEntry> *originalObjects;
   vector<int> indices;
};

Sublist Sublist::addItem(const int& indexOfItemToAdd) const
{
   Sublist newList = Sublist(this->originalObjects);

   newList.sum = this->getSum() + (*originalObjects)[indexOfItemToAdd];
   newList.indices = this->indices;
   newList.indices.push_back(indexOfItemToAdd);

   return newList;
}

void Sublist::showSublist() const
{
   cout << "[ ";
   for (int i = 0; i < indices.size(); i++)
      cout <<  " " << (*originalObjects)[indices[i]] << " ";
   cout << "] ";
}


int main()
{
   const int TARGET = 3600;
   vector<iTunesEntry> dataSet;
   vector<Sublist> choices;
   vector<Sublist>::iterator iter, iterBest;
   int k, j, numSets, max = 0, array_size, sum = 0, additions = 0;
   bool foundPerfect = false, tooLarge = true;

   // read the data
   iTunesEntryReader tunes_input("itunes_file.txt");
   if (tunes_input.readError())
   {
      cout << "couldn't open " << tunes_input.getFileName() 
         << " for input.\n";
      exit(1);
   }

  
   // time the algorithm -------------------------
   clock_t start, stop; 
   start = clock();

   // create a vector of objects for our own use:
   array_size = tunes_input.getNumTunes();
   for (int k = 0; k < array_size; k++)
      dataSet.push_back(tunes_input[k]);

   iTunesEntry *iTunesArr = new iTunesEntry[array_size];
   for (int i = 0; i < array_size; i++)
      iTunesArr[i] = dataSet[i];

   arraySort(iTunesArr, array_size);
   
   for (int i = 0; i < array_size; i++)
      dataSet[i] = iTunesArr[i];

   delete[] iTunesArr;

   Sublist mySubList(&dataSet);
   choices.push_back(mySubList);


   cout << "Target time: " << TARGET << endl;

   //check for worst case scenarios
   for (unsigned int i = 0; i < dataSet.size(); i++)
   {
      sum = sum + dataSet[i];
      if (dataSet[i].getTime() < TARGET)
         tooLarge = false;
   }

   if (sum <= TARGET)
   {
      stop = clock();
      cout << "\nAlgorithm Elapsed Time: " 
      << (double)(stop - start)/(double)CLOCKS_PER_SEC 
      << " seconds." << endl << endl;

      cout << "Sublist was masterlist : \n{ ";
      for (unsigned int i = 0; i < dataSet.size(); i++)
         cout << dataSet[i].getTitle() << "  ";
      cout << " }\n ";
      cout << " sum: " << sum;

      int bar;
      cin >> bar;
      return 0;
   }

   if (tooLarge)
   {
      stop = clock();
      cout << "\nAlgorithm Elapsed Time: " 
      << (double)(stop - start)/(double)CLOCKS_PER_SEC 
      << " seconds." << endl << endl;

      cout << "Sublist was the empty set : \n{ [] }";

      int baz;
      cin >> baz;
      return 0;
   }

   numSets = choices.size();
   //otherwise algorithm continues normally
   for (unsigned int i = 0; i < array_size && !foundPerfect; i++)
   {
      cout << " num subsets: " << numSets << endl;
      numSets += additions;
      additions = 0;
      
      for (j = 0; j < numSets; j++)
      {
         sum = choices[j].getSum() + dataSet[i];

         if (sum == TARGET)
         {
            foundPerfect = true;
            choices.insert(choices.end(), choices[j].addItem(i));
            iterBest = choices.end() - 1;
            additions++;
            break;
         }

         else if (sum < TARGET)
         {
            choices.insert(choices.end(), choices[j].addItem(i));
            additions++;
         }
      }
   }

   //now check the subsets in Coll
   max = 0;
   for (iter = choices.begin(); iter != choices.end() && !foundPerfect; ++iter)
   {
      if (iter->getSum() >= max)
      {
         max = iter->getSum();
         iterBest = iter;
      }
   }
   
   stop = clock();
   cout << "\nAlgorithm Elapsed Time: " 
   << (double)(stop - start)/(double)CLOCKS_PER_SEC 
   << " seconds." << endl << endl;

   //show results
   cout << " Best sublist: \n";
   iterBest->showSublist();
   cout << "\n Sum: " << iterBest->getSum();

   int foo;
   cin >> foo;

   return 0; 
}

/*** Run of Part B***********
Target time: 837
 out of making subsetS
Algorithm Elapsed Time: 0.052 seconds.

 Best sublist:
[  Our Big Break by Veggie Tales(69)  Bullhead's Psalm by Blue Record(79)  Bach:
 Suite for Cello No. 1 in G Major Prelude by Yo-yo Ma(141)  Tico-Tico No Fuba by
 Caraivana(147)  Simple Gifts by Yo-yo Ma(154)  Ogeechee Hymnal by Blue Record(1
55) ]
 Sum: 745

Target time: 1200
 out of making subsetS
Algorithm Elapsed Time: 0.273 seconds.

 Best sublist:
[  Our Big Break by Veggie Tales(69)  Bullhead's Psalm by Blue Record(79)  Bach:
 Suite for Cello No. 1 in G Major Prelude by Yo-yo Ma(141)  Tico-Tico No Fuba by
 Caraivana(147)  Simple Gifts by Yo-yo Ma(154)  Ogeechee Hymnal by Blue Record(1
55)  France Chance by Ry Cooter(168)  This Will Be by Natalie Cole(171) ]
 Sum: 1084

Target time: 1433
 out of making subsetS
Algorithm Elapsed Time: 0.951 seconds.

 Best sublist:
[  Our Big Break by Veggie Tales(69)  Bullhead's Psalm by Blue Record(79)  Bach:
 Suite for Cello No. 1 in G Major Prelude by Yo-yo Ma(141)  Tico-Tico No Fuba by
 Caraivana(147)  Simple Gifts by Yo-yo Ma(154)  Ogeechee Hymnal by Blue Record(1
55)  France Chance by Ry Cooter(168)  This Will Be by Natalie Cole(171)  Well Th
at's All Right by Howlin' Wolf(175) ]
 Sum: 1259


 Target time: 90000

Algorithm Elapsed Time: 0.03 seconds.

Sublist was masterlist :
{ Our Big Break  Bullhead's Psalm  Bach: Suite for Cello No. 1 in G Major Prelud
e  Tico-Tico No Fuba  Simple Gifts  Ogeechee Hymnal  France Chance  This Will Be
  Well That's All Right  Alimony  Feeling Good  Everybody's In The Mood  I Can't
 Quit You Baby  Donuts for Benny  The Other Woman  Hobo Blues  Watch the Girl  T
e Amo Tanto  Twelve Sticks  Lil' Crips  Rip It Up  Ladies and Gentleman  The Roa
d  My Girlfriend  Dime Si te Vas Con El  Good Life  Hot Cha  Shuffleboard  Unfor
gettable  Samson and Delilah  Think About It  Give It All U Got  Quitter  Someth
ing to Talk About  Russian Roulette  Take Your Shirt Off  Monkey Wrench  I'm Jus
t a Prisoner  I Blew Up The United States  Luka  Small Blue Thing  Cowboy Casano
va  Fire Burning  What We Talkin' About  Noites Cariocas  In a Sentimental Mood
 Gangsta Luv  Timothy  All My Life  Lizard Skin  You Were Never Mine  Empire Sta
te of Mind  Kid Charlemagne  Pretending  The Bit  Clone  Fish Fare  Bad Love  Bl
ack Cow  Stronger  Medieval Overture  Rockit  I Can't Make You Love Me  That's T
he Homie  Where Did Your Heart Go?  Oblivion  Haitian Divorce  Goin' On  Blues O
n the Corner  Pirate Jenny  Monk/Trane  Green Onions  Sonny Side  Nefertiti  A L
ove Supreme Part 1  Afro Blue  Brahms: Symphony No. 4 in E Minor Op. 98  Brahms:
 Symphony No. 1 in C Minor Op. 68  Chameleon   }
  sum: 22110

/


/*
int main()
{
   clock_t start, stop;
   const int TARGET = 127;
   vector<int> dataSet;
   vector<Sublist> choices;
   vector<Sublist>::iterator iter, iterBest;
   int k, j, numSets, max = 0, sum = 0;
   bool foundPerfect = false;


   dataSet.push_back(20); dataSet.push_back(12); dataSet.push_back(22);
   dataSet.push_back(15); dataSet.push_back(25);
   dataSet.push_back(19); dataSet.push_back(29);
   dataSet.push_back(18);
   dataSet.push_back(11); dataSet.push_back(13); dataSet.push_back(17);

   choices.clear();
   cout << "Target value: " << TARGET << endl;

   // code provided by student
   Sublist mySublist(&dataSet);
   choices.push_back(mySublist);

   //algorithm starts here
   start = clock();

   //check for worst case scenario
   for (unsigned int i = 0; i < dataSet.size(); i++)
      sum += dataSet[i];
   if (sum <= TARGET)
   {
      stop = clock();
      cout << "\nAlgorithm Elapsed Time: " 
      << (double)(stop - start)/(double)CLOCKS_PER_SEC 
      << " seconds." << endl << endl;

      cout << "Sublist was masterlist : \n{ ";
      for (unsigned int i = 0; i < dataSet.size(); i++)
         cout << dataSet[i] << "  ";
      cout << " }\n ";
      cout << " sum: " << sum;

      int bar;
      cin >> bar;
      return 0;
   }

   //otherwise algorithm continues normally
   for (unsigned int i = 0; i < dataSet.size() && !foundPerfect; i++)
   {
      numSets = choices.size();
      for (j = 0; j < numSets; j++)
      {
         if (choices[j].getSum() + dataSet[i] == TARGET)
         {
            foundPerfect = true;
            choices.insert(choices.end(), choices[j].addItem(i));
            iterBest = choices.end() - 1;
            break;
         }

         if (choices[j].getSum() + dataSet[i] < TARGET)
            choices.insert(choices.begin(), choices[j].addItem(i));
      }
   }

   
   //now check the subsets in Coll
   for (iter = choices.begin(); iter != choices.end() && !foundPerfect; ++iter)
   {
      if (iter->getSum() >= max)
      {
         max = iter->getSum();
         iterBest = iter;
      }
   }
   
   stop = clock();
   cout << "\nAlgorithm Elapsed Time: " 
   << (double)(stop - start)/(double)CLOCKS_PER_SEC 
   << " seconds." << endl << endl;

   //show results
   cout << " Best sublist: \n";
   iterBest->showSublist();
   cout << "\n Sum: " << iterBest->getSum();

   int foo;
   cin >> foo;

   return 0; 
}*/

/*-----Run of Assignment 1------------------


============Part A====================

Target value: 32

Algorithm Elapsed Time: 0.001 seconds.

 Best sublist:
[  20  12 ]
 Sum: 32



Target value: 300

Algorithm Elapsed Time: 0 seconds.

Sublist was masterlist :
{ 20  12  22  15  25  19  29  18  11  13  17   }
  sum: 201



Target value: 154

Algorithm Elapsed Time: 2.061 seconds.

 Best sublist:
[  20  12  22  15  25  19  18  17 ]
 Sum: 148



Target value: 79

Algorithm Elapsed Time: 0.002 seconds.

 Best sublist:
[  20  12  22  25 ]
 Sum: 79


Target value: 127

Algorithm Elapsed Time: 0.609 seconds.

 Best sublist:
[  20  12  22  15  25  18  13 ]
 Sum: 125

-------------Source------------------

#include <iostream>
#include <string>
#include <vector>
#include <time.h>

using namespace std;

class Sublist
{
public:
   Sublist(vector<int> *orig = NULL) 
      : sum(0), originalObjects (orig) { }
   Sublist addItem( int indexOfItemToAdd );
   void showSublist() const;
   int getSum() const { return sum; }

private:
   int sum;
   vector<int> *originalObjects;
   vector<int> indices;
};

Sublist Sublist::addItem(int indexOfItemToAdd)
{
   Sublist newList = Sublist(this->originalObjects);

   newList.sum = this->getSum() + (*originalObjects)[indexOfItemToAdd];
   newList.indices = this->indices;
   newList.indices.push_back(indexOfItemToAdd);

   return newList;
}

template <class T>
class genericSublist
{
public:
   genericSublist(vector<T> *orig = NULL)
      : sum(0), originalObjects(orig) { }
   genericSublist(addItem int indexOfItemToAdd);
   void showSublist() const;
   int getSum() const { return sum; }

};

void Sublist::showSublist() const
{
   cout << "[ ";
   for (int i = 0; i < indices.size(); i++)
      cout <<  " " << (*originalObjects)[indices[i]] << " ";
   cout << "] ";
}
int main()
{
   clock_t start, stop;
   const int TARGET = 127;
   vector<int> dataSet;
   vector<Sublist> choices;
   vector<Sublist>::iterator iter, iterBest;
   int k, j, numSets, max = 0, sum = 0;
   bool foundPerfect = false;


   dataSet.push_back(20); dataSet.push_back(12); dataSet.push_back(22);
   dataSet.push_back(15); dataSet.push_back(25);
   dataSet.push_back(19); dataSet.push_back(29);
   dataSet.push_back(18);
   dataSet.push_back(11); dataSet.push_back(13); dataSet.push_back(17);

   choices.clear();
   cout << "Target value: " << TARGET << endl;

   // code provided by student
   Sublist mySublist(&dataSet);
   choices.push_back(mySublist);

   //algorithm starts here
   start = clock();

   //check for worst case scenario
   for (unsigned int i = 0; i < dataSet.size(); i++)
      sum += dataSet[i];
   if (sum <= TARGET)
   {
      stop = clock();
      cout << "\nAlgorithm Elapsed Time: " 
      << (double)(stop - start)/(double)CLOCKS_PER_SEC 
      << " seconds." << endl << endl;

      cout << "Sublist was masterlist : \n{ ";
      for (unsigned int i = 0; i < dataSet.size(); i++)
         cout << dataSet[i] << "  ";
      cout << " }\n ";
      cout << " sum: " << sum;

      int bar;
      cin >> bar;
      return 0;
   }

   //otherwise algorithm continues normally
   for (unsigned int i = 0; i < dataSet.size() && !foundPerfect; i++)
   {
      numSets = choices.size();
      for (j = 0; j < numSets; j++)
      {
         if (choices[j].getSum() + dataSet[i] == TARGET)
         {
            foundPerfect = true;
            choices.insert(choices.end(), choices[j].addItem(i));
            iterBest = choices.end() - 1;
            break;
         }

         if (choices[j].getSum() + dataSet[i] < TARGET)
            choices.insert(choices.begin(), choices[j].addItem(i));
      }
   }

   
   //now check the subsets in Coll
   for (iter = choices.begin(); iter != choices.end() && !foundPerfect; ++iter)
   {
      if (iter->getSum() >= max)
      {
         max = iter->getSum();
         iterBest = iter;
      }
   }
   
   stop = clock();
   cout << "\nAlgorithm Elapsed Time: " 
   << (double)(stop - start)/(double)CLOCKS_PER_SEC 
   << " seconds." << endl << endl;

   //show results
   cout << " Best sublist: \n";
   iterBest->showSublist();
   cout << "\n Sum: " << iterBest->getSum();

   int foo;
   cin >> foo;

   return 0; 
}
--------------End Source--------------------



 ========Part B===================


*/