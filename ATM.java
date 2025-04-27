// ATM.java
// Represents an automated teller machine

public class ATM 
{
   private int userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private DepositSlot depositSlot; // ATM's deposit slot
   private BankDatabase bankDatabase; // account information database
   private int Chequeaccount;

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int EXIT = 4;
   private static final int TRANSFER = 3;
   // no-argument ATM constructor initializes instance variables
   public ATM() 
   {
      userAuthenticated = 0; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      Chequeaccount = 0;
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      //depositSlot = new DepositSlot(); // create deposit slot
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   // start ATM 
   public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( userAuthenticated == 0 || userAuthenticated == 1 || userAuthenticated == 2) 
         {
            screen.displayMessageLine( "Welcome to SEHH2242 103A Group13 !" );       
            authenticateUser(); // authenticate user
         } // end while
         
         if (Chequeaccount == 1)
             performChequeAccountTransactions(); // user is now authenticated 
         else if (Chequeaccount == 0)
             performTransactions();
         userAuthenticated = 0; // reset before next ATM session
         Chequeaccount = 0;
         currentAccountNumber = 0; // reset before next ATM session 
         screen.displayMessageLine( "\nThank you! Goodbye!" );
      } // end while   
   } // end method run

   // attempts to authenticate user against database
   public void authenticateUser() 
   {
      screen.displayMessage( "Please enter your account number: " );
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage( "Enter your PIN: " ); // prompt for PIN
      int pin = keypad.getInput(); // input PIN
      
      // set userAuthenticated to boolean value returned by database
      userAuthenticated = bankDatabase.authenticateUser( accountNumber, pin );
      Chequeaccount = bankDatabase.authenticateCheque( accountNumber );
      
      // check whether authentication succeeded
      if ( userAuthenticated == 5)
      {
         currentAccountNumber = accountNumber; // save user's account
      } // end if
      else if (userAuthenticated == 1)
      {
         screen.displayMessageLine( 
             "Invalid account number. Please try again." );
      }
      else if (userAuthenticated == 2)
      {
         screen.displayMessageLine( 
             "Invalid PIN. Please try again." );
      }
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions() 
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {     
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY: 
            case WITHDRAWAL: 
            //case DEPOSIT:
            
               // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction
               break; 
               
            case TRANSFER: // User chose to transfer funds
                // Create and execute a new Transfer transaction
                currentTransaction = 
                new Transfer(currentAccountNumber, screen, bankDatabase, keypad);
                currentTransaction.execute(); // Execute the transfer transaction
                break;

            case EXIT: // user chose to terminate session
               screen.displayMessageLine( "\nExiting the system..." );
               userExited = true; // this ATM session should end
               break;
               
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine( 
                  "\nYou did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions
   
   
   private void performChequeAccountTransactions() 
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {     
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY: 
            case WITHDRAWAL: 
            //case DEPOSIT:
            
               // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction
               break; 
               
            case TRANSFER: // User chose to transfer funds
                // Create and execute a new Transfer transaction
                currentTransaction = 
                new ChequeAccountTransfer(currentAccountNumber, screen, bankDatabase, keypad);
                currentTransaction.execute(); // Execute the transfer transaction
                break;

            case EXIT: // user chose to terminate session
               screen.displayMessageLine( "\nExiting the system..." );
               userExited = true; // this ATM session should end
               break;
               
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine( 
                  "\nYou did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions
   
   // display the main menu and return an input selection
   private int displayMainMenu()
   {
      screen.displayMessageLine( "\nMain menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
      screen.displayMessageLine( "3 - Transfer funds" );
      screen.displayMessageLine( "4 - Exit" );
      screen.displayMessage( "Enter a choice: " );
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu
   
   

   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable
      
      // determine which type of Transaction to create     
      
      
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry( 
               currentAccountNumber, screen, bankDatabase );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen, 
               bankDatabase, keypad, cashDispenser );
            break;
         case TRANSFER: // user chose to transfer funds
            temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad); 
            break;
      } // end switch
      return temp; // return the newly created object
   } // end method createTransaction
} // end class ATM



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/