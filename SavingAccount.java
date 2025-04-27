public class SavingAccount extends Account {
    private double interestRate = 0.005; // Default interest rate of 0.5% per annum

    // SavingAccount constructor
    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        
    }

    // Returns the interest rate
    public double getInterestRate() {
        return interestRate;
    }

    // Calculates interest and credits it to the account
    public void applyInterest() {
        double interest = getAvailableBalance() * interestRate;
        credit(interest); // Add the interest to the total balance
    }
}