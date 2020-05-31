package br.com.radixeng;
import java.time.LocalDateTime;

public class Transaction {

    private TransactionType type;
    private String description;
    private LocalDateTime timestamp;
    private double amount;
    private int recipientAccountNumber;

    Transaction(TransactionType type, String description, LocalDateTime timestamp, double amount) {
        this.type = type;
        this.description = description;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public void setRecipientAccountNumber(int recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public int getRecipientAccountNumber() {
        return recipientAccountNumber;
    }
}