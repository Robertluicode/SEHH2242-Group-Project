// Withdrawal.java
// Represents a withdrawal ATM transaction

import java.util.Scanner;

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private double preamount;
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   private BankDatabase bankDatabase;
   private int temp1 = 0;
   private int temp2 = 0;
   private int temp3 = 0;
   
   // constant corresponding to menu option to cancel
   private final static int CANCELED = 6;
   // Withdrawal constructor   
   public Withdrawal( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
      
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase(); // invoke bankDatabase
      Screen screen = getScreen();

      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user 
         preamount = displayMenuOfAmounts();
         
         if (preamount % 1 != 0){
            screen.displayMessageLine( 
                      "\nCannot enter decimal places." );
                      break;
            }
            else{
            amount = (int)preamount;
            }
         
         // check whether user chose a withdrawal amount or canceled

         if ( amount != CANCELED )
         {
            // get available balance of account involved
            availableBalance = 
               bankDatabase.getAvailableBalance( getAccountNumber() );
      
            // check whether the user has enough money in the account 
            if ( amount <= availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  if (amount % 100 != 0){
                  
                     screen.displayMessageLine( "\nPlease enter a multiple of 100 Number." );
                     
                    }
                  // update the account involved to reflect withdrawal
                  else {
                      
                      bankDatabase.debit( getAccountNumber(), amount );
                      
                      temp1 = amount/1000;
                      temp2 = (amount-temp1*1000)/500;
                      temp3 = (amount-temp1*1000-temp2*500)/100;
                      
                      cashDispenser.dispenseCash( amount ); // dispense cash
                      cashDispensed = true; // cash was dispensed

                      // instruct user to take cash
                      screen.displayIntMessage(temp1, temp2, temp3);
                      screen.displayMessageLine( 
                      "\nPlease take your cash now." );
                      
                  }
            
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine( 
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            return; // return to main menu because user canceled
         } // end else
        } while ( !cashDispensed );
   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private double displayMenuOfAmounts()
   {
      double userChoice = 0; // local variable to store return value
      Screen screen = getScreen(); // get screen reference
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 200, 400, 600, 800, 1000 };

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - $200" );
         screen.displayMessageLine( "2 - $400" );
         screen.displayMessageLine( "3 - $600" );
         screen.displayMessageLine( "4 - $800" );
         screen.displayMessageLine( "5 - $1000" );
         screen.displayMessageLine( "6 - Enter a Customize Value" );
         screen.displayMessageLine( "7 - Cancel transaction" );
         screen.displayMessage( "\nChoose a withdrawal action: " );

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               userChoice = amounts[ input ]; // save user's choice
               break;
            case 6:
                System.out.print( "Enter a value: ");
               userChoice = keypad.getDoubleInput();
               break;
            case 7: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine( 
                  "\nIvalid selection. Try again." );
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal



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