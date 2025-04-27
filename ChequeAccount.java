public class ChequeAccount extends Account {
    private double limit; // Limit for overdraft or withdrawal

    private int chequeaccountNumber; // account number
    private int pin; // PIN for authentication
    private double availableBalance; // funds available for withdrawal
    private double totalBalance;
    // ChequeAccount constructor
    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        limit = 10000;
        chequeaccountNumber = theAccountNumber;
        pin = thePIN;
        availableBalance = theAvailableBalance;
        totalBalance = theTotalBalance;
    }

    @Override
    public int getAccountNumber()
    {
      return chequeaccountNumber;  
    } // end method getAccountNumber
   
    // end method getAccountNumber
   
    public double getLimit() {
        return limit;
    }

    // Override debit method to check against limit before allowing withdrawal
    @Override
    public void debit(double amount) {
        if (getAvailableBalance() + limit >= amount) {
            super.debit(amount); // Proceed with debiting if within limit
        } else {
            System.out.println("Debit amount exceeds the account limit.");
        }
    }
}