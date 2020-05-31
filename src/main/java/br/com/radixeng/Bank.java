package br.com.radixeng;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Bank implements IBank {

    private ArrayList<Account> accounts;

    private static Bank instance = new Bank();

    public static Bank getBank() {
        if (Bank.instance != null) {
            return Bank.instance;
        } else {
            Bank.instance = new Bank();
            return Bank.instance;
        }
    }

    Bank() {
        this.accounts = new ArrayList<>();
    };

    @Override
    public Client createClient(NewClient newClient) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", newClient.clientName);
        data.put("document", newClient.clientDocument);
        data.put("income", newClient.clientIncome);
        DB db = new DB();
        db.storeClient(new Document(data));
        return new Client(newClient.clientName, newClient.clientDocument, newClient.clientIncome);
    }

    @Override
    public Account createAccount(NewAccount newAccount) {
        DB db = new DB();
        int accountNumber = db.retrieveNumberOfAccounts();
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("type", newAccount.accountType);
        data.put("number", accountNumber + 1);
        data.put("clients", newAccount.clientsDocuments);
        data.put("credit", 0.0);
        data.put("limit", 0.0);
        data.put("balance", 0.0);
        data.put("transactions", new ArrayList<>());
        db.storeAccount(new Document(data));
        return new Account(AccountType.valueOf(newAccount.accountType), accountNumber + 1, newAccount.clientsDocuments);
    }

    @Override
    public double allowCredit(int accountNumber) {
        DB db = new DB();
        HashMap<String, Object> response = db.retrieveDataToAllowCredit(accountNumber);
        double income = (double) response.get("clientIncome");
        AccountType type = AccountType.valueOf((String) response.get("accountType"));
        if ((type == AccountType.CHECKING || type == AccountType.CORPORATE || type == AccountType.CORPORATE) && income > 5000.0) {
            if (db.updateCredit(accountNumber, (double)response.get("clientIncome"))) {
                return (double)response.get("clientIncome")*0.6;
            } else {
                return 0.0;
            }
        } else {
            return 0.0;
        }
    }

    @Override
    public boolean allowLimit(Account account) {
        return true;
    }

    @Override
    public HashMap<String, Object> deposit(double amount, int accountNumber) {
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "";
        int code = 0;
        for (Account account : this.accounts) {
            if (account.getNumber() == accountNumber) {
                account.deposit(amount, timestamp);
                message = "Added $ " + amount + " to account number " + accountNumber + ".";
                code = 1;
            } else {
                message = "Account not found.";
                code = 0;
            }
        }

        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("timestamp", timestamp);
        response.put("message", message);
        response.put("code", code);
        return response;

    }

    @Override
    public HashMap<String, Object> withdraw(double amount, int accountNumber) {
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "";
        int code = 0;
        for (Account account : this.accounts) {
            if (account.getNumber() == accountNumber) {

                if (account.getAvailableBalance() > 0.0 && account.getAvailableBalance() >= amount) {
                    account.withdraw(amount, timestamp);
                    message = "Removed $ " + amount + " from account number " + accountNumber + ".";
                    code = 1;
                } else {
                    message = "Not enough funds on account number " + accountNumber + ".";
                    code = 2;
                }

            } else {
                message = "Account not found.";
                code = 0;
            }
        }

        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("timestamp", timestamp);
        response.put("message", message);
        response.put("code", code);
        return response;
    }
}
