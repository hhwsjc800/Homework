//============================================================================
// Name        : CS1B (Java) Assignment 8: SevenSeg Display on Console
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Mar 2, 2016
// Instructor  : Harden
// Description : This program provides the console functionality for a 7seg
//               display. It adds two classes to the framework completed
//               in the previous assignment: SevenSegmentImage and 
//               SevenSegmentDisplay. SevenSegmentImage allows other classes
//               to draw line segments on a 2d grid. Users can see these
//               segments using public methods of the SevenSegmentImage class. 
//               SevenSegmentDisplay allows the client to create and show
//               7 segment characters at varying sizes. Users can see the
//               digits 0-9 as well as the letters A-F using 7 line segments.
//============================================================================

class Foothill
{
    public static void main(String[] args)
    {
        SevenSegmentImage ssi, ssiCopy;
        ssi = new SevenSegmentImage();
        
        ssi.setSize(7, 9);
        System.out.println("showing segments a,b,c,d progressively:\n");

        System.out.println("---a:");
        ssi.turnOnCellsForSegment('a');
        ssi.display();
        System.out.println("---a,b:");
        ssi.turnOnCellsForSegment('b');
        ssi.display();
        System.out.println("---a,b,c:");
        ssi.turnOnCellsForSegment('c');
        ssi.display();
        System.out.println("---a,b,c,d:");
        ssi.turnOnCellsForSegment('d');
        ssi.display();

        System.out.println("clearing grid, showing e,f,g progressively:\n");
        ssi.clearImage();
        System.out.println("---e:");
        ssi.turnOnCellsForSegment('e');
        ssi.display();
        System.out.println("---e, f:");
        ssi.turnOnCellsForSegment('f');
        ssi.display();
        System.out.println("---e,f, g:");
        ssi.turnOnCellsForSegment('g');
        ssi.display();

        System.out.println("clearing grid, showing two invalid inputs:\n");
        ssi.clearImage();
        ssi.turnOnCellsForSegment('x');
        ssi.display();
        ssi.turnOnCellsForSegment('3');
        ssi.display();

        System.out.println("testing copy ctor and assignment operator");
        ssi.clearImage();
        ssi.turnOnCellsForSegment('a');
        ssi.turnOnCellsForSegment('g');
        ssi.turnOnCellsForSegment('b');
        ssi.turnOnCellsForSegment('f');

        ssiCopy = ssi;

        System.out.println("the following should be identical:\n");
        ssiCopy.display();
        System.out.print("\n");
        ssi.display();

        System.out.println("\n\n===SevenSegDisplay Tests===\n");

        SevenSegmentDisplay my7SegForCon = new SevenSegmentDisplay(15, 13);

        my7SegForCon.setSize(5, 5);
        for (int j = 0; j < 16; j++)
        {
           my7SegForCon.eval(j);
           my7SegForCon.loadConsoleImage();
           my7SegForCon.consoleDisplay();
           System.out.println();
        }

        my7SegForCon.setSize(9, 9);

        my7SegForCon.eval(0);
        my7SegForCon.loadConsoleImage();
        my7SegForCon.consoleDisplay();
        my7SegForCon.eval(13);
        my7SegForCon.loadConsoleImage();
        my7SegForCon.consoleDisplay();
        my7SegForCon.eval(13);
        my7SegForCon.loadConsoleImage();
        my7SegForCon.consoleDisplay();

        return;
    }
}

class SevenSegmentImage implements Cloneable
{
   public static final int MIN_HEIGHT = 5;
   public static final int MIN_WIDTH = 5;
   public static final int MAX_HEIGHT = 65;
   public static final int MAX_WIDTH = 41;
   public static final String DRAW_CHAR = "*";
   public static final String BLANK_CHAR = " ";
   
   private boolean[][] data;
   private int topRow, midRow, bottomRow, leftCol, rightCol;
   
   //Default constructor for SevenSegmentImage class. Creates an SSI object
   //with min width and min height, then initializes the boolean member
   //data.
   public SevenSegmentImage()
   {
       setSize(MIN_WIDTH, MIN_HEIGHT);
   }
   
   
   
   
   
   
   
   //Overloaded constructor for the SevenSegmentImage class. Takes two int
   //parameters, width and height. Filters for correct input of width
   //and height then sets the image dimensions according to the width and
   //height. If this fails to happen, the size is set according to min 
   //width and height instead.
   public SevenSegmentImage( int width, int height)
   {
       if ( !setSize( width, height ) )
           setSize( MIN_WIDTH, MIN_HEIGHT );

   }
   
   
   
   
   
   
   
   //setSize takes two int parameters, width and height. First, it checks
   //whether or not these are valid dimensions by use of the helper function
   //validateSize. If they are not, the functions returns false immediately.
   //Otherwise, we set the topRow, midRow, bottomRow, rightCol, and leftCol
   //field members according to the width and height. Finally, we use these
   //values to initialize the data[][] array with correct dimensions and
   //return TRUE.
   public boolean setSize( int width, int height )
   {
      if ( !validateSize( width, height ) )
         return false;

      topRow = 0;
      leftCol = 0;
      bottomRow = height - 1;
      rightCol = width - 1;
      midRow = bottomRow / 2;

      allocateCleanArray();
      return true;
   }
   
   
   
   
   
