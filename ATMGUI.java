import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame{
    private JFrame frame;
    private JPanel loginPanel, mainPanel, balancePanel, withdrawalPanel, transferPanel, balanceInfoPanel, inputPanel;
    private JTextField accountNumberField;
    private JPasswordField passwordField;
    private JButton loginButton, balanceButton, withdrawButton, transferButton, exitButton;
    private JButton[] numberButtons;
    private JButton dotButton, deleteButton;
    private BankDatabase bankDatabase;
    private int currentAccountNumber;
    private JTextField lastFocusedField;

    public ATMGUI() {
        bankDatabase = new BankDatabase(); // Initialize the bank database with accounts
        initialize();
    }

    public void initialize() {
        
        frame = new JFrame("ATM System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new CardLayout());

        // Login Panel Setup
        loginPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints login = new GridBagConstraints();
        JLabel welcome = new JLabel("Welcome! ", JLabel.CENTER);
        welcome.setToolTipText("This is Welcome!");
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        loginPanel.add(welcome, BorderLayout.NORTH);
        login.fill = GridBagConstraints.HORIZONTAL;
        
        //inputPanel.setLayout();
        accountNumberField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        login.insets = new Insets(10, 10, 10, 10);
        login.fill = GridBagConstraints.HORIZONTAL;
        login.gridx = 0;
        login.gridy = 0;
        
        login.anchor = GridBagConstraints.WEST;
        //JLabel Welcome = new JLabel("Welcome! ");
        //JLabel Welcome1 = new JLabel("Welcome to SEHH2242 Group 13 ATM Project! ");
        JLabel AccountN = new JLabel("Account Number: ");
        JLabel AccountP = new JLabel("PIN: ");
        //login.gridx = 0;
        //login.gridy = -1;
        //inputPanel.add(Welcome, login);
        //inputPanel.add(Welcome1, login);
        login.gridx = -1;
        login.gridy = 0;
        inputPanel.add(AccountN, login);
        inputPanel.add(accountNumberField, login);
        login.gridx = 0;
        login.gridy = 1;
        inputPanel.add(AccountP, login);
        login.gridx = 1;
        inputPanel.add(passwordField, login);
        login.gridy = 4;
        inputPanel.add(loginButton, login);
        
        loginButton.addActionListener(new LoginButtonListener());

        // Keypad Panel Setup
        JPanel keypadPanel = createKeypadPanel();
        //loginButton.setPreferredSize(new Dimension(90, 90));
        inputPanel.setPreferredSize(new Dimension(100, 100));
        //keypadPanel.setPreferredSize(new Dimension(200, 250));
        loginPanel.add(inputPanel, BorderLayout.CENTER);
        loginPanel.add(keypadPanel, BorderLayout.SOUTH);

        // Add Focus Listeners to Fields
        accountNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedField = accountNumberField;
            }
        });
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedField = passwordField;
            }
        });

        // Main Panel Setup
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,2,200,10));
        //GridBagConstraints mainGbc = new GridBagConstraints();
        //mainGbc.insets = new Insets(15, 15, 15, 15);
        //mainGbc.fill = GridBagConstraints.HORIZONTAL;

        balanceButton = new JButton("Balance Inquiry");
        withdrawButton = new JButton("Withdraw Cash");
        transferButton = new JButton("Transfer Funds");
        exitButton = new JButton("Logout");

        balanceButton.setPreferredSize(new Dimension(150, 50));
        withdrawButton.setPreferredSize(new Dimension(150, 50));
        transferButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setPreferredSize(new Dimension(150, 50));

        //mainGbc.gridx = 0;
        //mainGbc.gridy = 0;
        mainPanel.add(balanceButton);

        //mainGbc.gridy = 1;
        mainPanel.add(withdrawButton);

        //mainGbc.gridy = 2;
        mainPanel.add(transferButton);

        //mainGbc.gridy = 3;
        mainPanel.add(exitButton);

        balanceButton.addActionListener(e -> switchToPanel(balancePanel));
        withdrawButton.addActionListener(e -> switchToPanel(withdrawalPanel));
        transferButton.addActionListener(e -> switchToPanel(transferPanel));
        exitButton.addActionListener(e -> switchToLogin());

        // Balance Inquiry Panel Setup
        balancePanel = new JPanel(new BorderLayout());
        balanceInfoPanel = new JPanel();
        balanceInfoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel availableBalanceLabel = new JLabel("Available Balance:");
        JTextField availableBalanceField = new JTextField(10);
        availableBalanceField.setEditable(false);
        
        JLabel totalBalanceLabel = new JLabel("Total Balance:");
        JTextField totalBalanceField = new JTextField(10);
        totalBalanceField.setEditable(false);

        gbc.anchor = GridBagConstraints.WEST;
        balanceInfoPanel.add(availableBalanceLabel, gbc);
        gbc.gridx = 1;
        balanceInfoPanel.add(availableBalanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        balanceInfoPanel.add(totalBalanceLabel, gbc);
        gbc.gridx = 1;
        balanceInfoPanel.add(totalBalanceField, gbc);

        JButton balanceExitButton = new JButton("Exit");
        balanceExitButton.setPreferredSize(new Dimension(90, 90));
        balanceExitButton.addActionListener(e -> switchToPanel(mainPanel));

        balancePanel.add(balanceInfoPanel, BorderLayout.CENTER);
        balancePanel.add(balanceExitButton, BorderLayout.SOUTH);

        // Withdrawal Panel Setup
        withdrawalPanel = new JPanel(new BorderLayout());
        JPanel withdrawalInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints withdrawalGbc = new GridBagConstraints();
        withdrawalGbc.insets = new Insets(10, 10, 10, 10);
        withdrawalGbc.fill = GridBagConstraints.HORIZONTAL;

        String[] defaultAmounts = {"200", "400", "600", "800", "1000"};
        JComboBox<String> defaultAmountComboBox = new JComboBox<>(defaultAmounts);
        JTextField customAmountField = new JTextField(10);
        JButton withdrawalSubmitButton = new JButton("Withdraw");
        JButton withdrawalExitButton = new JButton("Exit");

        withdrawalSubmitButton.setPreferredSize(new Dimension(100, 30));
        withdrawalExitButton.setPreferredSize(new Dimension(100, 30));

        withdrawalExitButton.addActionListener(e -> switchToPanel(mainPanel));
        withdrawalSubmitButton.addActionListener(e -> performWithdrawal(defaultAmountComboBox, customAmountField));

        withdrawalGbc.gridx = 0;
        withdrawalGbc.gridy = 0;
        withdrawalInfoPanel.add(new JLabel("Select Default Amount or Enter Custom Amount:"), withdrawalGbc);
        withdrawalGbc.gridy = 1;
        withdrawalInfoPanel.add(defaultAmountComboBox, withdrawalGbc);
        withdrawalGbc.gridy = 2;
        withdrawalInfoPanel.add(customAmountField, withdrawalGbc);
        withdrawalGbc.gridy = 3;
        withdrawalInfoPanel.add(withdrawalSubmitButton, withdrawalGbc);
        withdrawalGbc.gridy = 4;
        withdrawalInfoPanel.add(withdrawalExitButton, withdrawalGbc);

        withdrawalPanel.add(withdrawalInfoPanel, BorderLayout.CENTER);
        withdrawalPanel.add(createKeypadPanel(), BorderLayout.SOUTH);

        // Add Focus Listeners to Fields for Withdrawal Panel
        customAmountField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedField = customAmountField;
            }
        });

        // Transfer Panel Setup
        transferPanel = new JPanel(new BorderLayout());
        JPanel transferInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints transferGbc = new GridBagConstraints();
        transferGbc.insets = new Insets(10, 10, 10, 10);
        transferGbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField targetAccountField = new JTextField(10);
        JTextField transferAmountField = new JTextField(10);
        JButton transferSubmitButton = new JButton("Transfer");
        JButton transferExitButton = new JButton("Exit");

        transferSubmitButton.setPreferredSize(new Dimension(100, 30));
        transferExitButton.setPreferredSize(new Dimension(100, 30));

        transferExitButton.addActionListener(e -> switchToPanel(mainPanel));
        transferSubmitButton.addActionListener(e -> performTransfer(targetAccountField, transferAmountField));

        transferGbc.gridx = 0;
        transferGbc.gridy = 0;
        transferInfoPanel.add(new JLabel("Target Account Number:"), transferGbc);
        transferGbc.gridx = 1;
        transferInfoPanel.add(targetAccountField, transferGbc);
        transferGbc.gridx = 0;
        transferGbc.gridy = 1;
        transferInfoPanel.add(new JLabel("Amount to Transfer:"), transferGbc);
        transferGbc.gridx = 1;
        transferInfoPanel.add(transferAmountField, transferGbc);
        transferGbc.gridx = 0;
        transferGbc.gridy = 2;
        transferGbc.gridwidth = 2;
        transferGbc.anchor = GridBagConstraints.CENTER;
        transferInfoPanel.add(transferSubmitButton, transferGbc);
        transferGbc.gridy = 3;
        transferInfoPanel.add(transferExitButton, transferGbc);

        transferPanel.add(transferInfoPanel, BorderLayout.CENTER);
        transferPanel.add(createKeypadPanel(), BorderLayout.SOUTH);

        // Add Focus Listeners to Fields for Transfer Panel
        targetAccountField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedField = targetAccountField;
            }
        });
        transferAmountField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedField = transferAmountField;
            }
        });

        // Add panels to frame
        balancePanel.setName("Balance");
        withdrawalPanel.setName("Withdrawal");
        transferPanel.setName("Transfer");
        mainPanel.setName("Main");

        frame.add(loginPanel, "Login");
        frame.add(mainPanel, "Main");
        frame.add(balancePanel, "Balance");
        frame.add(withdrawalPanel, "Withdrawal");
        frame.add(transferPanel, "Transfer");

        frame.setVisible(true);
    }

    private JPanel createKeypadPanel() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(4, 3));
        numberButtons = new JButton[10];
        for (int i = 7; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setPreferredSize(new Dimension(80, 80));
            numberButtons[i].addActionListener(new NumberButtonListener());
            keypadPanel.add(numberButtons[i]);
        }
        for (int i = 4; i <= 6; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setPreferredSize(new Dimension(80, 80));
            numberButtons[i].addActionListener(new NumberButtonListener());
            keypadPanel.add(numberButtons[i]);
        }
        for (int i = 1; i <= 3; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setPreferredSize(new Dimension(80, 80));
            numberButtons[i].addActionListener(new NumberButtonListener());
            keypadPanel.add(numberButtons[i]);
        }
        numberButtons[0] = new JButton("0");
        numberButtons[0].setPreferredSize(new Dimension(50, 30));
        numberButtons[0].addActionListener(new NumberButtonListener());
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(50, 30));
        deleteButton.addActionListener(new DeleteButtonListener());
        dotButton = new JButton(".");
        dotButton.setPreferredSize(new Dimension(50, 30));
        dotButton.addActionListener(new NumberButtonListener());

        keypadPanel.add(deleteButton);
        keypadPanel.add(numberButtons[0]);
        keypadPanel.add(dotButton);

        return keypadPanel;
    }

    public void switchToPanel(JPanel panel) {
        Account userAccount = bankDatabase.getAccount(currentAccountNumber);
        if (panel == balancePanel) {
            JTextField availableBalanceField = (JTextField) ((JPanel) balancePanel.getComponent(0)).getComponent(1);
            JTextField totalBalanceField = (JTextField) ((JPanel) balancePanel.getComponent(0)).getComponent(3);
            availableBalanceField.setText(String.valueOf(userAccount.getAvailableBalance()));
            totalBalanceField.setText(String.valueOf(userAccount.getTotalBalance()));
        }
        CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
        cl.show(frame.getContentPane(), panel.getName());
    }

    private void switchToLogin() {
        accountNumberField.setText("");
        passwordField.setText("");
        JOptionPane.showMessageDialog(frame, "Card Ejected, Please take your card. ", "Logout", JOptionPane.INFORMATION_MESSAGE);
        CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
        cl.show(frame.getContentPane(), "Login");
    }

    private void performWithdrawal(JComboBox<String> defaultAmountComboBox, JTextField customAmountField) {
        try {
            int amount;
            if (customAmountField.getText().isEmpty()) {
                amount = Integer.parseInt((String) defaultAmountComboBox.getSelectedItem());
            } else {
                amount = Integer.parseInt(customAmountField.getText());
            }

            Account userAccount = bankDatabase.getAccount(currentAccountNumber);
            if (amount % 100 != 0) {
                JOptionPane.showMessageDialog(frame, "Amount must be in multiples of 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else if (amount > userAccount.getAvailableBalance()) {
                JOptionPane.showMessageDialog(frame, "Insufficient funds.", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                userAccount.debit(amount);
                JOptionPane.showMessageDialog(frame, "Please take your cash.", "Transaction Successful", JOptionPane.INFORMATION_MESSAGE);
                switchToLogin();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            customAmountField.setText("");
        }
    }
    
    private void performTransfer(JTextField targetAccountField, JTextField transferAmountField) {
        try {
            int targetAccount = Integer.parseInt(targetAccountField.getText());
            double amount = Double.parseDouble(transferAmountField.getText());

            if (targetAccount == currentAccountNumber) {
                JOptionPane.showMessageDialog(frame, "You cannot transfer funds to your own account.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account targetUserAccount = bankDatabase.getAccount(targetAccount);
            if (targetUserAccount == null) {
                JOptionPane.showMessageDialog(frame, "Target account does not exist.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account userAccount = bankDatabase.getAccount(currentAccountNumber);

            if (userAccount instanceof ChequeAccount && amount > 10000) {
                JOptionPane.showMessageDialog(frame, "Cheque Account transfer limit is 10000 per transaction.", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amount > userAccount.getAvailableBalance()) {
                JOptionPane.showMessageDialog(frame, "Insufficient funds.", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                userAccount.debit(amount);
                targetUserAccount.credit(amount);
                JOptionPane.showMessageDialog(frame, "Transfer Successful.", "Transaction Successful", JOptionPane.INFORMATION_MESSAGE);
                switchToPanel(mainPanel);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } finally {
            targetAccountField.setText("");
            transferAmountField.setText("");
        }
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int accountNum = Integer.parseInt(accountNumberField.getText());
                int pin = Integer.parseInt(new String(passwordField.getPassword()));
                int authenticationStatus = bankDatabase.authenticateUser(accountNum, pin);
                if (authenticationStatus == 5 ) { // Successful login
                    currentAccountNumber = accountNum;
                    switchToPanel(mainPanel);
                } else if (authenticationStatus == 1)
                    JOptionPane.showMessageDialog(frame, "Invalid account number. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                  else if (authenticationStatus == 2)
                    JOptionPane.showMessageDialog(frame, "Invalid PIN. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
            finally{
                accountNumberField.setText("");
                passwordField.setText("");
            }
        }
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            if (lastFocusedField != null) {
                lastFocusedField.setText(lastFocusedField.getText() + source.getText());
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (lastFocusedField != null && lastFocusedField.getText().length() > 0) {
                String currentText = lastFocusedField.getText();
                lastFocusedField.setText(currentText.substring(0, currentText.length() - currentText.length()));
            }
        }
    }
}

// BankDatabase class based on the provided accounts
class BankDatabase {
    private Account accounts[];

    public BankDatabase() {
       accounts = new Account[ 6 ]; // just 5 accounts for testing
       accounts[ 0 ] = new Account( 12345, 54321, 12000.00, 12000.00 );
       accounts[ 1 ] = new Account( 98765, 56789, 20000.00, 20000.00 );
       accounts[ 2 ] = new SavingAccount( 23456, 54322, 20000.00, 20000.00 );
       accounts[ 3 ] = new ChequeAccount( 54322, 23445, 12000.00, 12000.00 );
       accounts[ 4 ] = new SavingAccount( 54567, 56447, 15000.00, 15000.00 );
       accounts[ 5 ] = new ChequeAccount( 54000, 56975, 23000.00, 23000.00 );
    }

    public Account getAccount(int accountNumber) {
        for (Account currentAccount : accounts) {
            if (currentAccount.getAccountNumber() == accountNumber) {
                return currentAccount;
            }
        }
        return null;
    }

    public int authenticateUser(int userAccountNumber, int userPIN) {
        Account userAccount = getAccount(userAccountNumber);
        if (userAccount == null ) {
            return 1;
        } else
         return userAccount.validatePIN( userPIN );
    }
}

// Base Account class
class Account {
    private int AccountNumber;
    private int Pin;
    private double AvailableBalance;
    private double TotalBalance;

    public Account(int accountNumber, int pin, double availableBalance, double totalBalance) {
        AccountNumber = accountNumber;
        Pin = pin;
        AvailableBalance = availableBalance;
        TotalBalance = totalBalance;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public double getAvailableBalance() {
        return AvailableBalance;
    }

    public double getTotalBalance() {
        return TotalBalance;
    }

    public void credit(double amount) {
        AvailableBalance += amount;
        TotalBalance += amount;
    }

    public void debit(double amount) {
        AvailableBalance -= amount;
        TotalBalance -= amount;
    }

    public int validatePIN(int userPIN) {
        return (userPIN == Pin) ? 5 : 2;
    }
}

// SavingAccount class extending Account
class SavingAccount extends Account {
    public SavingAccount(int accountNumber, int pin, double availableBalance, double totalBalance) {
        super(accountNumber, pin, availableBalance, totalBalance);
    }
}

// ChequeAccount class extending Account
class ChequeAccount extends Account {
    private double limit;

    public ChequeAccount(int accountNumber, int pin, double availableBalance, double totalBalance) {
        super(accountNumber, pin, availableBalance, totalBalance);
        this.limit = 10000;
    }

    public double getLimit() {
        return limit;
    }

    @Override
    public void debit(double amount) {
        if (getAvailableBalance() + limit >= amount) {
            super.debit(amount);
        } else {
            System.out.println("Debit amount exceeds the account limit.");
        }
    }
}
