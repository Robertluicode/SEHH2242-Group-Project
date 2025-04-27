import javax.swing.JOptionPane;

public class Transfer extends Transaction
{
    // instance variables - replace the example below with your own
    private int TransferAmount;
    private int accountNumber; // indicates account involved
    private Screen screen; // ATM's screen
    private BankDatabase bankDatabase; // account info database
    private Keypad keypad;
    private Account account;
    
    public Transfer(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad)
      //Transfer atmTransfer )
    {
     super( userAccountNumber, atmScreen, atmBankDatabase );
     screen = new Screen(); // create screen
     keypad = new Keypad();
     TransferAmount = 0;
     bankDatabase = new BankDatabase();
     //Transfer = atmTransfer;
     accountNumber = userAccountNumber;
     screen = atmScreen;
     bankDatabase = atmBankDatabase;
    }
    
    @Override
    public void execute()
    {
        boolean TransferStates = false;
        double money = 0;
        double preinput;
        int input;
        int checkValidAccount;
        boolean EnoughMoney = false;
        while( !TransferStates ){
        screen.displayMessage( "\nEnter Account Number you want to transfer\n (Enter 0 to cancel transcation.): ");
        
        preinput = keypad.getDoubleInput();
        
        if (preinput == 0)
        break;
        
        if (preinput % 1 != 0){
        screen.displayMessage("\nNot Decimal account is exist! ");
        break;
        }
        else
        input = (int)preinput;
        
        if (accountNumber == input ){
        screen.displayMessage("\nYou cannot transfer to yourself.");
        break;
        }
        
        checkValidAccount = bankDatabase.authenticateTransfer(input);
        
        if (checkValidAccount == -1)
        screen.displayMessage( "\nYou enter a invalid Account Number, Please try again.");
        else{
            while (!EnoughMoney){
            //bankDatabase.getAvailableBalance(accountNumber);
            double availableBalance = bankDatabase.getAvailableBalance( getAccountNumber() );
            screen.displayMessage( "\nPlease enter a Amount of money to transfer: ");
            money = keypad.getDoubleInput();
            if (money > availableBalance){
                screen.displayMessage( "\nInsuffient Money to Transfer.");
                TransferStates = true;
                break;
            }
            else
            {
            EnoughMoney = true;
            bankDatabase.debit( getAccountNumber(), money );
            bankDatabase.credit( input, money);
            screen.displayMessage( "\nSucess! Your Acount Remaining: ");
            double newavailableBalance = bankDatabase.getAvailableBalance( getAccountNumber() );
            screen.displayDollarAmount( newavailableBalance );
            TransferStates = true;
            }
        }
        }
}
}
}    
