package br.com.radixeng;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account implements IAccount {
    private AccountType type;
    private int number;
    private ArrayList<String> clients;
    private double credit;
    private double limit;
    private double balance;
    private ArrayList<Transaction> transactions;

    Account(AccountType type, int number, ArrayList<String> clients) {
        this.type = type;
        this.number = number;
        this.clients = new ArrayList<String>(clients);
        this.credit = 0.0;
        this.limit = 0.0;
        this.balance = 0.0;
        this.transactions = new ArrayList<Transaction>();
    };

    public AccountType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<String> getClients() {
        return clients;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public double getAvailableBalance() {
        return balance + limit;
    }

    public double getCredit() {
        return credit;
    }

    public double getLimit() {
        return limit;
    }

    @Override
    public void updateCredit(double amount) {
        this.credit = amount;
    }

    @Override
    public void updateLimit(double amount) {
        this.limit = amount;
    }

    @Override
    public void deposit(double amount, LocalDateTime timestamp) {
        Transaction deposit = new Transaction(TransactionType.DEPOSIT, "Deposit", timestamp, amount);
        updateTransactionList(deposit);
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount, LocalDateTime timestamp) {
        Transaction withdrawal = new Transaction(TransactionType.WITHDRAWAL, "Withdrawal", timestamp, amount*-1);
        updateTransactionList(withdrawal);
        this.balance -= amount;
    }

    @Override
    public void updateTransactionList(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}