   //clearImage is a simple void function that clears the image of the 
   //sevenSegmentImage. It does this by going through each element of the
   //data[][] member and setting each index to FALSE. Assumes the data[][]
   //member is valid and instantiated correctly with correct dimensions.
   public void clearImage()
   {
       for (int i = 0; i <= bottomRow; i++)
           for (int j = 0; j <= rightCol; j++)
              data[i][j] = false;
   }
   
   
   
   
   
   
   
   //turnOnCellsForSegment takes a single char, segment, then converts it
   //to uppercase for use in a switch statement. Segment's value is then
   //used to draw the appropriate image on the SevenSegmentImage 
   //by use of the helper functions drawHorizontal and drawVertical.
   //In order to avoid a long if/else statement, switch was used.
   //Once drawing is complete, returns TRUE. turnOnCellsForSegment will return
   //FALSE for unrecognized segment input.
   public boolean turnOnCellsForSegment( char segment )
   {
      char displayLetter;
      displayLetter = Character.toUpperCase(segment);

      switch ( displayLetter )
      {
         case 'A':
            drawHorizontal( topRow );
            break;
         case 'G':
            drawHorizontal( midRow );
            break;
         case 'D':
            drawHorizontal( bottomRow );
            break;
         case 'E':
            drawVertical( leftCol, bottomRow, midRow );
            break;
         case 'F':
            drawVertical( leftCol, midRow, topRow );
            break;
         case 'B':
            drawVertical( rightCol, midRow, topRow );
            break;
         case 'C':
            drawVertical( rightCol, bottomRow, midRow );
            break;
         default:
            // otherwise something useless
            return false;
      }
      return true;
   }
   
   
   
  
   
   
   
   
   //Display is a simple void function that outputs the entire SevenSegment
   //Image to the console. It does this by looping through the entire 
   //data[][] array and outputting a blank character if the index at 
   //data is false. Otherwise, it outputs a draw character.
   public void display()
   {
       int numRows = bottomRow + 1;
       int numCols = rightCol + 1;

       String showGrid = new String();
       for (int i = 0; i < numRows; i++)
       {
          for (int j = 0; j < numCols; j++)
          {
             if (data[i][j])
                showGrid += DRAW_CHAR;
             else
                showGrid += BLANK_CHAR;
          }

          showGrid += "\n";
       }
       
       System.out.println(showGrid);

   }

   


   
   //validateSize takes in two int parameters, width and height. If either
   //width or height exceed the maximum or minimum values defined by the 
   //class for width and height, validateSize returns FALSE. Additionally,
   //to avoid asymmetryical SevenSegmentImage displays, width and height
   //must both be odd. Provided width and height are valid, returns TRUE.
   private boolean validateSize (int width, int height)
   {
       if (width < MIN_WIDTH || width > MAX_WIDTH || height < MIN_HEIGHT ||
           height > MAX_HEIGHT || width % 2 == 0 || height % 2 == 0)
           return false;
       return true;
       
   }
   
   
   
   
   
   
   

   //allocateCleanArray is a private helper function that initializes the
   //data[][] array private member using the values of topRow and rightCol.
   //Other filters should be used beforehand to make sure that 
   //allocateCleanArray does not receive any bad values. Because of such
   //a risk, allocateCleanArray is private to prevent client from 
   //instantiating the array with bad data.
   private void allocateCleanArray()
   {
       int height, width;

       height = bottomRow + 1;
       width = rightCol + 1;

       data = new boolean[height][width];
       clearImage();
   }
   
   
   
   
   
   
   
   

   
   //drawHorzontal is a helper function for the SevenSegmentImage class. It 
   //takes a single parameter, row, and sets every element of the 2d array 
   //data[][] corresponding to that row to TRUE. It returns if row is detected
   //to be invalid.
   private void drawHorizontal( int row )
   {
       if (row < topRow || row > bottomRow)
           return;
      for (int k = leftCol; k <= rightCol; k++ )
         data[row][k] = true;
   }

   
   
   
   
   
   
   //drawVertical is another private helper function. Takes three ints:
   //col, startRow, and stopRow. If any of these are invalid, returns FALSE.
   //Otherwise, sets the corresponding row of the 2d array data[][] to true.
   private void drawVertical( int col, int startRow, int stopRow )
   {
       if (startRow > bottomRow ||
           startRow < topRow ||
           stopRow > bottomRow ||
           stopRow < topRow ||
           col < leftCol ||
           col > rightCol)
           return;
       
       int temp;
       if (startRow > stopRow)
       {
          temp = stopRow;
          stopRow = startRow;
          startRow = temp;
       }

      for (int k = startRow; k <= stopRow; k++ )
         data[k][col] = true;
   }

   
   
   
   
   
   
