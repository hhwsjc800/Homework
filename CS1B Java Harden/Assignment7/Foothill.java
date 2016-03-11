//============================================================================
// Name        : CS1B (Java) Assignment 7: Boolean Function Class
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Feb 24, 2016
// Instructor  : Harden
// Description : This program provides the framework for a seven segment 
//               display. It consists of two classes and a derived class.
//               Its first class is BooleanFunc, which provides truth table
//               functionality and will be used to record the truth tables
//               of the seven segment display. MultiSegmentLogic and 
//               SevenSegmentLogic, its child, make use of the BooleanFunc 
//               class to define the truth tables of the seven segment
//               display in convenient forms. Main consists of tests of
//               these classes to make sure that they function correctly.
//============================================================================

class Foothill
{
    public static void main(String[] args)
    {
        BooleanFunc segA, segB, segC;
        segA = new BooleanFunc();
        segB = new BooleanFunc(13);
        segC = new BooleanFunc(100, true);

        int evenFunc[] = { 0, 2, 4, 6, 8, 10, 12, 14 }, inputX;
        int greater9Func[] = { 10, 11, 12, 13, 14, 15 };
        int greater3Func[] = { 0, 1, 2, 3 };

        //mutator tests for BooleanFunc class
        System.out.println("===Mutator Tests for BooleanFunc Class===\n");
        if (segA.setTruthTableUsingTrue(evenFunc))
            System.out.print("set segA\n");
        else
            System.out.print("failed to set segA\n");
        
        if (segB.setTruthTableUsingTrue(greater9Func))
            System.out.print("set segB\n");
        else
            System.out.print("failed set segB\n");
        if (segC.setTruthTableUsingFalse(greater3Func))
            System.out.print("set segC\n");
        else
            System.out.print("failed set segC\n");

        System.out.print("===Overall Tests for BooleanFunc Class===\n");
        
        // testing class BooleanFunc
        System.out.print("\nbefore eval()\n");
        System.out.print(
            "\n  A(x) = "
           + segA.getState()
           + "\n  B(x) = "
           + segB.getState()
           + "\n  C(x) = "
           + segC.getState()
           + "\n\n"
           );

        System.out.print( "looping with eval()\n");
        for ( inputX = 0; inputX < 16; inputX++ )
        {
           segA.eval( inputX );
           segB.eval( inputX );
           segC.eval( inputX );
           System.out.print(
               "Input: " + inputX
              + "\n  A(x) = "
              + segA.getState()
              + "\n  B(x) = "
              + segB.getState()
              + "\n  C(x) = "
              + segC.getState()
              +"\n\n"
              );
        }

        //Tests of copy constructor and assignment operator
        System.out.print("\n---Testing copy ctor and assignment operator:\n ");
        System.out.print("instantiating BooleanFunc segACopy \n");
        BooleanFunc segACopy;
        try
        {
            segACopy = segA.clone();
        } 
        catch (CloneNotSupportedException e)
        {
            System.out.println("caught clone exception");
            segACopy = new BooleanFunc();
        }

        System.out.print( "looping with eval()\n");
        for ( inputX = 0; inputX < 16; inputX++ )
        {
           segA.eval( inputX );
           segACopy.eval( inputX );

           System.out.print(
               "Input: " + inputX
              + "\n  ACopy(x) = "
              + segACopy.getState()
              + "\n  A(x) = "
              + segA.getState()
              +"\n\n"
              );
        }

        System.out.print("Assignment op, segA = segC \n");
        segA = segC;
        System.out.print( "looping with eval()\n");
        for ( inputX = 0; inputX < 16; inputX++ )
        {
           segA.eval( inputX );
           segC.eval( inputX );
           System.out.print(
               "Input: " + inputX
              + "\n  A(x) = "
              + segA.getState()
              + "\n  C(x) = "
              + segC.getState()
              +"\n\n"
              );
        }

        System.out.print("\n*******End BooleanFunc class Tests*****\n\n");

        //MSL and SSL Class Tests
        System.out.print("\n---Begin MSL/ SSLogic class Tests---\n\n");
        int inputY, k;
        MultiSegmentLogic smallMulti, badMulti, copyBadMulti;
        SevenSegmentLogic my7Seg, myCopy;
        
        smallMulti = new MultiSegmentLogic(3);
        badMulti = new MultiSegmentLogic(-1);
        try
        {
            copyBadMulti = badMulti.clone();
        } 
        catch (CloneNotSupportedException e)
        {
  
            System.out.println("caught exeception for MSL clone");
            copyBadMulti = new MultiSegmentLogic(7);
        }
        
        
        my7Seg = new SevenSegmentLogic();
        try
        {
            myCopy =  (SevenSegmentLogic) my7Seg.clone();
        } 
        catch (CloneNotSupportedException e)
        {
            System.out.println("caught exeception for MSL clone");
            myCopy = my7Seg;
        }

        //Testing seven segment display truth table
        System.out.print("===Seven Segment Display truth table=== \n\n");
        System.out.print("Input#: | A | B | C | D | E | F | G |\n");
        for ( inputY = 0; inputY < 16; inputY++ )
        {
           myCopy.eval( inputY );
           System.out.print(inputY + "      | ") ;
           for (k = 0; k < 7; k++)
           {
              if (myCopy.getValOfSeg(k))
                  System.out.print( "T | ");
              else
                  System.out.print("F | ");
           }
           System.out.print("\n");
        }

        //Testing SSL getValOfSeg
        System.out.print("\n==Tests for SSL getValOfSeg==\n\n");
        System.out.print("state of seg5 for last eval:\n");
        if (myCopy.getValOfSeg(5))
            System.out.print("true (expected)\n");
        else
            System.out.print("false\n");

        System.out.print("\nState of Segment 4 for my7Seg:\n");
        if (my7Seg.getValOfSeg(4))
            System.out.print("true (Expected!)\n");
        else
            System.out.print("false\n");

        System.out.print("\nState of Segment 100 for my7Seg:\n");
        if (my7Seg.getValOfSeg(100))
            System.out.print("true\n");
        else
            System.out.print("false/error (expected)\n");

        //Testing MSL setSegment
        System.out.print("\n--Tests for MultiSegmentLogic Mutators---\n");
        if (smallMulti.setSegment(0, segA))
            System.out.print("set smallMulti seg0 to segA\n");
        if (smallMulti.setSegment(1, segB))
            System.out.print("set smallMulti seg1 to segB\n");
        if (smallMulti.setSegment(2, segC))
            System.out.print("set smallMulti seg2 to segC\n");
        if (!smallMulti.setSegment(500, segA))
            System.out.print("failed to set segment 500 of smallMulti\n\n");

        if (!badMulti.setSegment(4, segB))
            System.out.print("Failed to set badMulti seg4\n");
        if (!badMulti.setSegment(0, segA))
            System.out.print("failed to set badMulti seg0\n");

        if (badMulti.setNumSegs(1))
            System.out.print("Set numSegs of badMulti to 1(expected)\n");
        else
            System.out.print("Failed to set numSegs of badMulti\n");

        if (badMulti.setSegment(0, segB))
            System.out.print("Set segment 0 of badMulti segB(Expected)\n\n");

        //Testing eval function of MSL class
        System.out.print("\n---Tests for eval function of MSL class---\n");
        my7Seg.eval(4);
        System.out.print(" my7seg.eval(4) should equal to Input 4 of table\n");
        for (k = 0; k < 7; k++)
        {
           if (my7Seg.getValOfSeg(k))
               System.out.print("T | ");
           else
               System.out.print( "F | ");
       }
        System.out.print("\n\n");

        //Testing copy ctor of MSL class
        System.out.print("\n---Tests of MSL class copy ctor---\n ");
        
        //Copy ctor tests
        System.out.print("Testing copy ctor with copy of original badMulti" +
                         ", numSegs = 0)\n");
        if (copyBadMulti.setSegment(7, segB))
            System.out.print("Set segment7 of copyBadMulti to segB\n\n");
        else
            System.out.print("Failed to set segment 7 of copyBadMulti" +
                             "(expected)\n\n");

        System.out.print("****END OF TESTS****\n");

        return;
    }
}

