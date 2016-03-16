//============================================================================
// Name        : CS1B (Java) Assignment 9: Trees (LATE) Option B-2 and B-1
//               Extra Credit
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Mar 10, 2016
// Instructor  : Harden
// Description : This program consists of a generic tree class with soft
//               deletion. Soft deletion is obtained using derived classes
//               from base tree classes provided. Additionally, recursive
//               function definitions are used in order to provide this 
//               functionality to the trees.
//               Unlike the other assignments, the classes here are template
//               classes which allow the user to create trees of generic
//               type. There are three mains, each demonstrating a different
//               class template.
//               
//               Thanks for your help and patience Prof. Harden and Cliff!
//                
//============================================================================

//NOTE: Assignment tries for option B-1, B-2 for extra credit!!!

//------------------------------------------------------
public class FoothillExtraCredB1B2
{ 
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      FHsdTree<String> sceneTree = new FHsdTree<String>();
      FHsdTreeNode<String> tn;
      
      System.out.println("Starting tree empty? " + sceneTree.empty() + "\n");
      // create a scene in a room
      tn = sceneTree.addChild(null, "room");

      // add three objects to the scene tree
      sceneTree.addChild(tn, "Lily the canine");
      sceneTree.addChild(tn, "Miguel the human");
      sceneTree.addChild(tn, "table");
      // add some parts to Miguel
      tn = sceneTree.find("Miguel the human");

      // Miguel's left arm
      tn = sceneTree.addChild(tn, "torso");
      tn = sceneTree.addChild(tn, "left arm");
      tn =  sceneTree.addChild(tn, "left hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");

      // Miguel's right arm
      tn = sceneTree.find("Miguel the human");
      tn = sceneTree.find(tn, "torso", 0);
      tn = sceneTree.addChild(tn, "right arm");
      tn =  sceneTree.addChild(tn, "right hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");

      // add some parts to Lily
      tn = sceneTree.find("Lily the canine");
      tn = sceneTree.addChild(tn, "torso");
      sceneTree.addChild(tn, "right front paw");
      sceneTree.addChild(tn, "left front paw");
      sceneTree.addChild(tn, "right rear paw");
      sceneTree.addChild(tn, "left rear paw");
      sceneTree.addChild(tn, "spare mutant paw");
      sceneTree.addChild(tn, "wagging tail");

      // add some parts to table
      tn = sceneTree.find("table");
      sceneTree.addChild(tn, "north east leg");
      sceneTree.addChild(tn, "north west leg");
      sceneTree.addChild(tn, "south east leg");
      sceneTree.addChild(tn, "south west leg");

      System.out.println("\n------------ Loaded Tree ----------------- \n");
      sceneTree.display();
      
      sceneTree.remove("spare mutant paw");
      sceneTree.remove("Miguel the human");
      sceneTree.remove("an imagined higgs boson");
      
      System.out.println("\n------------ Virtual (soft) Tree ----------- \n");
      sceneTree.display();

      System.out.println("\n----------- Physical (hard) Display ---------- \n");
      sceneTree.displayPhysical();
      
      System.out.println("------- Testing Sizes (compare with above) ----- \n");
      System.out.println("virtual (soft) size: " + sceneTree.size()  );
      System.out.println("physiical (hard) size: " + sceneTree.sizePhysical());

      System.out.println("------------ Collecting Garbage -------------- \n");
      System.out.println("found soft-deleted nodes? " 
            + sceneTree.collectGarbage()  );
      System.out.println("immediate collect again? " 
            + sceneTree.collectGarbage()  );

      System.out.println("--------- Hard Display after garb col --------- \n");

      sceneTree.displayPhysical();

      System.out.println("Semi-deleted tree empty? " + sceneTree.empty()+"\n");
      sceneTree.remove("room");
      System.out.println("Completely-deleted tree empty? " + sceneTree.empty() 
         + "\n");
      
      sceneTree.display();
      
      
      //--------MAIN PART TWO: WITH ints--------------------
      FHsdTree<Integer> numTree = new FHsdTree<Integer>();
      FHsdTreeNode<Integer> numNode;

      //create a double root
      numNode = numTree.addChild(null, 6);

      //add some objects to the tree
      numTree.addChild(numNode, 33);
      numTree.addChild(numNode, 12);
      numTree.addChild(numNode, -30);