   //SevenSegmentImage clone function as defined in spec. Copy constructor
   //functionality.
   public SevenSegmentImage clone() throws CloneNotSupportedException
   {
       SevenSegmentImage newSSI = (SevenSegmentImage)super.clone();
       
       int height, width;

       height = this.bottomRow + 1;
       width = this.rightCol + 1;
       newSSI.data = new boolean[height][width];
       
       for (int i = 0; i <= this.bottomRow; i++)
           for (int j = 0; j <= this.rightCol; j++)
              newSSI.data[i][j] = this.data[i][j];
     
       return newSSI;
   }
}


class SevenSegmentDisplay implements Cloneable
{

    private SevenSegmentImage theImage;
    private SevenSegmentLogic theDisplay;

    //Default constructor for SevenSegmentDisplay class. Simply initializes
    //the private data members.
    public SevenSegmentDisplay()
    {
        theImage = new SevenSegmentImage();
        theDisplay = new SevenSegmentLogic();
    }
    
    
    
    
    
    
    
    //Overloaded constructor for SevenSegmentDisplay. Takes in two ints,
    //width and height, and uses them as parameters for a setSize function
    //call by theImage. Note that we do not filter inputs here because
    //theImage will still be default initialized even if width 
    //and height are invalid.
    public SevenSegmentDisplay( int width, int height )
    {
        theImage = new SevenSegmentImage(width, height);
        theDisplay = new SevenSegmentLogic();
    }
    
    
    
    
    
    
    
    
    
    
    
    //setSize is a boolean function that takes in two int parameters, width 
    //and height. Since the size is contingent simply upon the image's size, 
    //we can simply return the return value of theImage's function call to
    //SevenSegmentImage's setSize.
    public boolean setSize( int width, int height )
    {
       return theImage.setSize(width, height);
    }
    
    
    
    
    
    
    
    
    
    //loadConsoleImage first clears the image of the theImage private data
    //member. Then, it goes through a loop through each of the theDisplay
    //segments and sets segments in the image to true if the segment
    //values in the booleanfunction are true.
    public void loadConsoleImage()
    {
        theImage.clearImage();
        char letter = 'a';
        for (int i = 0; i < 7; i++)
        {
           if (theDisplay.getValOfSeg(i))
              theImage.turnOnCellsForSegment(letter);
           letter++;
        }
    }
    
    
    
    
    
    
    
    
    //consoleDisplay simply calls the display function of the private member
    //theImage.
    public void consoleDisplay()
    {
       theImage.display();
    }
    
    
    
    
    
    
    
    
    //eval takes a single parameter, an int called input. It passes input to
    //the function call of the private member theDisplay's eval function.
    //Afterward, theDisplay's segment is changed (or not) according to input.
    public void eval( int input )
    {
       theDisplay.eval(input);
    }

    
    
    
    
    
    
    
    
    public SevenSegmentDisplay clone() throws CloneNotSupportedException
    {
        SevenSegmentDisplay newSSD = (SevenSegmentDisplay)super.clone();
        
        newSSD.theImage = this.theImage.clone();
        newSSD.theDisplay = (SevenSegmentLogic) this.theDisplay.clone();
      
        return newSSD;
    }
}


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

/* ------------------------- assignment8 run ---------------------------
showing segments a,b,c,d progressively:

---a:
*******
       
       
       
       
       
       
       
       

---a,b:
*******
      *
      *
      *
      *
       
       
       
       

---a,b,c:
*******
      *
      *
      *
      *
      *
      *
      *
      *

---a,b,c,d:
*******
      *
      *
      *
      *
      *
      *
      *
*******

clearing grid, showing e,f,g progressively:

---e:
       
       
       
       
*      
*      
*      
*      
*      

---e, f:
*      
*      
*      
*      
*      
*      
*      
*      
*      

---e,f, g:
*      
*      
*      
*      
*******
*      
*      
*      
*      

clearing grid, showing two invalid inputs:

       
       
       
       
       
       
       
       
       

       
       
       
       
       
       
       
       
       

testing copy ctor and assignment operator
the following should be identical:

*******
*     *
*     *
*     *
*******
       
       
       
       


*******
*     *
*     *
*     *
*******
       
       
       
       



===SevenSegDisplay Tests===

*****
*   *
*   *
*   *
*****


    *
    *
    *
    *
    *


*****
    *
*****
*    
*****


*****
    *
*****
    *
*****


*   *
*   *
*****
    *
    *


*****
*    
*****
    *
*****


*****
*    
*****
*   *
*****


*****
    *
    *
    *
    *


*****
*   *
*****
*   *
*****


*****
*   *
*****
    *
*****


*****
*   *
*****
*   *
*   *


*    
*    
*****
*   *
*****


*****
*    
*    
*    
*****


    *
    *
*****
*   *
*****


*****
*    
*****
*    
*****


*****
*    
*****
*    
*    


*********
*       *
*       *
*       *
*       *
*       *
*       *
*       *
*********

        *
        *
        *
        *
*********
*       *
*       *
*       *
*********

        *
        *
        *
        *
*********
*       *
*       *
*       *
*********




*/
