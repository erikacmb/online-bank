package br.com.radixeng;
import java.util.HashMap;

public interface IBank {

    double allowCredit(int account);

    boolean allowLimit(Account account);

    Client createClient(NewClient newClient);

    Account createAccount(NewAccount newAccount);

    HashMap<String, Object> deposit(double amount, int accountNumber);

    HashMap<String, Object> withdraw(double amount, int accountNumber);

}
