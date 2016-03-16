//============================================================================
// Name        : CS1B (Java) Assignment 4: Message Class and Derived Classes
// Author      : Eduardo Albano #20077222
// Version     : Winter 2016 Foothill College
// Date        : Feb 3, 2016
// Instructor  : Harden
// Description : This program consists of a base class, Message, with two 
//               derived classes, Email and Shwitter. Message class 
//               holds Messages while Email and Shwitter are meant to simulate
//               Emails and Twitter-like messages. Main is used to test the
//               functionality of the base class and derived classes.
//============================================================================


class Foothill
{
   public static void main(String[] args)
   {
      Message testMsg1, testMsg2, testMsg3;
      testMsg1 = new Message();
      testMsg2 = new Message("My life for Aiur!");
      testMsg3 = new Message("");
      
      String longString = new String("LOL");
      
      while (longString.length() < Message.MAX_MSG_LENGTH + 1)
         longString += "LOLLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOL";
      
      System.out.println("---Tests of Base Class----\n\n");
      
      //Show initial messages using toString()
      System.out.println("---Initial messages using toString(): \n\n" +
                         "Message 1: \n" + testMsg1.toString() +
                         "Message 2: \n" + testMsg2.toString() +
                         "Message 3: \n" + testMsg3.toString());
      
      //Mutate some members then display all w/ toString()
      testMsg1.setMessage("Goodbye cruel world!");
      testMsg3.setMessage("IT IS GOOD TO BE A VALID MESSAGE!");
      
      System.out.println("---Mutated msg1, msg3. Showing w/ toString(): \n\n" +
                         "Message 1: \n" + testMsg1.toString() +
                         "Message 2: \n" + testMsg2.toString() +
                         "Message 3: \n" + testMsg3.toString());
      
      //Testing accessor
      System.out.println("---Testing message accessor: " +
                         "\n\nMessage 1: \n" + testMsg1.getMessage() +
                         "\n\nMessage 2: \n" + testMsg2.getMessage() +
                         "\n\nMessage 3: \n" + testMsg3.getMessage());
      
      //Testing mutator
      System.out.println("---Testing message mutator: \n");
      if (testMsg1.setMessage("k"))
         System.out.println("Successfully mutated testMsg1");
      else
         System.out.println("Failed to mutate testMsg1");
      
      if (testMsg2.setMessage(longString))
         System.out.println("Successfully mutated testMsg2");
      else
         System.out.println("Testmsg2 mutate too long (expected)");
      
      if (testMsg3.setMessage(""))
         System.out.println("Successfully mutated testMsg3!\n\n");
      else
         System.out.println("Testmsg3 mutate too short (expected)\n\n");
      
      //--------End base class tests
      System.out.println("******END OF BASE CLASS TESTS*******");

      //---Tests of derived class email----
      Email email1, email2, email3;
      email1 = new Email();
      email2 = new Email("Hello there", "Cliff@Foothill.edu", "JA@AOL.com");
      email3 = new Email("Invalid address here", "Bob@Bob.com", "email");
      
   
      System.out.println("---Tests of Derived Class Email----\n");
      
      //Show initial messages using toString()
      System.out.println("---Initial Emails using toString(): \n\n" +
                         "Email 1: \n" + email1.toString() +
                         "Email 2: \n" + email2.toString() +
                         "Email 3: \n" + email3.toString());
      
      //Mutate some members then display all w/ toString()
      email1.setFromAddress("Thisemailaddresswillnotbevalid");
      email2.setFromAddress("@. trying to exceed the maximum length for " + 
                            "emails is not so easy because things have to be" +
                            "very extended you see and if they're not then");
      email1.setMessage("this is a new email message");
      email3.setToAddress("email@email.email");
      
      System.out.println("---Mutated emails.Showing w/ toString(): \n\n" +
                         "Email1 : \n" + email1.toString() +
                         "Email2 : \n" + email2.toString() +
                         "Email3 : \n" + email3.toString());
      
      //Testing accessor
      System.out.println("---Testing derived class(Email) accessor: " +
                         "\nEmail1 : \n" + email1.getFromAddress() +
                         "\nEmail2 : \n" + email2.getToAddress() +
                         "\nEmail3: \n" + email3.getMessage());
      
      //Testing mutator
      System.out.println("---Testing message mutator: \n");
      if (email1.setFromAddress("k"))
         System.out.println("Successfully mutated fromAddress of email1");
      else
         System.out.println("Failed to mutate fromAddress email1");
      
      if (email2.setToAddress(longString))
         System.out.println("Successfully mutated email2");
      else
         System.out.println("email2 mutate too long (expected)");
      
      if (email3.setToAddress(""))
         System.out.println("Successfully changed toAddress of email3!\n");
      else
         System.out.println("Email3 mutate too short (expected)\n");
      
      if (email3.setToAddress("Eduardo.....Hello!!.com"))
          System.out.println("Successfully changed toAddress of email3\n");
      else
          System.out.println("Missing '@' character for email3 (expected)\n");
      
      //---End of tests of Email derived class-----
      System.out.println("******END OF EMAIL CLASS TESTS*******\n");

      //---Tests of derived class Shweet----
      Shweet shweet1, shweet2;
      shweet1 = new Shweet();
      shweet2 = new Shweet("WOW I M A SHWEET", "hardenMan");
   
      System.out.println("---Tests of Derived Class Shweet----\n");
      
      //Show initial messages using toString()
      System.out.println("---Initial Shweets using toString(): \n\n" +
                         "Shweet 1: \n" + shweet1.toString() +
                         "Shweet 2: \n" + shweet2.toString());
      
      //Mutate some members then display all w/ toString()
      shweet1.setMessage("Guacamole, we've got a shweet!");
      shweet1.setID("N0_SP4CES");
      shweet1.setID("No spaces allowed");
      shweet2.setMessage("New valid shweet");
      
      System.out.println("---Mutated Shweets.Showing w/ toString(): \n\n" +
                         "Shweet1 : \n" + shweet1.toString()  +
                         "Shweet2 : \n" + shweet2.toString());
      
      //Testing accessor
      System.out.println("---Testing derived class(Shweet) accessor: " +
                         "\nshweet1 msg : \n" + shweet1.getMessage() +
                         "\nshweet2 ID : \n" + shweet2.getID() +
                         "\nshweet1 ID: \n" + shweet1.getID() + "\n");
      
      //Testing mutator
      System.out.println("---Testing shweet mutator: \n");
      if (shweet1.setMessage(longString))
         System.out.println("Successfully changed shweet of shweet1\n");
      else
         System.out.println("shweet1 too long (expected)\n");
      
      if (shweet1.setID("_shwitIDD_69"))
         System.out.println("Successfully changed id shweet1 (expected)\n");
      else
         System.out.println("failed to change id of shweet1\n");
      
      if (shweet2.setMessage("Should be a valid Shweet!! Yay"))
         System.out.println("Successfully changed shweet2 msg (expected)\n");
      else
         System.out.println("Failed to change shweet of shweet2\n");
      
      if (shweet2.setID("&&invalidcharacters"))
          System.out.println("Successfully changed ID of shweet2\n\n");
      else
          System.out.println("invalid characters for shweet2 Id (expected)");
      
 
      
   }
   
}

