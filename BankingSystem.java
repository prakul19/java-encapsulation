import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<BankAccount> accounts = new ArrayList<>();

        try {
            System.out.print("Enter the number of accounts: ");
            int n = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter details for account " + (i + 1) + ":");
                System.out.print("Enter account type (Savings/Current): ");
                String accountType = scanner.nextLine();

                System.out.print("Enter account number: ");
                String accountNumber = scanner.nextLine();

                System.out.print("Enter holder name: ");
                String holderName = scanner.nextLine();

                System.out.print("Enter initial balance: ");
                double balance = scanner.nextDouble();
                scanner.nextLine();

                if (accountType.equalsIgnoreCase("Savings")) {
                    System.out.print("Enter interest rate (e.g., 0.04 for 4%): ");
                    double interestRate = scanner.nextDouble();
                    scanner.nextLine();
                    accounts.add(new SavingsAccount(accountNumber, holderName, balance, interestRate));
                } else if (accountType.equalsIgnoreCase("Current")) {
                    accounts.add(new CurrentAccount(accountNumber, holderName, balance));
                } else {
                    System.out.println("Invalid account type! Skipping.");
                }
            }

            // Displaying details and processing accounts
            for (BankAccount account : accounts) {
                account.displayDetails();
                System.out.println("Interest: $" + account.calculateInterest());
                if (account instanceof Loanable) {
                    Loanable loanAccount = (Loanable) account;
                    loanAccount.applyForLoan();
                    System.out.println(loanAccount.getLoanDetails());
                    System.out.println("Loan Eligibility: $" + loanAccount.calculateLoanEligibility());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

// Abstract class BankAccount
abstract class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // Encapsulation with getters and setters
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    // Concrete methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid or insufficient balance.");
        }
    }

    // Abstract method
    public abstract double calculateInterest();

    // Display account details
    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: $" + balance);
    }
}

// Interface for loan-related behavior
interface Loanable {
    void applyForLoan();
    String getLoanDetails();
    double calculateLoanEligibility();
}

// SavingsAccount subclass
class SavingsAccount extends BankAccount implements Loanable {
    private double interestRate;

    public SavingsAccount(String accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance);
        this.interestRate = interestRate;
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public void applyForLoan() {
        System.out.println("Loan application submitted for Savings Account: " + getAccountNumber());
    }

    @Override
    public String getLoanDetails() {
        return "Savings Account Loan: Max eligibility based on balance.";
    }

    @Override
    public double calculateLoanEligibility() {
        return getBalance() * 2;
    }
}

// CurrentAccount subclass
class CurrentAccount extends BankAccount {
    public CurrentAccount(String accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
    }

    @Override
    public double calculateInterest() {
        return 0; // No interest for current accounts
    }
}

/*
Sample Input:
Enter the number of accounts: 2

Enter details for account 1:
Enter account type (Savings/Current): Savings
Enter account number: 001
Enter holder name: Prakul
Enter initial balance: 5000
Enter interest rate : 0.04

Enter details for account 2:
Enter account type (Savings/Current): Current
Enter account number: 0011
Enter holder name: Parth
Enter initial balance: 2000

Sample Output:

Account Number: 001
Holder Name: Prakul
Balance: $5000.0
Interest: $200.0
Loan application submitted for Savings Account: 001
Savings Account Loan: Max eligibility based on balance.
Loan Eligibility: $10000.0

Account Number: 0011`
Holder Name: Parth
Balance: $2000.0
Interest: $0.0
*/
