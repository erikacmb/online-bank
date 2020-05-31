package br.com.radixeng;
import java.time.LocalDateTime;

public interface IAccount {

    void updateCredit(double amount);

    void updateLimit(double amount);

    double getAvailableBalance();

    void deposit(double amount, LocalDateTime timestamp);

    void withdraw(double amount, LocalDateTime timestamp);

    void updateTransactionList(Transaction transaction);

}
