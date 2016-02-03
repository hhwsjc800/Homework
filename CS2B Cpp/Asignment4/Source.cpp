/*------------------------------------------------------------------------
Two classes used by assignment #4.  Do not modify these defintions.
Place the prototypes and definitions into your final submission,
with each element appearing in the correct place among the other protypes,
defintions, and main().

The #includes are needed by my solution and will probably be needed
by yours, so you should place them in your project at the top of the
file(s).
----------------------------------------------------------------------- */

#include <iostream>
#include <string>
#include <climits>
#include <cmath>
#include <sstream>
#include <ctime>       
#include <stdlib.h>   
using namespace std;

// IntPair allows public, no filtering; classes that use it will protect it
class IntPair
{
public:
   long firstInt;
   long secondInt;

   IntPair() { firstInt = secondInt = 0; }
   IntPair(long frst, long scnd) { firstInt = frst;  secondInt = scnd; }
   string toString();
};


// helper  class IntPair method definitions ----------------------------------
string IntPair::toString()
{
   ostringstream cnvrt1, cnvrt2;
   string retString;

   cnvrt1 << firstInt;
   cnvrt2 << secondInt;

   retString = "(" + cnvrt1.str() + ", " + cnvrt2.str() + ")";

   return retString;
}

// EncryptionSupport contains only static methods that clients can use wherever
// all method names should be fairly descriptive other than inverseMonN(), which
// you can take as a black-box (see description of assignment)
class EncryptionSupport
{
public:
   static bool isPrime(long x);
   static long inverseModN(long a, long n);
   static long getSmallRandomPrime();
   static long getMedSizedRandomPrime();
};


// class EncryptionSupport method definitions --------------------------------
bool EncryptionSupport::isPrime(long x)
{
   long k, loopLim;

   if (x < 2)
      return false;
   if (x < 4)
      return true;
   if (x % 2 == 0 || x % 3 == 0)
      return false;

   // now use the fact the all primes of form 6k +/- 1
   loopLim = (long)sqrt(x);
   for (k = 5; k <= loopLim; k += 6)
   {
      if (x % k == 0 || x % (k + 2) == 0)
         return false;
   }
   return true;
}

long EncryptionSupport::getSmallRandomPrime()
{
   int index;
   long lowPrimes[] =
   {
      19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
      71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
      127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
      179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
      233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
      283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
      353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
      419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
      467, 479, 487, 491, 499, 503, 509, 521, 523, 541
   };
   short arraySize = sizeof(lowPrimes) / sizeof(lowPrimes[0]);

   // pick prime in the above array btween 0 and arraySize - 1
   index = (int)(rand() % (arraySize - 1));

   return lowPrimes[index];
}

long EncryptionSupport::getMedSizedRandomPrime()
{
   int index;
   long medPrimes[] =
   {
      541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607,
      613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677,
      683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761,
      769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853,
      857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937,
      941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019,
      1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087,
      1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153,
      1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223,
   };
   short arraySize = sizeof(medPrimes) / sizeof(medPrimes[0]);

   // pick prime in the above array bet 0 and arraySize - 1
   index = (int)(rand() % (arraySize - 1));

   return medPrimes[index];
}

long EncryptionSupport::inverseModN(long a, long n)
{
   // uses extended euclidean algorithm giving as + nt = gcd(n, a), 
   // with gcd(n, a) = 1,  and s, t discovered.  s = 1/a, and t ignored

   long s, t, r, s_prev, t_prev, r_prev, temp, q, inverse;

   // special key encryption conditions;  we will pick some prime e >= 3 for a
   if (a < 3 || a >= n || !isPrime(a))
      return 0;  // error

                 // we are now guaranteed 3 <= a < n and gcd(a, n) = 1;

                 // initialize working variables
   s = 0;         t = 1;         r = n;
   s_prev = 1;    t_prev = 0;    r_prev = a;

   while (r != 0)
   {
      q = r_prev / r;

      temp = r;
      r = r_prev - q * r;
      r_prev = temp;

      temp = s;
      s = s_prev - q * s;
      s_prev = temp;

      temp = t;
      t = t_prev - q * t;
      t_prev = temp;
   }

   inverse = s_prev % n;
   if (inverse < 0)
      inverse += n;
   return inverse;
}

class InternetUser
{
public:
   static const int MIN_NAME_LENGTH = 2;
   static const int MAX_NAME_LENGTH = 50;
   static const int MIN_IP_LENGTH = 7;
   static const int MAX_IP_LENGTH = 15; 
   static const string DEFAULT_IP;
   static const string DEFAULT_NAME;
   InternetUser();
   InternetUser(string name, string ip);
   string getName() const { return name; }
   string getIp() const { return ip; }
   bool setName(string nameTest);
   bool setIp(string ipTest);
   string toString() const;

private:
   string name;
   string ip;
   static bool isValidIp(string ipTest);
   static bool isValidName(string nameTest);
};

