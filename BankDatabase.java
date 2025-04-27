// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private Account accounts[]; // array of Accounts
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      accounts = new Account[ 6 ]; // just 5 accounts for testing
      accounts[ 0 ] = new Account( 12345, 54321, 12000.00, 12000.00 );
      accounts[ 1 ] = new Account( 98765, 56789, 20000.00, 20000.00 );
      accounts[ 2 ] = new SavingAccount( 23456, 54322, 20000.00, 20000.00 );
      accounts[ 3 ] = new ChequeAccount( 54322, 23445, 12000.00, 12000.00 );
      accounts[ 4 ] = new SavingAccount( 54567, 56447, 15000.00, 15000.00 );
      accounts[ 5 ] = new ChequeAccount( 54000, 56975, 23000.00, 23000.00 );
   } // end no-argument BankDatabase constructor
   
   // retrieve Account object containing specified account number
   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount
   
   public int authenticateCheque( int userAccountNumber )
   {
      // attempt to retrieve the account with the account number
      //Account userAccount = getAccount( userAccountNumber );
      
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == 0 )
            return 0;
         else
            return 1;
      }
      
      // if account exists, return result of Account method validatePIN
      // ( userAccountNumber == 54322 )
      //   return 1;
      //else
      return 0; // account number not found, so return false
   } 
   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public int authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount == null )
         return 1;
      else
         return userAccount.validatePIN( userPIN ); // account number not found, so return false
   } // end method authenticateUser
   
   public int authenticateTransfer( int userAccountNumber )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount == null )
         return -1;
      else
         return userAccountNumber;// account number not found, so return false
   } // end method authenticateUser


   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit
   
   public void transfer(int fromAccountNumber, int toAccountNumber, double amount) {
    // Check if the from account exists and has sufficient funds
    Account fromAccount = getAccount(fromAccountNumber);
   if (fromAccount != null && fromAccount.getAvailableBalance() >= amount) {
        // Debit the amount from the source account
        fromAccount.debit(amount);
        
        // Credit the amount to the target account
        Account toAccount = getAccount(toAccountNumber);
        if (toAccount != null) {
            toAccount.credit(amount);
            System.out.println("The transaction work successfully");
        } else {
            // If the target account does not exist, return the funds
            fromAccount.credit(amount);
            System.out.println("The target account does not exist. Transaction canceled.");
        }
    } else {
        System.out.println("Insufficient funds for transfer.");
    }
    } // end method transfer
} // end class BankDatabase



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