//CS 2B - LOCEFF
//Assignment #6 - Logic Gates and Circuits ============

class BooleanFunc implements Cloneable
{
    public static final int MAX_TABLE_FOR_CLASS = 65536;// 16 binary inputs
    public static final int DEFAULT_TABLE_SIZE = 16;
    public static final boolean DEFAULT_TABLE_VAL = false;
    public static final int MIN_TABLE_SIZE = 0;
    
    private int tableSize;
    private boolean[] truthTable;
    private boolean evalReturnIfError;
    private boolean state;
 
    public boolean getState(){return state;} //simple getter

    //Default constructor, takes no parameters. It sets tableSize
    //and evalReturnIfError to their default values and initializes the 
    //truthTable data member as well as state. 
    public BooleanFunc()
    {
        tableSize = DEFAULT_TABLE_SIZE;
        state = evalReturnIfError = DEFAULT_TABLE_VAL;
        initTable(tableSize);
    }
    
    
    
    
    
    
    //Overloaded class constructor which takes in a single int parameter,
    //tableSize. It checks for valid tableSize and fixes tableSize if the
    //tableSize is invalid. Afterward, sets the private data member 
    //tableSize to the parameter tableSize and allocates the truthTable
    //member with the helper function. Finally, sets state and 
    //evalReturnIfError to their default values.
    public BooleanFunc(int tableSize)
    {
        if(!validTableSize(tableSize))
            tableSize = DEFAULT_TABLE_SIZE;
        
        this.tableSize = tableSize;
        initTable(tableSize);
        state = evalReturnIfError = DEFAULT_TABLE_VAL;
        
    }
    
    
    
    
    
    
    
