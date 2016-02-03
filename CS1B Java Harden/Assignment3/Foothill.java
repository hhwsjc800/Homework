//============================================================================
// Name        : CS1B (Java) Assignment 3: Cellular Automata (OPTION B1)
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Jan 27, 2016
// Instructor  : Harden
// Description : This program consists of the game of Life, a type of Cellular
//               Automata. Users are given the chance to input a seed number
//               and the program's main() function outputs the 'growth' of 
//               an initial 'seed' based on the input. Consists of a class
//               for main and the Automata class, which provides the mechanisms
//               that convert the user's input into a growth pattern. Note that
//               this program uses Option B2 for a ruleset of 32 bools instead
//               of 8, with a maximum input of 2^32 - 1.
//============================================================================

import java.util.Scanner;

public class Foothill
{
   public static void main(String[] args)
   {
      long rule;
      int k;
      
      String strUserIn;
      
      Scanner inputStream = new Scanner(System.in);
      Automaton aut;

      // get rule from user
      do
      {
         System.out.print("Enter Rule (0 - 4294967295): ");
         // get the answer in the form of a string:
         strUserIn = inputStream.nextLine();
         // and convert it to a number so we can compute:
         rule = Long.parseLong(strUserIn);
      } while (rule < 0 || rule > Automaton.MAX_RULE);

      // create automaton with this rule and single central dot
      aut = new Automaton(rule);

      // now show it
      System.out.println("   start");
      for (k = 0; k < 100; k++)
      {
         System.out.println( aut.toStringCurrentGen() );
         aut.propagateNewGeneration();
      }
      System.out.println("   end");
      inputStream.close();
   }
}

class Automaton
{
   // class constants
   public final static int MAX_DISPLAY_WIDTH = 121;
   public static final long MAX_RULE = 4294967295L;
   public static final int DEFAULT_DISPLAY_WIDTH = 89;
   public static final int RULES_SIZE = 32;
   
   // private members
   private boolean rules[];  // allocate rules[32] in constructor!
   private String thisGen;   // same here
   String extremeBit; // bit, "*" or " ", implied everywhere "outside"
   int displayWidth;  // an odd number so it can be perfectly centered
   
   // public constructors, mutators, etc. 
   //Default constructor for the Automaton class. Takes in a long, newRule, and
   //converts newRule to its binary form in order to create a unique ruleset
   //for that number. This ruleset is recorded in the private boolean member
   //rules[].
   public Automaton(long newRule)
   {
       rules = new boolean[RULES_SIZE];
       if (setRule(newRule))
           displayWidth = DEFAULT_DISPLAY_WIDTH;
       resetFirstGen();
   }
   
   
   
   
   
   
   //resetFirstGen sets the private member string thisGen to be a single byte
   //centered in a sea of 'zeros'. Size of thisGen is contingent upon
   //the displayWidth. Additionally, resets extremeBit to be a 0 (' ')
   //instead of a 1 ('*').
   public void resetFirstGen()
   {
       extremeBit = " ";
       thisGen = "*"; 
   }
   
   
   
   
   
   
   