      //add parts to some nodes
      numNode = numTree.find(12);
      numTree.addChild(numNode, 542);
      numTree.addChild(numNode, 2232);
      numTree.addChild(numNode, 41);

      numNode = numTree.find(41);
      numTree.addChild(numNode, 34);

      numNode = numTree.find(-30);
      numTree.addChild(numNode, 314);
      numTree.addChild(numNode, -314);
      numTree.addChild(numNode, 2141);

      System.out.print( "\n------------ Loaded Tree ----------------- \n");
      numTree.display();

      numTree.remove(314);
      numTree.remove(41);
      numTree.remove(2232);
      numTree.remove(67);

      System.out.print("\n------------ Virtual (soft) Tree --------------\n");
      numTree.display();

      System.out.print("\n------------ Physical (hard) Display --------- \n");
      numTree.displayPhysical();
      
      System.out.print( "------- Testing Sizes (compare with above)------ \n");
      System.out.println("virtual (soft) size: " + numTree.size());
      System.out.println( "physical (hard) size: " + numTree.sizePhysical());
      
      System.out.println("--------- Collecting Garbage -------------- \n");
      System.out.println("found soft-deleted nodes? "+
      numTree.collectGarbage());
      System.out.println("immediate collect again? " + 
      numTree.collectGarbage());
      System.out.print( "--------- Hard Display after garb col --------- \n");
      numTree.displayPhysical();

      System.out.println( "Semi-deleted tree empty? " + numTree.empty() +
              "\n");
      numTree.remove(6);
      System.out.println( "Completely-deleted tree empty? "+numTree.empty()
      +"\n\n");
      //-----------------------End Main Run 2--------------------



      //------Main Run #3: SoftDel tree with type Card------------------
      FHsdTree<Card> cardTree = new FHsdTree<Card>();

      FHsdTreeNode<Card> cNode;

      //create a double root
      cNode = cardTree.addChild(null,new Card('A', Card.Suit.HEARTS));