class Message
{
   //--static class constants
   public static final int MIN_MSG_LENGTH = 1;
   public static final int MAX_MSG_LENGTH = 100000;
   public static final int DEFAULT_MSG_LENGTH = 10;
   public static final String DEFAULT_MSG = "(invalid message)";
   
   //--private member data
   private String message;
   
   
   
   
   
   
   
   //--public class methods
   //Default constructor for Message class. Takes no parameters, simply sets
   //the private data member message as the DEFAULT_MSG for the class.
   public Message()
   {
      message = new String(DEFAULT_MSG);
   }
   
   
   
   
 
   
   
   //Overloaded constructor for the Message class. Takes a string, userMessage,
   //then checks for its validity. If userMessage is valid, we set the private
   //data member message to userMessage. If not, message is set to the default
   //message.
   public Message(String userMessage)
   {
      if (!validMessage(userMessage))
         message = new String(DEFAULT_MSG);
      else
         message = new String(userMessage);
      
   }
   
   
   
   
   
   
   
   //Simple getter for private data member, message, as its data type (String).
   //Returns message as a String.
   public String getMessage()
   {
      return message;
   }
   
   
   
   
   
   
   
   
   
   
   
   
   //setMessage is a mutator for the private member message of the Message 
   //class. Makes use of the private helper function validMessage. Takes in
   //a String, messageInput, and returns TRUE while setting the private data
   //member message to messageInput if messageInput is valid. Otherwise leaves
   //the Message object unchanged and returns FALSE.
   public boolean setMessage(String messageInput)
   {
      if (!validMessage(messageInput))
         return false;
      
      message = messageInput;
      return true;
   }
   
   
   
   
   
   
   
   
   
