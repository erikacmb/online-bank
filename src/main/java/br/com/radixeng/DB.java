package br.com.radixeng;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.HashMap;

public class DB {

    private String url = "mongodb+srv://USER:PASSWORD@pptodescluster-tz9os.mongodb.net/test?retryWrites=true&w=majority";
    private MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(url)).retryWrites(true).build();
    private MongoClient mongoClient = MongoClients.create(settings);
    private MongoDatabase database = mongoClient.getDatabase("test");

    public void storeClient(Document client) {
        MongoCollection<Document> collection = this.database.getCollection("clients");
        try {
            Bson filter = Filters.eq("document", client.get("document"));
            long response = collection.countDocuments(filter);
            if (response == 0) {
                collection.insertOne(client);
                System.out.println("[DB] Client created.");
            } else {
                System.out.println("[DB] Client exists.");
            }

        } catch (Exception e) {
            System.out.println("[DB EXCEPTION clients collection] Cause: " + e.getCause() + " - Message: " + e.getLocalizedMessage());
            throw e;
        }
    }

    public HashMap<String, Object> retrieveDataToAllowCredit(int accountNumber) {
        HashMap<String, Object> response = new HashMap<String, Object>();

        MongoCollection<Document> accountCollection = this.database.getCollection("accounts");
        Bson accountFilter = Filters.eq("number", accountNumber);
        FindIterable<Document> accounts = accountCollection.find(accountFilter);
        response.put("accountType", accounts.first().get("type"));
        ArrayList<String> clients = (ArrayList<String>) accounts.first().get("clients");
        String clientDocument = clients.get(0);
        response.put("clientDocument", clientDocument);

        MongoCollection<Document> clientsCollection = this.database.getCollection("clients");
        Bson clientFilter = Filters.eq("document", clientDocument);
        FindIterable<Document> clientResult = clientsCollection.find(clientFilter);
        response.put("clientIncome", clientResult.first().get("income"));
        return response;
    }

    public boolean updateCredit(int accountNumber, double income) {
        MongoCollection<Document> accountCollection = this.database.getCollection("accounts");
        Bson accountFilter = Filters.eq("number", accountNumber);
        UpdateResult result = accountCollection.updateOne(accountFilter, new Document("$set", new Document("credit", income*0.6)));
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    public int retrieveNumberOfAccounts() {
        MongoCollection<Document> collection = this.database.getCollection("accounts");
        return (int) collection.countDocuments();
    }

    public void storeAccount(Document account) {
        MongoCollection<Document> collection = this.database.getCollection("accounts");
        try {
            Bson filter = Filters.eq("number", account.get("number"));
            long response = collection.countDocuments(filter);
            if (response == 0) {
                collection.insertOne(account);
                System.out.println("[DB] Account created.");
            } else {
                System.out.println("[DB] Account exists.");
            }

        } catch (Exception e) {
            System.out.println("[DB EXCEPTION accounts collection] Cause: " + e.getCause() + " - Message: " + e.getLocalizedMessage());
            throw e;
        }
    }

    public Account retrieveAccount() {
        return null;
    }

    public ArrayList<Account> retrieveAllAccounts() {
        return null;
    }

    public Client retrieveClient() {
        return null;
    }

    public ArrayList<Client> retrieveAllClients() {
        return null;
    }

}
