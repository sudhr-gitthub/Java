import java.util.*;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class Transaction {
    String type;
    float amount;
    Date date;

    public Transaction(String type, float amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date(); // Current time
    }

    @Override
    public String toString() {
        return String.format("%s of %.2f on %s", type, amount, date.toString());
    }
}

class Account {
    String accNo, name, mobile, email;
    float balance;
    List<Transaction> transactions = new ArrayList<>();

    public Account(String accNo, String name, String mobile, String email, float initialDeposit) {
        this.accNo = accNo;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.balance = initialDeposit;
        transactions.add(new Transaction("Account Opened", initialDeposit));
    }

    public void deposit(float amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(float amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
        balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount));
    }

    public void transfer(Account to, float amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance for transfer.");
        }
        this.balance -= amount;
        to.balance += amount;
        transactions.add(new Transaction("Transferred to " + to.accNo, amount));
        to.transactions.add(new Transaction("Received from " + this.accNo, amount));
    }

    public void miniStatement() {
        System.out.println("\n=== Mini Statement for Account: " + accNo + " ===");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        System.out.printf("Current Balance: %.2f\n", balance);
    }

    @Override
    public String toString() {
        return String.format("Acc No: %s | Name: %s | Balance: %.2f", accNo, name, balance);
    }
}

class InputHelper {
    static Scanner sc = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static float readFloat(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Float.parseFloat(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}

public class BankingSystem {
    static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Banking System Menu ====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Fund Transfer");
            System.out.println("5. Mini Statement");
            System.out.println("6. View All Accounts");
            System.out.println("7. Exit");

            int choice = InputHelper.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> miniStatement();
                case 6 -> viewAllAccounts();
                case 7 -> {
                    running = false;
                    System.out.println("Exiting Banking System...");
                }
                default -> System.out.println("Invalid option. Please choose 1-7.");
            }
        }
        InputHelper.sc.close();
    }

    static void createAccount() {
        String accNo = InputHelper.readString("Enter Account Number: ");
        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists!");
            return;
        }
        String name = InputHelper.readString("Enter Name: ");
        String mobile = InputHelper.readString("Enter Mobile No: ");
        String email = InputHelper.readString("Enter Email: ");
        float initialDeposit = InputHelper.readFloat("Enter Initial Deposit: ");

        Account acc = new Account(accNo, name, mobile, email, initialDeposit);
        accounts.put(accNo, acc);
        System.out.println("Account created successfully.");
    }

    static void deposit() {
        String accNo = InputHelper.readString("Enter Account Number: ");
        Account acc = accounts.get(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        float amount = InputHelper.readFloat("Enter amount to deposit: ");
        acc.deposit(amount);
        System.out.println("Deposit successful.");
    }

    static void withdraw() {
        String accNo = InputHelper.readString("Enter Account Number: ");
        Account acc = accounts.get(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        float amount = InputHelper.readFloat("Enter amount to withdraw: ");
        try {
            acc.withdraw(amount);
            System.out.println("Withdrawal successful.");
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void transfer() {
        String fromAccNo = InputHelper.readString("Enter your Account Number: ");
        String toAccNo = InputHelper.readString("Enter recipient Account Number: ");

        Account from = accounts.get(fromAccNo);
        Account to = accounts.get(toAccNo);

        if (from == null || to == null) {
            System.out.println("One or both accounts not found.");
            return;
        }

        float amount = InputHelper.readFloat("Enter amount to transfer: ");
        try {
            from.transfer(to, amount);
            System.out.println("Transfer successful.");
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void miniStatement() {
        String accNo = InputHelper.readString("Enter Account Number: ");
        Account acc = accounts.get(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        acc.miniStatement();
    }

    static void viewAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
        accounts.values().forEach(System.out::println);
    }
}