    //Overloaded class constructor which takes in anint parameter tableSize and
    //boolean evalReturnIfError. It checks for valid tableSize and fixes param 
    //tableSize if it is invalid. Afterward, sets the private data member 
    //tableSize to the parameter tableSize and allocates the truthTable
    //member with the helper function. Finally, sets state and 
    //evalReturnIfError to the parameter evalReturnIfError value.
    public BooleanFunc(int tableSize, boolean evalReturnIfError)
    {
        if (!validTableSize(tableSize))
            tableSize = DEFAULT_TABLE_SIZE;
        this.tableSize = tableSize;
        
        initTable(tableSize);
        this.evalReturnIfError = evalReturnIfError;
        this.state = evalReturnIfError;
        
    }
    
    
    
    
    
    
    
    
    
    //initTable takes in a single int, tableSize, then uses tableSize to 
    //properly initialize the truthTable data member array. First, it checks
    //for valid tableSize. If an invalid size is detected, it returns FALSE.
    //Otherwise, the truthTable member is set accordingly and returns
    //TRUE.
    private boolean initTable(int tableSize)
    {
        if (!validTableSize(tableSize))
            return false;
        
        this.tableSize = tableSize;
        truthTable = new boolean[tableSize];
        
        setTableDefault(DEFAULT_TABLE_VAL);
        return true;
        
    }
    
    
    
    
    
    //setTruthTableUsingTrue takes a single int array, inputsThatProduceTrue.
    //If the size of inputsThatProduceTrue is incompatible with the tableSize,
    //the method uses a helper to detect this and returns FALSE. Otherwise, 
    //setTruthTableUsingTrue sets all truthTable members to FALSE and then
    //goes through each element of inputsThatProduceTrue. If the element of
    //inputsThatProduceTrue is valid, it sets the corresponding index of
    //truthTable to TRUE. Finally, the method returns TRUE.
    public boolean setTruthTableUsingTrue(int inputsThatProduceTrue[])
    {
        if (!validArraySize(inputsThatProduceTrue.length))
            return false;
        
        setTableDefault(false);
        
        for (int i = 0; i < inputsThatProduceTrue.length; i++)
        {
            if (inputsThatProduceTrue[i] < tableSize &&
                inputsThatProduceTrue[i] >= 0)
                truthTable[inputsThatProduceTrue[i]] = true;
        }
        
        return true;
    }
    
    
    
    
    
    
    
    
    //setTruthTableUsingFalse takes a single int array, inputsThatProduceFalse.
    //If the size of inputsThatProduceFalse is incompatible with the tableSize,
    //the method uses a helper to detect this and returns FALSE. Otherwise, 
    //inputsThatProduceFalse sets all truthTable members to TRUE and then
    //goes through each element of inputsThatProduceFalse. If the element of
    //inputsThatProduceFalse is valid, it sets the corresponding index of
    //truthTable to FALSE. Finally, the method returns TRUE.
    public boolean setTruthTableUsingFalse(int inputsThatProduceFalse[])
    {
        if (!validArraySize(inputsThatProduceFalse.length))
            return false;
        
        setTableDefault(true);
        
        for (int i = 0; i < inputsThatProduceFalse.length; i++)
        {
            if (inputsThatProduceFalse[i] < tableSize &&
                inputsThatProduceFalse[i] >= 0)
                truthTable[inputsThatProduceFalse[i]] = false;
        }
        
        return true;
    }
    
    
    
    
    
    
    