   //takes a long newRule, then checks to see if newRule is a valid number.
   //If not, returns FALSE. Otherwise, takes newRule then finds its binary 
   //representation using bitwise operators. Its binary representation is used
   //to initialize the rules array accordingly. Returns TRUE after it is 
   //finished.
   public boolean setRule(long newRule)
   {
       if (newRule > MAX_RULE || newRule < 0)
           return false;
       
       for (int i = 0; i < RULES_SIZE; i++)
       {
           if (((newRule >> i) & 1) == 1)
               rules[i] = true;
           else 
               rules[i] = false;
       }
       return true;
   }
   
   
   
   
   
   
   //Basic mutator function for displayWidth. Takes an int width and makes sure
   //width is odd and within the bounds of display width. Returns TRUE upon
   //successful change, FALSE otherwise
   public boolean setDisplayWidth(int width)
   {
       if (width < 0 || width > MAX_DISPLAY_WIDTH || (width % 2 == 0) )
           return false;

        displayWidth = width;
        return true;
   }
   
   
   
   
   
   
   //Alters the thisGen String to make it suitable for viewing purposes. Its 
   //altered form is returned in a String called returnString. Truncates 
   //characters from both ends of thisGen evenly until its length is equal
   //to displayWidth, or pads the ends of thisGen evenly with extremeBit until
   //its length is equal to displayWidth. Once returnString's size is equal
   //to displayWidth, returns returnString.
   public String toStringCurrentGen()
   {
       String returnString = new String(thisGen);
       int distance = 0;
       int end = 0;
       while (returnString.length() != displayWidth)
       {
           
           if (returnString.length() < displayWidth)
           {
               returnString += extremeBit;
               returnString = extremeBit + returnString;
           }
           
           if (returnString.length() > displayWidth)
           {
               distance = (returnString.length() - displayWidth) / 2;
               end = returnString.length() - distance;
               returnString = returnString.substring(distance, end);
           }
       }
       return returnString;
   }
   
   
   
   
   
   
   //Provides the work for creating the next generation based on the previous
   //generation. Pads thisGen with 3 extremeBits on each side, then creates a
   //new temporary String nextGen that applies the rules[] to each element of
   //thisGen in order to decide what character is appended to nextGen. Once
   //nextGen is finished, converts thisGen into nextGen and finds the new
   //extremeBit value.
   public void propagateNewGeneration()
   {
       String nextGen, temp;
       int decVal = 0;
       
       nextGen = new String();
       //padding for thisGen
       thisGen += extremeBit + extremeBit + extremeBit;
       thisGen =  extremeBit + extremeBit + extremeBit + thisGen;

       //Creates nextGen
       for (int i = 0; i < thisGen.length() - 4; i++)
       {
          temp = thisGen.substring(i, i + 5);
          decVal = 0;
          for (int k = temp.length() - 1; k >= 0; k--)
          {
             if (temp.charAt(k) == '*')
             {
                switch (k)
                {
                case 0: 
                   decVal += 16;
                   break;
                case 1: 
                   decVal += 8;
                   break;
                case 2: 
                   decVal += 4;
                   break;
                case 3: 
                   decVal += 2;
                   break;
                case 4: 
                   decVal += 1;
                   break;
                default:
                   decVal += 0;
                   break;
                }
             }
          }

          if (rules[decVal])
             nextGen += "*";
          else
             nextGen += " ";
       }
       thisGen = nextGen;

       //calculate new extremeBit
       if (extremeBit == "*" && rules[RULES_SIZE - 1]) // extremeBits are stars
          extremeBit = "*";
       else if (extremeBit == " " && rules[0])
          extremeBit = "*";
       else
          extremeBit = " ";
   }
}