   //toString() functionality for the Message class. Takes no parameters,
   //but returns a String consisting of the message private member in a 
   //'nice' way.
   public String toString()
   {
      
      String returnString = new String();
      if (validMessage(message))
         returnString += message + "\n"; 
      
      return returnString;
   }
   
   
   
   
   
   
   
   //--private class methods
   
   //validMessage is a boolean helper function that takes in a String, 
   //testMessage. It checks if testMessage has appropriate length as defined
   //by the Message class. If not, it returns FALSE. If the message is of
   //appropriate length, it returns TRUE.
   private boolean validMessage(String testMessage)
   {
      if (testMessage.length() > MAX_MSG_LENGTH ||
          testMessage.length() < MIN_MSG_LENGTH)
         return false;
      
      return true;  
   }
      
   
}

class Email extends Message
{
    //--static class constants
    public static final int MAX_EMAIL_ADDRESS_LEN = 125;
    public static final int MIN_EMAIL_ADDRESS_LEN = 6;
    public static final String DEFAULT_EMAIL_ADDRESS = "(undefined)@(N/A).com";
    
    //--private member data
    private String fromAddress;
    private String toAddress;
    
    //--public class methods---
    
    //Default constructor for Email class. Sets email fromAddress and toAddress
    //to default values. 
    public Email()
    {
        fromAddress = DEFAULT_EMAIL_ADDRESS;
        toAddress = DEFAULT_EMAIL_ADDRESS;
    }
    
    
    
    
    
    
    //Overloaded three parameter constructor for Email class. Uses constructor
    //chaining to pass the first String argument, message, as a parameter to 
    //the 1-param Message constructor. Takes in two additional strings,
    //fromAddress and toAddress and checks for their validity. If the two
    //Strings are valid, then the private members are set to them. Otherwise,
    //they are set as the default emails. 
    public Email(String message, String fromAddress, String toAddress)
    {
        super(message);
        if (!setFromAddress(fromAddress))
            this.fromAddress = DEFAULT_EMAIL_ADDRESS;
        if (!setToAddress(toAddress))
            this.toAddress = DEFAULT_EMAIL_ADDRESS;
    }
    
    
    
    
    
    
    