    //eval takes in a single int, input. If input is not valid, it sets the
    //state to the evalReturnIfError member. Otherwise, it sets state equal
    //to the index of the truthTable corresponding to the input. Finally,
    //returns state.
    public boolean eval(int input)
    {
        if (input < 0 || input >= tableSize)
            state = evalReturnIfError;
        else
            state = truthTable[input];
        
        return state;
    }
    
    
    
    
    
    
    //Copy constructor. Assures deep data of tableSize is copied between
    //objects.
    public BooleanFunc clone() throws CloneNotSupportedException
    {

       BooleanFunc newBooleanFunc = (BooleanFunc)super.clone();
       newBooleanFunc.truthTable = new boolean[this.tableSize];
       
       for (int i = 0; i < tableSize; i++)
           newBooleanFunc.truthTable[i] = this.truthTable[i];
       
       return newBooleanFunc;
    }
    //---private helper functions
    
    //validArraySize takes in a single int, arraySize. If arraySize is negative
    //or if the arraySize is larger than the table itself, returns FALSE.
    //Otherwise, returns TRUE.
    boolean validArraySize(int arraySize)
    {
        if (arraySize < 0 || arraySize > tableSize)
            return false;
        
        return true;
    }
    
    
    
    
    
    
    
    //validTableSize takes in a single int parameter, tableSize. It checks 
    //whether or not tableSize is a valid input and returns FALSE if the 
    //parameter tableSize is invalid. Otherwise, returns true.
    private boolean validTableSize(int tableSize)
    {
        if (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_FOR_CLASS)
            return false;
        
        return true;
    }
    
    
    
    
    
    
    
    
    //setTableDefault takes in a boolean, tableVal, and runs through each 
    //element of the truthTable private member, setting each index of the 
    //truthTable array to tableVal. Returns nothing.
    private void setTableDefault(boolean tableVal)
    {
        for (int k = 0; k < tableSize; k++)
            truthTable[k] = tableVal;
    }
    
}

class MultiSegmentLogic implements Cloneable
{
    public static final int DEFAULT_NUM_SEGS = 0;
    
    protected int numSegs;
    protected BooleanFunc segs[];
    
    //Default constructor MultiSegmentLogic for the class. Takes no parameters
    //and simply sets the numSegs to the default numSegs using the helper 
    //function.
    public MultiSegmentLogic()
    {
        setNumSegs(DEFAULT_NUM_SEGS);
    }
    
    
    
    
    
    
    
    
    //Overloaded constructor MultiSegmentLogic which takes a single int param,
    //numSegs. Checks for the validity of numSegs and tries to set numSegs
    //field member to the param. If not, sets numSegs to default 
    //numSegs.
    public MultiSegmentLogic(int numSegs)
    {
        if (!setNumSegs(numSegs))
            setNumSegs(DEFAULT_NUM_SEGS);
        
    }
    
    
    
    
    
    
    
    //setNumSegs is a boolean function that takes a single int parameter,
    //numSegs. Checks for the validity of the numSegs parameter first.
    //If the numSegs param is invalid, the function returns FALSE without
    //changing anything. Otherwise, the segs array size is set to 
    //numSegs and the private member numSegs is updated to the value
    //of the parameter. Returns TRUE after all of this is done.
    public boolean setNumSegs(int numSegs)
    {
        if (numSegs < 0)
            return false;
        
        initSegArray(numSegs);
        this.numSegs = numSegs;
        
        return true;
    }
    
    
    
    
    
    
    
    
    //Takes two parameters, int segNum and a BooleanFunc funcForThisSeg.
    //If the segNum parameter is invalid, returns FALSE immediately.
    //Otherwise, updates the index of the segs array corresponding to the
    //passed param segNum with funcForThisSeg via direct assignment and returns
    //TRUE.
    public boolean setSegment(int segNum, BooleanFunc funcForThisSeg)
    {
        if (segNum < 0 || segNum >= numSegs)
            return false;
        
        segs[segNum] = funcForThisSeg;
        return true;
    }
    
    
    
    
    
    
    
    
    
