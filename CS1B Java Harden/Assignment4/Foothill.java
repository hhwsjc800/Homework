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
      
      
      
   }
   
}

class Message
{
   //--static class constants
   public static final int MIN_MSG_LENGTH = 1;
   public static final int MAX_MSG_LENGTH = 100000;
   public static final int DEFAULT_MSG_LENGTH = 10;
   public static final String DEFAULT_MSG = "Hello world!";
   
   //--private member data
   private String message;
   
   //--public class methods
   public Message()
   {
      message = new String(DEFAULT_MSG);
   }
   
   
   
   
 
   
   
   public Message(String userMessage)
   {
      if (!validMessage(userMessage))
         message = new String(DEFAULT_MSG);
      else
         message = new String(userMessage);
      
   }
   
   
   
   
   
   
   
   public String getMessage()
   {
      return message;
   }
   
   
   
   
   
   
   
   public boolean setMessage(String messageInput)
   {
      if (!validMessage(messageInput))
         return false;
      
      message = messageInput;
      return true;
   }
   
   
   
   
   
   
   
   
   public String toString()
   {
      
      String returnString = new String("***** BEGIN MESSAGE *****\n\n");
      if (validMessage(message))
         returnString += message; 
      
      returnString += "\n\n***** END MESSAGE *****\n\n";
      
      return returnString;
   }
   
   
   
   
   
   
   
   //--private class methods
   
   private boolean validMessage(String testMessage)
   {
      if (testMessage.length() > MAX_MSG_LENGTH ||
          testMessage.length() < MIN_MSG_LENGTH)
         return false;
      
      return true;  
   }
      
   
}