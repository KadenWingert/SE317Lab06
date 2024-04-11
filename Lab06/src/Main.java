import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

class Account implements Serializable {
    double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(double amount, Account targetAccount) {
        if (balance - amount >= 0) {
            withdraw(amount);
            targetAccount.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }
}

class CheckingAccount extends Account {
    static final double MAX_DEPOSIT_PER_DAY = 5000;
    static final double MAX_WITHDRAW_PER_DAY = 500;

    public CheckingAccount(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= MAX_DEPOSIT_PER_DAY) {
            super.deposit(amount);
        } else {
            System.out.println("Maximum deposit limit exceeded ($5000)");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= MAX_WITHDRAW_PER_DAY) {
            super.withdraw(amount);
        } else {
            System.out.println("Maximum withdrawal limit exceeded ($500)");
        }
    }
}

class SavingAccount extends Account {
    static final double MAX_DEPOSIT_PER_DAY = 5000;
    static final double MAX_TRANSFER_PER_DAY = 100;

    public SavingAccount(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= MAX_DEPOSIT_PER_DAY) {
            super.deposit(amount);
        } else {
            System.out.println("Maximum deposit limit exceeded ($5000)");
        }
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Cannot withdraw from saving account.");
    }

    @Override
    public void transfer(double amount, Account targetAccount) {
        if (amount <= MAX_TRANSFER_PER_DAY) {
            super.transfer(amount, targetAccount);
        } else {
            System.out.println("Maximum transfer limit exceeded ($100)");
        }
    }
}

class UtilityCompany {
    Map<String, String> accounts;

    public UtilityCompany() {
        accounts = new HashMap<>();
    }

    public void createAccount(String username, String password) {
        accounts.put(username, password);
        System.out.println("Utility account created successfully.");

        // Update bankAccounts with the new utility account
        Map<String, Account> utilityAccount = new HashMap<>();
        utilityAccount.put("utility_account", new Account(0)); // Create a new utility account
        Main.bankAccounts.put(username, utilityAccount);

        // Debugging: Print bankAccounts
        System.out.println("Updated bankAccounts:");
        System.out.println(Main.bankAccounts);
    }

    public boolean authenticate(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }

    public String getUtilityAccountNumber(String username) {
        // Generate and return a 6-digit account number (dummy implementation)
        return "123456";
    }
}

public class Main {
    static Map<String, Map<String, Account>> bankAccounts;
    static UtilityCompany utilityCompany;
    static final String DATA_FILE = "bank_accounts.dat";

    public static void main(String[] args) {
        bankAccounts = loadData();
        utilityCompany = new UtilityCompany();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Create Utility Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (bankAccounts.containsKey(username) && bankAccounts.get(username).containsKey(password)) {
                    Account checkingAccount = bankAccounts.get(username).get("checking");
                    Account savingAccount = bankAccounts.get(username).get("saving");

                    while (true) {
                        System.out.println("\n1. Deposit to Checking Account");
                        System.out.println("2. Withdraw from Checking Account");
                        System.out.println("3. Transfer from Checking to Saving Account");
                        System.out.println("4. Pay Utility Bill");
                        System.out.println("5. Check Checking Account Balance");
                        System.out.println("6. Check Saving Account Balance");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");
                        int subChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (subChoice) {
                            case 1:
                                System.out.print("Enter deposit amount: ");
                                double depositAmount = scanner.nextDouble();
                                checkingAccount.deposit(depositAmount);
                                System.out.println("Deposit successful.");
                                break;
                            case 2:
                                System.out.print("Enter withdrawal amount: ");
                                double withdrawAmount = scanner.nextDouble();
                                checkingAccount.withdraw(withdrawAmount);
                                break;
                            case 3:
                                System.out.print("Enter transfer amount: ");
                                double transferAmount = scanner.nextDouble();
                                checkingAccount.transfer(transferAmount, savingAccount);
                                break;
                            case 4:
                                System.out.println("Functionality not implemented yet.");
                                break;
                            case 5:
                                System.out.println("Checking Account Balance: $" + checkingAccount.balance);
                                break;
                            case 6:
                                System.out.println("Saving Account Balance: $" + savingAccount.balance);
                                break;
                            case 7:
                                saveData();
                                return;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                utilityCompany.createAccount(username, password);
                // Create new bank accounts for the user
                bankAccounts.put(username, new HashMap<>());
                bankAccounts.get(username).put("checking", new CheckingAccount(0));
                bankAccounts.get(username).put("saving", new SavingAccount(0));
            } else if (choice == 3) {
                saveData();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(bankAccounts);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static Map<String, Map<String, Account>> loadData() {
        Map<String, Map<String, Account>> data = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            data = (Map<String, Map<String, Account>>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
}