    //Mutator for fromAddress. Takes in a string, addressTest, and passes it
    //into the validEmailAddress helper function. If addressTest is valid,
    //sets fromAddress to addressTest and returns TRUE. If addressTest is
    //invalid, returns FALSE without changing fromAddress.
    public boolean setFromAddress(String addressTest)
    {
        if (!isValidEmailAddress(addressTest))
            return false;
        fromAddress = addressTest;
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    //Mutator for toAddress. Takes in a String, addressTest, and passes it into
    //the validEmailAddress helper function. If addressTest is valid, sets
    //toAddress to addressTest and returns TRUE. If addressTest is invalid,
    //returns TRUE without changing toAddress.
    public boolean setToAddress(String addressTest)
    {
        if (!isValidEmailAddress(addressTest))
            return false;

        toAddress = addressTest;
        return true;
    }
    
    
    
    
    
    
    
    //Simple accessor for fromAddress. Returns fromAddress as a String.
    public String getFromAddress()
    {
        return fromAddress;
    }
    
    
    
    
    
    
    
    //Simple accessor for toAddress. Returns toAddress as a String
    public String getToAddress()
    {
        return toAddress;
    }
    
    
    
    
    
    
    
    //toString() functionality for Email class. Returns a String called
    //returnString consisting of the base class String in addition to the 
    //Email member field data, formatted in a readable way. Overload for
    //base class toString()
    public String toString()
    {
        String retString = super.toString();
        retString += "From: " + fromAddress + "\n" + "To: " +toAddress +"\n\n";
        
        return retString;
    }
    
    
    
    
    
    //--private class methods
    
    //Private static validation helper for the Email class. Checks for correct
    //email length and the proper punctuation for emails, including the @ sign
    //and a '.' character after it. Takes in a String called addressTest and
    //returns TRUE if addressTest is a valid Email address. Otherwise, returns
    //FALSE. Only checks for length of email and existence of '@' and '.' 
    //characters, as required by spec.
    private static boolean isValidEmailAddress(String addressTest)
    {
        if (addressTest.length() > MAX_EMAIL_ADDRESS_LEN ||
            addressTest.length() < MIN_EMAIL_ADDRESS_LEN ||
            addressTest.indexOf('@') == -1 ||
            addressTest.indexOf('.') == -1)
            return false;
        
        return true;
    }
}

class Shweet extends Message
{
    //--static class constants
    public static final int MAX_SHWITTER_ID_LENGTH = 15;
    public static final int MAX_SHWEET_LENGTH = 140;
    public static final String DEFAULT_USER_ID = "!invalid user!";
    
    //--private member data
    String fromID;
    
    //--public class methods--
    
    //Default constructor for class Shweet. Sets private member data fromID
    //to the default ID, which is a flag that the ID is actually invalid.
    
    public Shweet()
    {
        fromID = DEFAULT_USER_ID;
    }
    
    
    
    
    
    
    
    //Overloaded constructor for class Shweet. Takes in two Strings, message 
    //and fromID. Uses ctor chaining to call the baseclass ctor to set the
    //message so that the message is initialized, then tries to filter the 
    //message using the Shweet class setMessage. If the message is a valid 
    //Shweet, it is saved. Otherwise, we get the default invalid message.
    //If the ctor fails to set an ID, then fromID is set to the default
    //Shweet user ID.
    public Shweet(String message, String fromID)
    {
        super(message);
        setMessage(message);
        if (!setID(fromID))
            this.fromID = DEFAULT_USER_ID;
    }
    
    
    
    
    
    
    //Mutator for the fromID(Shweet ID). Takes a String called idTest and 
    //passes it through helper method, isValidShwitterID. If idTest is a 
    //valid ID, then sets the private member fromID to idTest and returns
    //TRUE. Otherwise, returns FALSE.
    public boolean setID(String idTest)
    {
        if (!isValidShwitterID(idTest))
            return false;
        fromID = idTest;
        return true;
    }
    
    
    
    
    
    
    
    //Mutator for Shweet class messages. Takes one parameter, a String called
    //shweetTest and passes it through a helper method, isValidShweet. If 
    //shweetTest is a valid Shweet, sets the private member message to
    //shweetTest and returns TRUE. Otherwise, returns FALSE. Overload for
    //the setMessage() of base class. Checks for validity of message
    //first as spec requires.
    public boolean setMessage(String shweetTest)
    {
       if (!super.setMessage(shweetTest))
           return false;
       if (!isValidShweet(shweetTest))
           return false;
       
       super.setMessage(shweetTest);
       return true;
    }
    
    
    
    
    
    
    //Simple accessor for fromID. Returns fromID as a String.
    public String getID()
    {
        return fromID;
    }
    
    
    
    
    
    //toString() functionality for Shweet class. Returns a String called
    //returnString consisting of the base class String in addition to the 
    //Shweet member field data, formatted in a readable way. Overload for
    //base class toString()
    public String toString()
    {
        String retString = "Shweet: @" + fromID + "\n";
        retString += super.toString() + "\n";
        
        return retString;
    }

    
    //---private helper methods---
    
    //Checks for the validity of a Shweet by checking for its length compared
    //to the Shweet class constants. Takes in a single String, shweetTest
    //and returns TRUE if the Shweet is valid, FALSE otherwise.
    private boolean isValidShweet(String shweetTest)
    {
        if (shweetTest.length() > MAX_SHWEET_LENGTH)
            return false;
        return true;
    }
    
    
    
    
    