class Communicator: public InternetUser
{
public:
   static const int ERROR_FLAG_NUM = 0;
   static const long MAX_PQ;
   Communicator();
   Communicator(long firstPrime, long secondPrime);
   Communicator(string name, string ip);
   Communicator(string name, string ip, long firstPrime, long secondPrime);
   bool setPrimes(long prime1, long prime2);
   bool computeBothEncrKeys(long p, long q);

private:
   IntPair publicKey;
   IntPair privateKey;
   static const long MAX_PQ;
   long firstPrime;
   long secondPrime;
   long n, phi, e, d;

};

//////////////////////////////////////
//                                  //
//  Communicator CLASS DEFINITIONS  //
//                                  //
/////////////////////////////////////
const long Communicator::MAX_PQ = sqrt(LONG_MAX);

Communicator::Communicator() 
{
   publicKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   privateKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   firstPrime = secondPrime = ERROR_FLAG_NUM;
   n = phi = e = d = ERROR_FLAG_NUM;
}

Communicator::Communicator(long firstPrime, long secondPrime)
{
   if (!setPrimes(firstPrime, secondPrime))
   {
      this->firstPrime = ERROR_FLAG_NUM;
      this->secondPrime = ERROR_FLAG_NUM;
      publicKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
      privateKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   }
   n = phi = e = d = ERROR_FLAG_NUM;

}
   

Communicator::Communicator(string name, string ip) : InternetUser(name, ip)
{
   publicKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   privateKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   firstPrime = secondPrime = ERROR_FLAG_NUM;
   n = phi = e = d = ERROR_FLAG_NUM;
}

Communicator::Communicator(string name, string ip, 
   long firstPrime, long secondPrime) : InternetUser(name, ip)
{
   if (!setPrimes(firstPrime, secondPrime))
   {
      this->firstPrime = ERROR_FLAG_NUM;
      this->secondPrime = ERROR_FLAG_NUM;
      publicKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
      privateKey = IntPair(ERROR_FLAG_NUM, ERROR_FLAG_NUM);
   }

   n = phi = e = d = ERROR_FLAG_NUM;
}

bool Communicator::setPrimes(long firstPrime, long secondPrime)
{
   if (EncryptionSupport::isPrime(firstPrime) &&
      EncryptionSupport::isPrime(secondPrime) &&
      firstPrime != secondPrime)
   {
      this->firstPrime = firstPrime;
      this->secondPrime = secondPrime;
      if (computeBothEncrKeys(firstPrime, secondPrime))
         return true;
   }
   
   return false;
}

bool Communicator::computeBothEncrKeys(long p, long q)
{

}


//////////////////////////////////////
//                                  //
//  InternetUser CLASS DEFINITIONS  //
//                                  //
/////////////////////////////////////

const string InternetUser::DEFAULT_IP = "0.0.0.0";
const string InternetUser::DEFAULT_NAME = "(undefined)";

InternetUser::InternetUser()
{
   name = DEFAULT_NAME;
   ip = DEFAULT_IP;
}

InternetUser::InternetUser(string name, string ip)
{
   if (!isValidName(name) || !isValidIp(ip))
   {
      this->name = DEFAULT_NAME;
      this->ip = DEFAULT_IP;
   }
   
   else
   {
      this->name = name;
      this->ip = ip;
   }
}

bool InternetUser::isValidName(string nameTest)
{
   if (nameTest.length() < MIN_NAME_LENGTH ||
      nameTest.length() > MAX_NAME_LENGTH)
      return false;
   return true;
}

bool InternetUser::isValidIp(string ipTest)
{
   if (ipTest.length() < MIN_IP_LENGTH ||
      ipTest.length() > MAX_IP_LENGTH)
      return false;
   
   return true;
}

bool InternetUser::setName(string nameTest)
{
   if (!isValidName(nameTest))
      return false;
   name = nameTest;
   return true;
}

bool InternetUser::setIp(string ipTest)
{
   if (!isValidIp(ipTest))
      return false;
   ip = ipTest;
   return true;
}

string InternetUser::toString() const
{
   string returnString = "Name: ";
   returnString += name + "\n";
   returnString += "IP Address: " + ip + "\n";

   return returnString;
}

int main()
{

   //----InternetUser Class Tests
   InternetUser internet1, internet2("n", "69"), internet3;
   internet3 = InternetUser("Joey", "9.8.7.6");
   internet2.setName("Han Solo");

   cout << "---InternetUser Class Tests: \n\n Initial Members: \n"
      << internet1.toString() << internet2.toString() << internet3.toString()
      << endl << endl;

   //----Test Accessors: 
   cout << "---Testing accessors: \n"
      << internet2.getName() << " " << internet2.getIp() 
      << internet3.getName() << " " << internet3.getIp() << endl << endl;

   //----Test mutators: 
   cout << "---Testing mutators: \n";
   if (internet1.setName("Billy Bob"))
      cout << "Successfully changed name of internet1 \n";
   else
      cout << "Failed to change name of internet1\n";
   
   if (internet2.setIp("1234.5.6.7.8.999999."))
      cout << "Successfully changed IP of internet2\n";
   else
      cout << "Failed to change ip of internet2 (expected)\n";

   if (internet3.setName("A"))
      cout << "Successfully changed name of internet3!\n\n";
   else
      cout << "Failed to change internet3 name (expected) \n\n";

   cout << "---End base class tests \n\n";

//----End Base Class Tests


   int foo;
   cin >> foo;
   return 0;


}