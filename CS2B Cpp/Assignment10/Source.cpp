#include <iostream>
#include <string>

using namespace std;

template <typename T>
class Node
{
protected:
   Node<T> *next;

public:
   Node<T>() { next = NULL; }
   virtual string toString() const;
   virtual Node<T>& operator<<(const Node<T> &rhs);

};

template <typename T>
class Queue
{
protected: 
   Node<T> *front, *back;

public: 
   Queue<T>() { front = back = NULL; }
};