    //eval takes a single int parameter, input, and simply goes through the
    //segs[] array passing each individual BooleanFunc's eval method
    //with the passed parameter input.
    public void eval(int input)
    {
        for (int i = 0; i < numSegs; i++)
            segs[i].eval(input);
    }
    
    
    
    
    
    
    
    
    //Copy constructor. Assures deep data of segs is copied between
    //objects.
    public MultiSegmentLogic clone() throws CloneNotSupportedException
    {

       MultiSegmentLogic newMSL = (MultiSegmentLogic)super.clone();
       newMSL.segs = new BooleanFunc[this.numSegs];
       
       for (int i = 0; i < numSegs; i++)
           newMSL.segs[i] = this.segs[i];
       
       return newMSL;
    }
    
    
    
    
    
    
    
    //--helper functions
    
    //Initializes the segs array. Takes in a single parameter, numSegs, then 
    //sets the segs[] array to be the size of numSegs.
    protected void initSegArray(int numSegs)
    {
        segs = new BooleanFunc[numSegs];
    }




    
}

class SevenSegmentLogic extends MultiSegmentLogic
{
    private static boolean sevenSegmentsLoaded = false;
    private static BooleanFunc sevenSegBoolFuncs[];
    
    public SevenSegmentLogic()
    {
        super(7);
        loadSevenSegmentBooleans();
    }
    
    
    
    
    
    //Takes a single int, seg. If the seg number is invalid, returns FALSE
    //immediately. Otherwise, returns the getState of the BooleanFunc 
    //in the segs[] arraycorresponding to the passed index seg as a boolean.
    public boolean getValOfSeg(int seg)
    {
        if (seg < 0 || seg >= numSegs)
            return false;
        
        return segs[seg].getState();
        
    }
    
    
    private void loadSevenSegmentBooleans()
    {
        if (!sevenSegmentsLoaded)
        {
           sevenSegBoolFuncs = new BooleanFunc[7];
           for (int i = 0; i < sevenSegBoolFuncs.length; i++)
               sevenSegBoolFuncs[i] = new BooleanFunc();
           //set with false
           int aFunc[] = { 1, 4, 11, 13 };
           int bFunc[] = { 5, 6, 11, 12, 14, 15 };
           int cFunc[] = { 2, 12, 14, 15 };
           int dFunc[] = { 1, 4, 7, 10, 15};
           int eFunc[] = { 1, 3, 4, 5, 7, 9 };
           int fFunc[] = { 1, 2, 3, 7, 13 };
           int gFunc[] = { 0, 1, 7, 12 };

           sevenSegBoolFuncs[0].setTruthTableUsingFalse(aFunc);
           sevenSegBoolFuncs[1].setTruthTableUsingFalse(bFunc);
           sevenSegBoolFuncs[2].setTruthTableUsingFalse(cFunc);
           sevenSegBoolFuncs[3].setTruthTableUsingFalse(dFunc);
           sevenSegBoolFuncs[4].setTruthTableUsingFalse(eFunc);
           sevenSegBoolFuncs[5].setTruthTableUsingFalse(fFunc);
           sevenSegBoolFuncs[6].setTruthTableUsingFalse(gFunc);

           sevenSegmentsLoaded = true;
        }

        for (int i = 0; i < 7; i++)
           setSegment(i, sevenSegBoolFuncs[i]);
    }



}