      //add some objects to the tree
      cardTree.addChild(cNode,new Card());
      cardTree.addChild(cNode,new Card('K', Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('X',Card.Suit.DIAMONDS));

      //add parts to some nodes
      cNode = cardTree.find(new Card('K', Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('J', Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('Q', Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('A',Card.Suit.HEARTS));

      cNode = cardTree.find(new Card('X',Card.Suit.DIAMONDS));
      cardTree.addChild(cNode,new Card('T',Card.Suit.CLUBS));

      cNode = cardTree.find(new Card('T',Card.Suit.CLUBS));
      cardTree.addChild(cNode,new Card('T',Card.Suit.SPADES));
      cardTree.addChild(cNode,new Card('T',Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('T',Card.Suit.DIAMONDS));

      cNode = cardTree.find(new Card('Q', Card.Suit.HEARTS));
      cardTree.addChild(cNode,new Card('5', Card.Suit.SPADES));
      cardTree.addChild(cNode,new Card('6', Card.Suit.SPADES));
      cardTree.addChild(cNode,new Card('3', Card.Suit.SPADES));
      cardTree.addChild(cNode,new Card('9', Card.Suit.SPADES));


      System.out.print("\n------------ Loaded Tree ----------------- \n");
      cardTree.display();
      FHsdTree<Card> cardTreeClone = cardTree;

      System.out.print("\n------Loaded Clone Tree ----------------\n");
      cardTreeClone.display();

      cardTree.remove(new Card('T',Card.Suit.HEARTS));
      cardTree.remove(new Card('Q',Card.Suit.HEARTS));
      
      System.out.print("\n------------ Virtual (soft) Tree + clone ------- \n");
      cardTree.display();

      System.out.print("\n---clone---\n");
      cardTreeClone.display();

      System.out.print("\n------------ Physical tree, real then clone------ \n");
      cardTree.displayPhysical();
      System.out.print("\n" + "------\n" );
      cardTreeClone.displayPhysical();
      
      System.out.print("------- Testing Sizes (compare with above)------- \n");
      System.out.println("virtual (soft) size: " + cardTree.size() );
      System.out.println("physical (hard) size: " + cardTree.sizePhysical() );
      System.out.println("clone (hard) size: " + cardTreeClone.sizePhysical() );

      System.out.print("------------ Collecting Garbage ----------------- \n");
      System.out.println("found soft-deleted nodes? "+ cardTree.collectGarbage());
      System.out.println("immediate collect again? " + cardTree.collectGarbage());

      System.out.print("--------- Hard Display after garb col ------------ \n");
      cardTree.displayPhysical();

      System.out.println("Semi-deleted tree empty? " + cardTree.empty() );
      cardTree.remove(new Card('A', Card.Suit.HEARTS));
      System.out.println("Completely-deleted tree empty? " + cardTree.empty());
      
      System.out.println("\n---final tree----");
      cardTree.displayPhysical();
      
      //-----End Main Run 3-------------------------------------

      return;
   }
}

class FHsdTree<E> extends FHtree<E> implements Cloneable
{
    //Omitting descriptions for inline functions. They are self-explanatory

    public int sizePhysical() { return super.size(); }
    public boolean empty() { return size() == 0; }
    

    
    
    
    
    
    
    
    //Takes an FHsdTreeNode treeNode and generic object x. Tries to add
    //the node as a child into the tree. If the tree is empty the 
    //passed node will be used to initialize the tree as the root
    //provided that the treeNode parameter is null. Otherwise, provided
    //that the treeNode parameter is valid, the passed in treeNode
    //will be added as a child. Returns the new child.
    public FHsdTreeNode<E> addChild( FHsdTreeNode<E> treeNode, E x)
    {
       // empty tree? - create a root node if user passes in NULL
       if (mSize == 0)
       {
          if (treeNode != null)
             return null; // silent error something's fishy.treeNode can't write
          this.mRoot = new FHsdTreeNode<E>(x, null, null, null, false);
          ((FHsdTreeNode<E>) mRoot).setRoot(mRoot);
          mSize = 1;
          return (FHsdTreeNode<E>) mRoot;
       }

       if (treeNode == null)
          return null;
       if (treeNode.getRoot() != mRoot)
          return null;  // silent error, node does not belong to this tree

       // push this node into the head of the sibling list; adjust prev pointers
       FHsdTreeNode<E> newNode = new FHsdTreeNode<E>(x, 
          treeNode.getFirstChild(), null, treeNode, false);  
       newNode.setRoot(mRoot);
       treeNode.setFirstChild(newNode);
       if (newNode.getSibling() != null)
          newNode.getSibling().setPrevious(newNode);
       ++mSize;
       return (FHsdTreeNode<E>)newNode;
    }

    
    
    
    
    
    
    
    
    //Recursively searches through the tree. Takes nodes as root, object
    //data as x, and an int level for recursion. It returns null if 
    //the current node is invalid or deleted or if the tree is empty.
    //Otherwise, it will return the current node if it finds its data
    //is equal to x. If not, find will process siblings and 
    //children until it finds the value x or searches the entire tree.
    public FHsdTreeNode<E> find(FHsdTreeNode<E> root, E x, int level)
    {
       FHsdTreeNode<E> retval;
     
       if (mSize == 0 || root == null || root.isDeleted())
          return null;

       
       if (root.getData().equals(x))
          return root;
       
       // otherwise, recurse.  don't process sibs if this was the original call
       if ( level > 0 && (retval = find(root.getSibling(), x, level)) != null)
          return (FHsdTreeNode<E>) retval;
       return find(root.getFirstChild(), x, level+1);
    }

    
    
    
    
    
    
    
    //Initial step for above recursive function. Starts the search of
    //finding a node in a tree at the root.
    public FHsdTreeNode<E> find(E x)
    {
       return find((FHsdTreeNode<E>)mRoot, x, 0); 
    }

    
    
    
    
    
    
    
    //Initial step for below recursive function that searches the tree
    //for an object with data x. Takes in a single object x of type generic
    //and searches the tree for that object beginning from the root.
    public boolean remove(E x) 
    { 
       return remove((FHsdTreeNode<E>)(mRoot), x); 
    }
   
    
    
    
    
    
    
    //Recursive step of the remove function. Takes in a node and object data
    //of generic type called x. If we find that the root passed in is 
    //deleted or null or the tree is empty, returns false immediately
    //to indicate that we could not remove the node. Otherwise
    //the function searches the tree for the node to remove using a placeholder
    //node called tn. If it finds this node in the tree and if the node is 
    //valid, we set the node's deleted member to true and return true.
    //If anything else happens the function returns FALSE
    public boolean remove(FHsdTreeNode<E> root, E x)
    {
       FHsdTreeNode<E> tn = null;

       if (mSize == 0 || root == null || root.isDeleted())
          return false;
      
       if ( (tn = find(root, x, 0)) != null)
       {
          tn.setDeleted(true);
          return true;
       }
       return false;
    }

    
    
    
    
    
    
    
    //Initial step for recursive function display
    public void display()
    { 
       display((FHsdTreeNode<E>)(mRoot), 0); 
    }
    
    final static String blankString = "                                    ";
    
    
    
    
    
    
    
    
    //Similar to the display function provided by the instructor. Recursively
    //searches through the nodes of the tree and outputs them in a nice way
    //based on their depth in the tree. Takes two parameters, a node
    //treeNode and int level. level is used for the recursive call in order
    //to decide whether or not to search for siblings. If the function
    //encounters an invalid node or an end to a node it will return null.
    //Otherwise it will output node data based on its position in the tree
    //using the threeNode getData() function. If the node happens to be
    //deleted, we will not print the node or its children. However,
    //it will still rightfully try to see if the deleted node's siblings
    //can be printed
    public void display(FHsdTreeNode<E> treeNode, int level) 
    {
       // this will be static and so will be shared by all calls - a 
        //special technique to
       // be avoided in recursion, usually
       
       String indent;

       // stop runaway indentation/recursion
       if  (level > (int)blankString.length() - 1)
       {
          System.out.println(blankString + " ... ");
          return;
       }

       if (treeNode == null)
          return;

       indent = blankString.substring(0, level) + "..";
       if (!treeNode.isDeleted())
       {
           System.out.println( indent + treeNode.getData());
          display(treeNode.getFirstChild(), level + 1);
       }
       
       if (level > 0)
          display( treeNode.getSibling(), level );

    }

    
    
    
    
    
    
    //Traverser intial step used in recursive function below
    public <F extends Traverser<? super E>> 
    void traverse(F func)  { traverse(func, (FHsdTreeNode<E>) mRoot, 0); }
    
    
    
    
    
    
    
    
    
    //Traversre is a function that takes in a function and applies that 
    //function to each node of the tree provided the node is not deleted.
    //Identical in function to the traverse function provided by the
    //instructor except for the fact that traverse will not act
    //on deleted nodes.
    public <F extends Traverser<? super E>> 
    void traverse(F func, FHsdTreeNode<E> treeNode, int level)
    {
       if (treeNode == null || treeNode.isDeleted())
          return;

       func.visit(treeNode.data);
       
       // recursive step done here
       traverse( func, treeNode.getFirstChild(), level + 1);
       if (level > 0 )
          traverse( func,  treeNode.getSibling(), level);
    }
    
    
    
    
    
    
    
    
    
    //Initial recursive step for size function which counts the nondeleted
    //nodes of the tree
    public int size()
    {
       return size((FHsdTreeNode<E>)(mRoot), 0, 0);
    }

    
    
    
    
    
    
    
    
    //size is a recursive function that takes in treeNode of generic node
    //type, and two ints: level and sz. Level is used to determine 
    //the correct recursive call for each depth of the tree. sz is preserved
    //across calls and passed in recursively. With each call to size,
    //the function will add one to size and check for children
    //provided that the node it is currently dealing with is not deleted.
    //It will always check for valid siblings. If it finds null, it will
    //return the current value of size to above callers.
    public int size(FHsdTreeNode<E> treeNode, int level, int sz) 
    {
       if (treeNode == null)
          return sz;

       if (!treeNode.isDeleted())
       {
          sz++;
          sz = size(treeNode.getFirstChild(), level + 1, sz);
       }
       if (level > 0)
          sz = size(treeNode.getSibling(), level, sz);

       return sz;
    }

    
    
    
    
    
    
    
    
    //Initial step for recursive function displayPhysical
    void displayPhysical() 
    { 
       displayPhysical((FHsdTreeNode<E>)(mRoot), 0); 
    }

    
    
    
    
    
    
    
    
    //Identical to the display function provided by the instructor. Recursively
    //searches through the nodes of the tree and outputs them in a nice way
    //based on their depth in the tree. Takes two parameters, a node
    //treeNode and int level. level is used for the recursive call in order
    //to decide whether or not to search for siblings. If the function
    //encounters an invalid node or an end to a node it will return null.
    //Otherwise it will output node data based on its position in the tree
    //using the threeNode getData() function. If the node happens to be
    //deleted, we will STILL print the node and its children. However,
    //the node will be marked with a (D) in the output.
    void displayPhysical(FHsdTreeNode<E> treeNode, int level) 
    {
       String indent;

       // stop runaway indentation/recursion
       if  (level > (int)blankString.length() - 1)
       {
          System.out.println(blankString + " ... ");
          return;
       }

       if (treeNode == null)
          return;

       indent = blankString.substring(0, level);

       System.out.print(indent + treeNode.getData());
       if (treeNode.isDeleted())
          System.out.print(" (D)");
       System.out.println();
       displayPhysical( treeNode.getFirstChild(), level + 1 );
       if (level > 0)
          displayPhysical( treeNode.getSibling(), level );
    }

    
    
    
    
    
    
    
    //collectGarbage is a boolean function that tells the caller whether or
    //not there is a difference in virtual and physical size of the tree.
    //If there is a difference, collectGarbage will begin a recursive process
    //in which it physically removes the virtually deleted nodes from the tree.
    //It returns TRUE if the physicalSize is greater than the virtualSize
    //and false otherwise. Cleanup will not be performed if the tree is empty.
    public boolean collectGarbage()
    {
        
       int virtualSize = size();
       int physicalSize = sizePhysical();
       
       if (virtualSize == 0)
           return false;
       
       if (physicalSize > virtualSize)
           collectGarbage((FHsdTreeNode<E>)(mRoot), 0); //cleans tree
       
       return physicalSize > virtualSize;
    }


    
    
    
    
    
    //Recursive component to the collectGarbage function. Goes through each
    //element of the tree and takes in a generic softdelete treeNode and 
    //an int, level. level is used to determine correct recursive calls.
    //If the function encounters the end to a branch or a null node, it will
    //return. If it encounters a deleted node, it first remembers the
    //deleted node's previous node. Then it deletes the deleted node and 
    //continues with the first child of the previous node and its siblings.
    //collectGarbage recursively searches through the three until all 
    //deleted nodes are physically removed from the tree.
    public void collectGarbage(FHsdTreeNode<E> treeNode,
       int level) 
    {
       //return values here are irrelevant

       if (treeNode == null || mSize == 0)
          return;

       if (treeNode.isDeleted())
       {
          FHsdTreeNode<E> previous = treeNode.getPrevious();
          super.removeNode(treeNode);
          collectGarbage(previous.getFirstChild(), level + 1);
          if (level > 0)
             collectGarbage(previous.getSibling(), level);

          return;
       }
       collectGarbage(treeNode.getFirstChild(), level + 1);
       if (level > 0)
          collectGarbage(treeNode.getSibling(), level);

       return;
    }
}


class FHsdTreeNode<E> extends FHtreeNode<E>
{
    protected boolean deleted;
    
    
    
    

    //default constructor for FHsdTreeNode class. Simply calls base class
    //constructor and sets deleted member to false.
    public FHsdTreeNode()
    {
        super();
        deleted = false;
    }
    
    
    
    
    //overloaded constructor for FHsdTreeNode class. Calls base class
    //constructor for all parameters and then sets deleted to dltd boolean
    //which is passed into it.
    public FHsdTreeNode( E d, FHtreeNode<E> sb, FHtreeNode<E> chld, 
            FHtreeNode<E> prv,boolean dltd)
    {
        super(d, sb, chld, prv);
        deleted = dltd;
    }
    
    
    
    //---simple accessors and mutators declared inline
    public boolean isDeleted() { return deleted; }
    public FHsdTreeNode<E> getSibling() { return (FHsdTreeNode<E>) this.sib; }
    public FHsdTreeNode<E> getFirstChild()  
    { return (FHsdTreeNode<E>) firstChild; }
    public FHsdTreeNode<E> getPrevious()  { return (FHsdTreeNode<E>) prev; }
    public FHsdTreeNode<E> getRoot()  { return (FHsdTreeNode<E>) myRoot; }
    public void setDeleted(boolean dltd) { this.deleted = dltd; }
    
    
    
    
    
    
    //setSibling is a mutator for the sibling member of the superclass 
    //FHtreeNode. Returns FALSE if the value of sibling parameter is 
    //null, otherwise sets sib to sibling and returns TRUE.
    public boolean setSibling(FHtreeNode<E> sibling)
    {
        if (sibling == null)
            return false;

         this.sib = sibling;
         return true;
    }
    
    
    
    
    
    
    
    //setFirstChild s a mutator for the firstChild member of the superclass
    //FHtreeNode. It takes a single FHtreeNode<E> child and returns false
    //if the chld paramter is null. Otherwise sets firstChild to child
    //and returns TRUE
    public boolean setFirstChild(FHtreeNode<E> child)
    {
        if (child == null)
            return false;

         this.firstChild = child;
         return true;
    }
    
    
    
    
    
    
    
    
    //setPrevious is a mutator for the prev member of the superclass
    //FHtreeNode. It takes a single FHtreeNode<E> previous and returns false
    //if the previous paramter is null. Otherwise sets prev to previous
    //and returns true
    public boolean setPrevious(FHtreeNode<E> previous)
    {
        if (previous == null)
            return false;

         this.prev = previous;
         return true;
    }
    
    
    
    
    
    
    
    
    
    //setRoot is a basic mutator for the myRoot member of the superclass
    //FHtreeNode. It takes a single FHtreeNode<E> parameter root and returns
    //false if the root parameter is null. Otherwise sets myRoot to root
    //and returns true
    public boolean setRoot(FHtreeNode<E> root)
    {
        if (root == null)
            return false;

         this.myRoot = root;
         return true;
    }


}

class FHtree<E> implements Cloneable
{
   protected int mSize;
   protected FHtreeNode<E> mRoot;
   
   public FHtree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() { mSize = 0; mRoot = null; }
   
   public FHtreeNode<E> find(E x) { return find(mRoot, x, 0); }
   public boolean remove(E x) { return remove(mRoot, x); }
   public void  display()  { display(mRoot, 0); }
   
   public < F extends Traverser< ? super E > > 
   void traverse(F func)  { traverse(func, mRoot, 0); }
   
   public FHtreeNode<E> addChild( FHtreeNode<E> treeNode,  E x )
   {
      // empty tree? - create a root node if user passes in null
      if (mSize == 0)
      {
         if (treeNode != null)
            return null; // error something's fishy.  treeNode can't right
         mRoot = new FHtreeNode<E>(x, null, null, null);
         mRoot.myRoot = mRoot;
         mSize = 1;
         return mRoot;
      }
      if (treeNode == null)
         return null; // error inserting into non_null tree with a null parent
      if (treeNode.myRoot != mRoot)
         return null;  // silent error, node does not belong to this tree

      // push this node into the head of the sibling list; adjust prev pointers
      FHtreeNode<E> newNode = new FHtreeNode<E>(x, 
         treeNode.firstChild, null, treeNode, mRoot);  // sb, chld, prv, rt
      treeNode.firstChild = newNode;
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      ++mSize;
      return newNode;  
   }
   
   public FHtreeNode<E> find(FHtreeNode<E> root, E x, int level)
   {
      FHtreeNode<E> retval;

      if (mSize == 0 || root == null)
         return null;

      if (root.data.equals(x))
         return root;

      // otherwise, recurse.  don't process sibs if this was the original call
      if ( level > 0 && (retval = find(root.sib, x, level)) != null )
         return retval;
      return find(root.firstChild, x, ++level);
   }
   
   public boolean remove(FHtreeNode<E> root, E x)
   {
      FHtreeNode<E> tn = null;

      if (mSize == 0 || root == null)
         return false;
     
      if ( (tn = find(root, x, 0)) != null )
      {
         removeNode(tn);
         return true;
      }
      return false;
   }
   
   protected void removeNode(FHtreeNode<E> nodeToDelete )
   {
      if (nodeToDelete == null || mRoot == null)
         return;
      if (nodeToDelete.myRoot != mRoot)
         return;  // silent error, node does not belong to this tree

      // remove all the children of this node
      while (nodeToDelete.firstChild != null)
         removeNode(nodeToDelete.firstChild);

      if (nodeToDelete.prev == null)
         mRoot = null;  // last node in tree
      else if (nodeToDelete.prev.sib == nodeToDelete)
         nodeToDelete.prev.sib = nodeToDelete.sib; // adjust left sibling
      else
         nodeToDelete.prev.firstChild = nodeToDelete.sib;  // adjust parent

      // adjust the successor sib's prev pointer
      if (nodeToDelete.sib != null)
         nodeToDelete.sib.prev = nodeToDelete.prev;
      mSize--;
   }
   
   public Object clone() throws CloneNotSupportedException
   {
      FHtree<E> newObject = (FHtree<E>)super.clone();
      newObject.clear();  // can't point to other's data

      newObject.mRoot = cloneSubtree(mRoot);
      newObject.mSize = mSize;
      newObject.setMyRoots(newObject.mRoot);
      
      return newObject;
   }
   
   private FHtreeNode<E> cloneSubtree(FHtreeNode<E> root)
   {
      FHtreeNode<E> newNode;
      if (root == null)
         return null;

      // does not set myRoot which must be done by caller
      newNode = new FHtreeNode<E>
      (
         root.data, 
         cloneSubtree(root.sib), cloneSubtree(root.firstChild),
         null
      );
      
      // the prev pointer is set by parent recursive call ... this is the code:
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      if (newNode.firstChild != null)
         newNode.firstChild.prev = newNode;
      return newNode;
   }
   
   // recursively sets all myRoots to mRoot
   private void setMyRoots(FHtreeNode<E> treeNode)
   {
      if (treeNode == null)
         return;

      treeNode.myRoot = mRoot;
      setMyRoots(treeNode.sib);
      setMyRoots(treeNode.firstChild);
   }
   
   // define this as a static member so recursive display() does not need
   // a local version
   final static String blankString = "                                    ";
   
   // let be public so client can call on subtree
   public void  display(FHtreeNode<E> treeNode, int level) 
   {
      String indent;

      // stop runaway indentation/recursion
      if  (level > (int)blankString.length() - 1)
      {
         System.out.println( blankString + " ... " );
         return;
      }
      
      if (treeNode == null)
         return;

      indent = blankString.substring(0, level);

      // pre-order processing done here ("visit")
      System.out.println( indent + treeNode.data ) ;
      
      // recursive step done here
      display( treeNode.firstChild, level + 1 );
      if (level > 0 )
         display( treeNode.sib, level );
   }
      
   // often helper of typical public version, but also callable by on subtree
   public <F extends Traverser<? super E>> 
   void traverse(F func, FHtreeNode<E> treeNode, int level)
   {
      if (treeNode == null)
         return;

      func.visit(treeNode.data);
      
      // recursive step done here
      traverse( func, treeNode.firstChild, level + 1);
      if (level > 0 )
         traverse( func,  treeNode.sib, level);
   }
}



class FHtreeNode<E>
{
   // use protected access so the tree, in the same package, 
   // or derived classes can access members 
   protected FHtreeNode<E> firstChild, sib, prev;
   protected E data;
   protected FHtreeNode<E> myRoot;  // needed to test for certain error

   public FHtreeNode( E d, FHtreeNode<E> sb, FHtreeNode<E> chld, 
           FHtreeNode<E> prv )
   {
      firstChild = chld; 
      sib = sb;
      prev = prv;
      data = d;
      myRoot = null;
   }
   
   public FHtreeNode()
   {
      this(null, null, null, null);
   }
   
   public E getData() { return data; }

   // for use only by FHtree (default access)
   protected FHtreeNode( E d, FHtreeNode<E> sb, FHtreeNode<E> chld, 
      FHtreeNode<E> prv, FHtreeNode<E> root )
   {
      this(d, sb, chld, prv);
      myRoot = root;
   }
}


interface Traverser<E>
{
   public void visit(E x);
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
   
    //For equality
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (errorFlag ? 1231 : 1237);
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        result = prime * result + value;
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (errorFlag != other.errorFlag)
            return false;
        if (suit != other.suit)
            return false;
        if (value != other.value)
            return false;
        return true;
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

//----------------Run of Assignment 9: Option B1 B2 Extra Credit---------------
/*
Starting tree empty? true

//====Main Run 1==============

------------ Loaded Tree ----------------- 

..room
 ..table
  ..south west leg
  ..south east leg
  ..north west leg
  ..north east leg
 ..Miguel the human
  ..torso
   ..right arm
    ..right hand
     ..pinky
     ..ring finger
     ..middle finger
     ..index finger
     ..thumb
   ..left arm
    ..left hand
     ..pinky
     ..ring finger
     ..middle finger
     ..index finger
     ..thumb
 ..Lily the canine
  ..torso
   ..wagging tail
   ..spare mutant paw
   ..left rear paw
   ..right rear paw
   ..left front paw
   ..right front paw

------------ Virtual (soft) Tree ----------- 

..room
 ..table
  ..south west leg
  ..south east leg
  ..north west leg
  ..north east leg
 ..Lily the canine
  ..torso
   ..wagging tail
   ..left rear paw
   ..right rear paw
   ..left front paw
   ..right front paw

----------- Physical (hard) Display ---------- 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human (D)
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw (D)
   left rear paw
   right rear paw
   left front paw
   right front paw
------- Testing Sizes (compare with above) ----- 

virtual (soft) size: 13
physiical (hard) size: 30
------------ Collecting Garbage -------------- 

found soft-deleted nodes? true
immediate collect again? false
--------- Hard Display after garb col --------- 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw
Semi-deleted tree empty? false

Completely-deleted tree empty? true


=========Main Run 2 with Integers================

------------ Loaded Tree ----------------- 
..6
 ..-30
  ..2141
  ..-314
  ..314
 ..12
  ..41
   ..34
  ..2232
  ..542
 ..33

------------ Virtual (soft) Tree --------------
..6
 ..-30
  ..2141
  ..-314
 ..12
  ..2232
  ..542
 ..33

------------ Physical (hard) Display --------- 
6
 -30
  2141
  -314
  314 (D)
 12
  41 (D)
   34
  2232
  542
 33
------- Testing Sizes (compare with above)------ 
virtual (soft) size: 8
physical (hard) size: 11
--------- Collecting Garbage -------------- 

found soft-deleted nodes? true
immediate collect again? false
--------- Hard Display after garb col --------- 
6
 -30
  2141
  -314
 12
  2232
  542
 33
Semi-deleted tree empty? false

Completely-deleted tree empty? true



=======Main Run 3 with Cards==============

------------ Loaded Tree ----------------- 
..A of Hearts
 ..[invalid]
  ..T of Clubs
   ..T of Diamonds
   ..T of Hearts
   ..T of Spades
 ..K of Hearts
  ..A of Hearts
  ..Q of Hearts
   ..9 of Spades
   ..3 of Spades
   ..6 of Spades
   ..5 of Spades
  ..J of Hearts
 ..A of Spades

------Loaded Clone Tree ----------------
..A of Hearts
 ..[invalid]
  ..T of Clubs
   ..T of Diamonds
   ..T of Hearts
   ..T of Spades
 ..K of Hearts
  ..A of Hearts
  ..Q of Hearts
   ..9 of Spades
   ..3 of Spades
   ..6 of Spades
   ..5 of Spades
  ..J of Hearts
 ..A of Spades

------------ Virtual (soft) Tree + clone ------- 
..A of Hearts
 ..[invalid]
  ..T of Clubs
   ..T of Diamonds
   ..T of Spades
 ..K of Hearts
  ..A of Hearts
  ..J of Hearts
 ..A of Spades

---clone---
..A of Hearts
 ..[invalid]
  ..T of Clubs
   ..T of Diamonds
   ..T of Spades
 ..K of Hearts
  ..A of Hearts
  ..J of Hearts
 ..A of Spades

------------ Physical tree, real then clone------ 
A of Hearts
 [invalid]
  T of Clubs
   T of Diamonds
   T of Hearts (D)
   T of Spades
 K of Hearts
  A of Hearts
  Q of Hearts (D)
   9 of Spades
   3 of Spades
   6 of Spades
   5 of Spades
  J of Hearts
 A of Spades

------
A of Hearts
 [invalid]
  T of Clubs
   T of Diamonds
   T of Hearts (D)
   T of Spades
 K of Hearts
  A of Hearts
  Q of Hearts (D)
   9 of Spades
   3 of Spades
   6 of Spades
   5 of Spades
  J of Hearts
 A of Spades
------- Testing Sizes (compare with above)------- 
virtual (soft) size: 9
physical (hard) size: 15
clone (hard) size: 15
------------ Collecting Garbage ----------------- 
found soft-deleted nodes? true
immediate collect again? false
--------- Hard Display after garb col ------------ 
A of Hearts
 [invalid]
  T of Clubs
   T of Diamonds
   T of Spades
 K of Hearts
  A of Hearts
  J of Hearts
 A of Spades
Semi-deleted tree empty? false
Completely-deleted tree empty? true

---final tree----
A of Hearts (D)
 [invalid]
  T of Clubs
   T of Diamonds
   T of Spades
 K of Hearts
  A of Hearts
  J of Hearts
 A of Spades

 
 
 *//////////////////////////////////////