    //Checks for the validity of a shwitter ID by passing it as a String, 
    //idTest. idTest is passed through a lower level helper function,
    //stringHasOnlyAlphaOrNumOrUnderscore, and returns the value of
    //that helper function. Returns TRUE if idTest is a valid ID,
    //FALSE otherwise. Also checks idTest for appropriate length.
    private boolean isValidShwitterID(String idTest)
    {
        if (idTest.length() > MAX_SHWITTER_ID_LENGTH)
            return false;
        return stringHasOnlyAlphaOrNumOrUnderscore(idTest);
    }
    
    
    
    
    
    
    
    //Checks to see if a String has only alphanumeric characters or underscores.
    //Takes in a single String, testString, and returns FALSE if it finds
    //anything in the string that isn't an alphanumeric character or 
    //underscore. returns TRUE otherwise. 
    private boolean stringHasOnlyAlphaOrNumOrUnderscore(String testString)
    {
        for (int i = 0; i <testString.length(); i++)
        {
            if (!Character.isLetterOrDigit(testString.charAt(i)) &&
                testString.charAt(i) != '_')
                return false;
        }
        return true;
    }
    
}

/*******RUN OF ASSIGNMENT 4**************

---Tests of Base Class----


---Initial messages using toString(): 

Message 1: 
(invalid message)
Message 2: 
My life for Aiur!
Message 3: 
(invalid message)

---Mutated msg1, msg3. Showing w/ toString(): 

Message 1: 
Goodbye cruel world!
Message 2: 
My life for Aiur!
Message 3: 
IT IS GOOD TO BE A VALID MESSAGE!

---Testing message accessor: 

Message 1: 
Goodbye cruel world!

Message 2: 
My life for Aiur!

Message 3: 
IT IS GOOD TO BE A VALID MESSAGE!
---Testing message mutator: 

Successfully mutated testMsg1
Testmsg2 mutate too long (expected)
Testmsg3 mutate too short (expected)


******END OF BASE CLASS TESTS*******
---Tests of Derived Class Email----

---Initial Emails using toString(): 

Email 1: 
(invalid message)
From: (undefined)@(N/A).com
To: (undefined)@(N/A).com

Email 2: 
Hello there
From: Cliff@Foothill.edu
To: JA@AOL.com

Email 3: 
Invalid address here
From: Bob@Bob.com
To: (undefined)@(N/A).com


---Mutated emails.Showing w/ toString(): 

Email1 : 
this is a new email message
From: (undefined)@(N/A).com
To: (undefined)@(N/A).com

Email2 : 
Hello there
From: Cliff@Foothill.edu
To: JA@AOL.com

Email3 : 
Invalid address here
From: Bob@Bob.com
To: email@email.email


---Testing derived class(Email) accessor: 
Email1 : 
(undefined)@(N/A).com
Email2 : 
JA@AOL.com
Email3: 
Invalid address here
---Testing message mutator: 

Failed to mutate fromAddress email1
email2 mutate too long (expected)
Email3 mutate too short (expected)

Missing '@' character for email3 (expected)

******END OF EMAIL CLASS TESTS*******

---Tests of Derived Class Shweet----

---Initial Shweets using toString(): 

Shweet 1: 
Shweet: @!invalid user!
(invalid message)

Shweet 2: 
Shweet: @hardenMan
WOW I M A SHWEET


---Mutated Shweets.Showing w/ toString(): 

Shweet1 : 
Shweet: @N0_SP4CES
Guacamole, we've got a shweet!

Shweet2 : 
Shweet: @hardenMan
New valid shweet


---Testing derived class(Shweet) accessor: 
shweet1 msg : 
Guacamole, we've got a shweet!
shweet2 ID : 
hardenMan
shweet1 ID: 
N0_SP4CES

---Testing shweet mutator: 

shweet1 too long (expected)

Successfully changed id shweet1 (expected)

Successfully changed shweet2 msg (expected)

invalid characters for shweet2 Id (expected)

******************************************/