/* ------------------------- assignment7 run ---------------------------
===Mutator Tests for BooleanFunc Class===

set segA
set segB
set segC
===Overall Tests for BooleanFunc Class===

before eval()

  A(x) = false
  B(x) = false
  C(x) = true

looping with eval()
Input: 0
  A(x) = true
  B(x) = false
  C(x) = false

Input: 1
  A(x) = false
  B(x) = false
  C(x) = false

Input: 2
  A(x) = true
  B(x) = false
  C(x) = false

Input: 3
  A(x) = false
  B(x) = false
  C(x) = false

Input: 4
  A(x) = true
  B(x) = false
  C(x) = true

Input: 5
  A(x) = false
  B(x) = false
  C(x) = true

Input: 6
  A(x) = true
  B(x) = false
  C(x) = true

Input: 7
  A(x) = false
  B(x) = false
  C(x) = true

Input: 8
  A(x) = true
  B(x) = false
  C(x) = true

Input: 9
  A(x) = false
  B(x) = false
  C(x) = true

Input: 10
  A(x) = true
  B(x) = true
  C(x) = true

Input: 11
  A(x) = false
  B(x) = true
  C(x) = true

Input: 12
  A(x) = true
  B(x) = true
  C(x) = true

Input: 13
  A(x) = false
  B(x) = false
  C(x) = true

Input: 14
  A(x) = true
  B(x) = false
  C(x) = true

Input: 15
  A(x) = false
  B(x) = false
  C(x) = true


---Testing copy ctor and assignment operator:
 instantiating BooleanFunc segACopy 
looping with eval()
Input: 0
  ACopy(x) = true
  A(x) = true

Input: 1
  ACopy(x) = false
  A(x) = false

Input: 2
  ACopy(x) = true
  A(x) = true

Input: 3
  ACopy(x) = false
  A(x) = false

Input: 4
  ACopy(x) = true
  A(x) = true

Input: 5
  ACopy(x) = false
  A(x) = false

Input: 6
  ACopy(x) = true
  A(x) = true

Input: 7
  ACopy(x) = false
  A(x) = false

Input: 8
  ACopy(x) = true
  A(x) = true

Input: 9
  ACopy(x) = false
  A(x) = false

Input: 10
  ACopy(x) = true
  A(x) = true

Input: 11
  ACopy(x) = false
  A(x) = false

Input: 12
  ACopy(x) = true
  A(x) = true

Input: 13
  ACopy(x) = false
  A(x) = false

Input: 14
  ACopy(x) = true
  A(x) = true

Input: 15
  ACopy(x) = false
  A(x) = false

Assignment op, segA = segC 
looping with eval()
Input: 0
  A(x) = false
  C(x) = false

Input: 1
  A(x) = false
  C(x) = false

Input: 2
  A(x) = false
  C(x) = false

Input: 3
  A(x) = false
  C(x) = false

Input: 4
  A(x) = true
  C(x) = true

Input: 5
  A(x) = true
  C(x) = true

Input: 6
  A(x) = true
  C(x) = true

Input: 7
  A(x) = true
  C(x) = true

Input: 8
  A(x) = true
  C(x) = true

Input: 9
  A(x) = true
  C(x) = true

Input: 10
  A(x) = true
  C(x) = true

Input: 11
  A(x) = true
  C(x) = true

Input: 12
  A(x) = true
  C(x) = true

Input: 13
  A(x) = true
  C(x) = true

Input: 14
  A(x) = true
  C(x) = true

Input: 15
  A(x) = true
  C(x) = true


*******End BooleanFunc class Tests*****


---Begin MSL/ SSLogic class Tests---

===Seven Segment Display truth table=== 

Input#: | A | B | C | D | E | F | G |
0      | T | T | T | T | T | T | F | 
1      | F | T | T | F | F | F | F | 
2      | T | T | F | T | T | F | T | 
3      | T | T | T | T | F | F | T | 
4      | F | T | T | F | F | T | T | 
5      | T | F | T | T | F | T | T | 
6      | T | F | T | T | T | T | T | 
7      | T | T | T | F | F | F | F | 
8      | T | T | T | T | T | T | T | 
9      | T | T | T | T | F | T | T | 
10      | T | T | T | F | T | T | T | 
11      | F | F | T | T | T | T | T | 
12      | T | F | F | T | T | T | F | 
13      | F | T | T | T | T | F | T | 
14      | T | F | F | T | T | T | T | 
15      | T | F | F | F | T | T | T | 

==Tests for SSL getValOfSeg==

state of seg5 for last eval:
true (expected)

State of Segment 4 for my7Seg:
true (Expected!)

State of Segment 100 for my7Seg:
false/error (expected)

--Tests for MultiSegmentLogic Mutators---
set smallMulti seg0 to segA
set smallMulti seg1 to segB
set smallMulti seg2 to segC
failed to set segment 500 of smallMulti

Failed to set badMulti seg4
failed to set badMulti seg0
Set numSegs of badMulti to 1(expected)
Set segment 0 of badMulti segB(Expected)


---Tests for eval function of MSL class---
 my7seg.eval(4) should equal to Input 4 of table
F | T | T | F | F | T | T | 


---Tests of MSL class copy ctor---
 Testing copy ctor with copy of original badMulti, numSegs = 0)
Failed to set segment 7 of copyBadMulti(expected)

****END OF TESTS****

*/