/*-------------Run of Assignment 3--------------

///////=======Old Rule 4 (Rule 16 (and more) in new space)=============
Enter Rule (0 - 4294967295): 16
   start
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
                                            *                                            
   end


//===========Old rule 126 (rule 457716188?)==================
 Enter Rule (0 - 4294967295): 457716188
   start
                                            *                                            
                                           ***                                           
                                          ** **                                          
                                         *******                                         
                                        **     **                                        
                                       ****   ****                                       
                                      **  ** **  **                                      
                                     ***************                                     
                                    **             **                                    
                                   ****           ****                                   
                                  **  **         **  **                                  
                                 ********       ********                                 
                                **      **     **      **                                
                               ****    ****   ****    ****                               
                              **  **  **  ** **  **  **  **                              
                             *******************************                             
                            **                             **                            
                           ****                           ****                           
                          **  **                         **  **                          
                         ********                       ********                         
                        **      **                     **      **                        
                       ****    ****                   ****    ****                       
                      **  **  **  **                 **  **  **  **                      
                     ****************               ****************                     
                    **              **             **              **                    
                   ****            ****           ****            ****                   
                  **  **          **  **         **  **          **  **                  
                 ********        ********       ********        ********                 
                **      **      **      **     **      **      **      **                
               ****    ****    ****    ****   ****    ****    ****    ****               
              **  **  **  **  **  **  **  ** **  **  **  **  **  **  **  **              
             ***************************************************************             
            **                                                             **            
           ****                                                           ****           
          **  **                                                         **  **          
         ********                                                       ********         
        **      **                                                     **      **        
       ****    ****                                                   ****    ****       
      **  **  **  **                                                 **  **  **  **      
     ****************                                               ****************     
    **              **                                             **              **    
   ****            ****                                           ****            ****   
  **  **          **  **                                         **  **          **  **  
 ********        ********                                       ********        ******** 
**      **      **      **                                     **      **      **      **
***    ****    ****    ****                                   ****    ****    ****    ***
  **  **  **  **  **  **  **                                 **  **  **  **  **  **  **  
*****************************                               *****************************
                            **                             **                            
                           ****                           ****                           
                          **  **                         **  **                          
*                        ********                       ********                        *
**                      **      **                     **      **                      **
***                    ****    ****                   ****    ****                    ***
  **                  **  **  **  **                 **  **  **  **                  **  
*****                ****************               ****************                *****
    **              **              **             **              **              **    
   ****            ****            ****           ****            ****            ****   
  **  **          **  **          **  **         **  **          **  **          **  **  
 ********        ********        ********       ********        ********        ******** 
**      **      **      **      **      **     **      **      **      **      **      **
***    ****    ****    ****    ****    ****   ****    ****    ****    ****    ****    ***
  **  **  **  **  **  **  **  **  **  **  ** **  **  **  **  **  **  **  **  **  **  **  
*****************************************************************************************
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
*                                                                                       *
**                                                                                     **
***                                                                                   ***
  **                                                                                 **  
*****                                                                               *****
    **                                                                             **    
   ****                                                                           ****   
  **  **                                                                         **  **  
 ********                                                                       ******** 
**      **                                                                     **      **
***    ****                                                                   ****    ***
  **  **  **                                                                 **  **  **  
*************                                                               *************
            **                                                             **            
           ****                                                           ****           
          **  **                                                         **  **          
         ********                                                       ********         
   end










//===========Old Rule 30 (Rule 66847740 in new space)========
Enter Rule (0 - 4294967295): 66847740
   start
                                            *                                            
                                           ***                                           
                                          **  *                                          
                                         ** ****                                         
                                        **  *   *                                        
                                       ** **** ***                                       
                                      **  *    *  *                                      
                                     ** ****  ******                                     
                                    **  *   ***     *                                    
                                   ** **** **  *   ***                                   
                                  **  *    * **** **  *                                  
                                 ** ****  ** *    * ****                                 
                                **  *   ***  **  ** *   *                                
                               ** **** **  *** ***  ** ***                               
                              **  *    * ***   *  ***  *  *                              
                             ** ****  ** *  * *****  *******                             
                            **  *   ***  **** *    ***      *                            
                           ** **** **  ***    **  **  *    ***                           
                          **  *    * ***  *  ** *** ****  **  *                          
                         ** ****  ** *  ******  *   *   *** ****                         
                        **  *   ***  ****     **** *** **   *   *                        
                       ** **** **  ***   *   **    *   * * *** ***                       
                      **  *    * ***  * *** ** *  *** ** * *   *  *                      
                     ** ****  ** *  *** *   *  ****   *  * ** ******                     
                    **  *   ***  ****   ** *****   * ***** *  *     *                    
                   ** **** **  ***   * **  *    * ** *     *****   ***                   
                  **  *    * ***  * ** * ****  ** *  **   **    * **  *                  
                 ** ****  ** *  *** *  * *   ***  **** * ** *  ** * ****                 
                **  *   ***  ****   **** ** **  ***    * *  ****  * *   *                
               ** **** **  ***   * **    *  * ***  *  ** ****   *** ** ***               
              **  *    * ***  * ** * *  ***** *  ******  *   * **   *  *  *              
             ** ****  ** *  *** *  * ****     ****     **** ** * * *********             
            **  *   ***  ****   **** *   *   **   *   **    *  * * *        *            
           ** **** **  ***   * **    ** *** ** * *** ** *  ***** * **      ***           
          **  *    * ***  * ** * *  **  *   *  * *   *  ****     * * *    **  *          
         ** ****  ** *  *** *  * **** **** ***** ** *****   *   ** * **  ** ****         
        **  *   ***  ****   **** *    *    *     *  *    * *** **  * * ***  *   *        
       ** **** **  ***   * **    **  ***  ***   ******  ** *   * *** * *  **** ***       
      **  *    * ***  * ** * *  ** ***  ***  * **     ***  ** ** *   * ****    *  *      
     ** ****  ** *  *** *  * ****  *  ***  *** * *   **  ***  *  ** ** *   *  ******     
    **  *   ***  ****   **** *   ******  ***   * ** ** ***  ******  *  ** *****     *    
   ** **** **  ***   * **    ** **     ***  * ** *  *  *  ***     ******  *    *   ***   
  **  *    * ***  * ** * *  **  * *   **  *** *  **********  *   **     ****  *** **  *  
 ** ****  ** *  *** *  * **** *** ** ** ***   ****         **** ** *   **   ***   * **** 
**  *   ***  ****   **** *    *   *  *  *  * **   *       **    *  ** ** * **  * ** *   *
* **** **  ***   * **    **  *** *********** * * ***     ** *  *****  *  * * *** *  ** **
  *    * ***  * ** * *  ** ***   *           * * *  *   **  ****    ****** * *   ****  * 
****  ** *  *** *  * ****  *  * ***         ** * ***** ** ***   *  **      * ** **   ****
*   ***  ****   **** *   ****** *  *       **  * *     *  *  * ***** *    ** *  * * **   
** **  ***   * **    ** **      *****     ** *** **   ******** *     **  **  **** * * *  
   * ***  * ** * *  **  * *    **    *   **  *   * * **        **   ** *** ***    * * ** 
  ** *  *** *  * **** *** **  ** *  *** ** **** ** * * *      ** * **  *   *  *  ** * * *
***  ****   **** *    *   * ***  ****   *  *    *  * * **    **  * * **** ********  * * *
*  ***   * **    **  *** ** *  ***   * ******  ***** * * *  ** *** * *    *       *** * *
 ***  * ** * *  ** ***   *  ****  * ** *     ***     * * ****  *   * **  ***     **   * *
 *  *** *  * ****  *  * *****   *** *  **   **  *   ** * *   **** ** * ***  *   ** * ** *
 ****   **** *   ****** *    * **   **** * ** **** **  * ** **    *  * *  **** **  * *  *
**   * **    ** **      **  ** * * **    * *  *    * *** *  * *  ***** ****    * *** ****
  * ** * *  **  * *    ** ***  * * * *  ** *****  ** *   **** ****     *   *  ** *   *   
*** *  * **** *** **  **  *  *** * * ****  *    ***  ** **    *   *   *** *****  ** ***  
*   **** *    *   * *** ******   * * *   ****  **  ***  * *  *** *** **   *    ***  *  **
 * **    **  *** ** *   *     * ** * ** **   *** ***  *** ****   *   * * ***  **  ****** 
** * *  ** ***   *  ** ***   ** *  * *  * * **   *  ***   *   * *** ** * *  *** ***     *
*  * ****  *  * *****  *  * **  **** **** * * * *****  * *** ** *   *  * ****   *  *   **
**** *   ****** *    ****** * ***    *    * * * *    *** *   *  ** ***** *   * ****** ** 
*    ** **      **  **      * *  *  ***  ** * * **  **   ** *****  *     ** ** *      *  
 *  **  * *    ** *** *    ** *******  ***  * * * *** * **  *    ****   **  *  **    ****
 **** *** **  **  *   **  **  *      ***  *** * * *   * * ****  **   * ** ****** *  **   
 *    *   * *** **** ** *** ****    **  ***   * * ** ** * *   *** * ** *  *      **** *  
 **  *** ** *   *    *  *   *   *  ** ***  * ** * *  *  * ** **   * *  *****    **    ** 
** ***   *  ** ***  ****** *** *****  *  *** *  * ******* *  * * ** ****    *  ** *  ** *
*  *  * *****  *  ***      *   *    ******   **** *       **** * *  *   *  *****  ****  *
 ****** *    ******  *    *** ***  **     * **    **     **    * ***** *****    ***   ***
**      **  **     ****  **   *  *** *   ** * *  ** *   ** *  ** *     *    *  **  * **  
* *    ** *** *   **   *** * *****   ** **  * ****  ** **  ****  **   ***  ***** *** * * 
* **  **  *   ** ** * **   * *    * **  * *** *   ***  * ***   *** * **  ***     *   * **
  * *** **** **  *  * * * ** **  ** * *** *   ** **  *** *  * **   * * ***  *   *** ** * 
 ** *   *    * ****** * * *  * ***  * *   ** **  * ***   **** * * ** * *  **** **   *  **
 *  ** ***  ** *      * * **** *  *** ** **  * *** *  * **    * * *  * ****    * * ***** 
*****  *  ***  **    ** * *    ****   *  * *** *   **** * *  ** * **** *   *  ** * *    *
*    ******  *** *  **  * **  **   * ***** *   ** **    * ****  * *    ** *****  * **  **
**  **     ***   **** *** * *** * ** *     ** **  * *  ** *   *** **  **  *    *** * *** 
* *** *   **  * **    *   * *   * *  **   **  * *** ****  ** **   * *** ****  **   * *   
  *   ** ** *** * *  *** ** ** ** **** * ** *** *   *   ***  * * ** *   *   *** * ** ** *
**** **  *  *   * ****   *  *  *  *    * *  *   ** *** **  *** * *  ** *** **   * *  *  *
*    * ******* ** *   * ************  ** ***** **  *   * ***   * ****  *   * * ** *******
**  ** *       *  ** ** *           ***  *     * **** ** *  * ** *   **** ** * *  *      
  ***  **     *****  *  **         **  ****   ** *    *  **** *  ** **    *  * *****     
***  *** *   **    ****** *       ** ***   * **  **  *****    ****  * *  ***** *    *    
   ***   ** ** *  **      **     **  *  * ** * *** ***    *  **   *** ****     **  ***   
  **  * **  *  **** *    ** *   ** ****** *  * *   *  *  ***** * **   *   *   ** ***  *  
 ** *** * ******    **  **  ** **  *      **** ** ********     * * * *** *** **  *  *****
 *  *   * *     *  ** *** ***  * ****    **    *  *       *   ** * * *   *   * ******    
****** ** **   *****  *   *  *** *   *  ** *  ******     *** **  * * ** *** ** *     *   
       *  * * **    **** *****   ** *****  ****     *   **   * *** * *  *   *  **   ***  
*     ***** * * *  **    *    * **  *    ***   *   *** ** * ** *   * ***** ***** * **  **
 *   **     * * **** *  ***  ** * ****  **  * *** **   *  * *  ** ** *     *     * * *** 
 ** ** *   ** * *    ****  ***  * *   *** *** *   * * ***** ****  *  **   ***   ** * *  *
**  *  ** **  * **  **   ***  *** ** **   *   ** ** * *     *   ****** * **  * **  * ****
* ******  * *** * *** * **  ***   *  * * *** **  *  * **   *** **      * * *** * *** *   
   end


 ======Rule 7777777========
 Enter Rule (0 - 4294967295): 7777777
   start
                                            *                                            
******************************************* *********************************************
                                             *                                           
*******************************************  ** *****************************************
                                             **                                          
*******************************************  *   ****************************************
                                            **** **                                      
******************************************  **   *  *************************************
                                            *  * *  *                                    
******************************************  * **** **************************************
                                           *** *    *                                    
*****************************************  *   **   ** **********************************
                                          **** *  * **  *                                
****************************************  **   * ****  **********************************
                                          *  * ** *    **                                
****************************************  * ***** **   *   ******************************
                                         *** *    *  * *** **                            
***************************************  *   **   * ***    *  ***************************
                                        **** *  * **       *  *                          
**************************************  **   * ****   ***  * ****************************
                                        *  * ** *   * *   *** *                          
**************************************  * ***** *** ***** *   ** ************************
                                       *** *         *    *** **  *                      
*************************************  *   ** *****  **   *   *  ************************
                                      **** **  *     *  * *** *  **                      
************************************  **   *  *** *  * ***    *  *   ********************
                                      *  * *  *   * ***       * **** **                  
************************************  * **** **** **     ***  ** *   *  *****************
                                     *** *    *   *   *  *    ** *** *  *                
***********************************  *   **   *** *** * ***   **     * ******************
                                    **** *  * *       **    * *   *  ** *                
**********************************  **   * ***** ***  *     ***** *  ** ** **************
                                    *  * ** *        *** *  **    *  ** **  *            
**********************************  * ***** ** ****  *   *  *     *  ** *  **************
                                   *** *    **  *   **** * *** *  *  ** *  **            
*********************************  *   **   *  **** **   **    * **  ** *  *   **********
                                  **** *  * *  **   *  * *     ***   ** * **** **        
********************************  **   * ****  *  * * ***** *  *   * ** ** *   *  *******
                                  *  * ** *   ** ***** *    * **** **** ** *** *  *      
********************************  * ***** *** **  *    **   ** *    *   **     * ********
                                 *** *        *  ***   *  * ** **   *** *   *  ** *      
*******************************  *   ** ****  *  *   * * ***** *  * *   *** *  ** ** ****
                                **** **  *   ** **** **** *    * ****** *   *  ** **  *  
******************************  **   *  **** **  *    *   **   ** *     *** *  ** *  ****
                                *  * *  **   *  ***   *** *  * ** ** *  *   *  ** *  **  
******************************  * ****  *  * *  *   * *   * ***** ** * **** *  ** *  *   
                               *** *   ** **** **** ***** ** *    ** ** *   *  ** * **** 
*****************************  *   *** **  *    *    *    ** **   ** ** *** *  ** ** *   
                              **** *   *  ***   **   **   ** *  * ** **     *  ** ** *** 
****************************  **   *** *  *   * *  * *  * ** * ***** *   *  *  ** **     
                              *  * *   * **** *** **** ***** ** *    *** * **  ** *   *  
****************************  * ****** ** *        *    *    ** **   *   ***   ** *** *  
                             *** *     ** ** ****  **   **   ** *  * *** *   * **     *  
***************************  *   ** *  ** **  *    *  * *  * ** * ***    *** ***   *  *  
                            **** ** *  ** *  ***   * **** ***** **       *       * * **  
**************************  **   ** *  ** *  *   * ** *    *    *   ***  ** ***  *****   
                            *  * ** *  ** * **** **** **   **   *** *    **      **    * 
**************************  * ***** *  ** ** *    *   *  * *  * *   **   *   **  *     **
                           *** *    *  ** ** **   *** * **** ****** *  * *** *  *** *  **
*************************  *   **   *  ** ** *  * *   ** *    *     * ***    *  *   *  * 
                          **** *  * *  ** ** * ****** ** **   ** *  **       * **** * ** 
************************  **   * ****  ** ** ** *     ** *  * ** *  *   ***  ** *   **** 
                          *  * ** *    ** ** ** ** *  ** * ***** * **** *    ** *** **   
************************  * ***** **   ** ** ** ** *  ** ** *    ** *   **   **     *  * 
                         *** *    *  * ** ** ** ** *  ** ** **   ** *** *  * *   *  * ***
***********************  *   **   * ***** ** ** ** *  ** ** *  * **     * ****** * *** * 
                        **** *  * ** *    ** ** ** *  ** ** * ****   *  ** *     **    **
**********************  **   * ***** **   ** ** ** *  ** ** ** *   * *  ** ** *  *     * 
                        *  * ** *    *  * ** ** ** *  ** ** ** *** ***  ** ** * *** *  **
**********************  * ***** **   * ***** ** ** *  ** ** **          ** ** **    *  **
                       *** *    *  * ** *    ** ** *  ** ** *   ******  ** ** *     *  * 
*********************  *   **   * ***** **   ** ** *  ** ** *** **      ** ** ** *  * ** 
                      **** *  * ** *    *  * ** ** *  ** **     *   **  ** ** ** * ****  
********************  **   * ***** **   * ***** ** *  ** *   *  *** *   ** ** ** ** *   *
                      *  * ** *    *  * ** *    ** *  ** *** *  *   *** ** ** ** ** *** *
********************  * ***** **   * ***** **   ** *  **     * **** *   ** ** ** **      
                     *** *    *  * ** *    *  * ** *  *   *  ** *   *** ** ** ** *   **  
*******************  *   **   * ***** **   * ***** * **** *  ** *** *   ** ** ** *** *   
                    **** *  * ** *    *  * ** *    ** *   *  **     *** ** ** **     *** 
******************  **   * ***** **   * ***** **   ** *** *  *   *  *   ** ** *   *  *   
                    *  * ** *    *  * ** *    *  * **     * **** * **** ** ** *** * *** *
******************  * ***** **   * ***** **   * ****   *  ** *   ** *   ** **     **     
                   *** *    *  * ** *    *  * ** *   * *  ** *** ** *** ** *   *  *   ***
*****************  *   **   * ***** **   * ***** *** ***  **     **     ** *** * **** ** 
                  **** *  * ** *    *  * ** *             *   *  *   *  **     ** *   *  
****************  **   * ***** **   * ***** ** *********  *** * **** *  *   *  ** *** ***
                  *  * ** *    *  * ** *    **  *         *   ** *   * **** *  **        
****************  * ***** **   * ***** **   *  *** *****  *** ** *** ** *   *  *   ***** 
                 *** *    *  * ** *    *  * *  *    *     *   **     ** *** * **** **    
***************  *   **   * ***** **   * **** ***   ** *  *** *   *  **     ** *   *   * 
                **** *  * ** *    *  * ** *       * ** *  *   *** *  *   *  ** *** *** * 
**************  **   * ***** **   * ***** ** ***  **** * **** *   * **** *  **         * 
                *  * ** *    *  * ** *    **      **   ** *   *** ** *   *  *   *****  * 
**************  * ***** **   * ***** **   *   **  *  * ** *** *   ** *** * **** **    ** 
               *** *    *  * ** *    *  * *** *  ** *****     *** **     ** *   *     *  
*************  *   **   * ***** **   * ***    *  **  *     *  *   *   *  ** *** ** *  ***
              **** *  * ** *    *  * **       *  *  *** *  * **** *** *  **     ** *  *  
************  **   * ***** **   * ****   ***  * **  *   * *** *       *  *   *  ** * ****
              *  * ** *    *  * ** *   * *   ****  **** **    ** ***  * **** *  ** ** *  
************  * ***** **   * ***** *** ***** **    **   *     **     *** *   *  ** ** ***
   end

 
 =====Rule 66=============
 Enter Rule (0 - 4294967295): 66
   start
                                            *                                            
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
                                                                                         
   end

 
 
 *******************************************************************************/





