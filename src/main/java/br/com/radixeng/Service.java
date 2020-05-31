package br.com.radixeng;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

class NewClient {
    public String clientName;
    public String clientDocument;
    public double clientIncome;
}

class NewAccount {
    public ArrayList<String> clientsDocuments;
    public String accountType;
}

class AccountNumber {
    public int accountNumber;
}


abstract class BasicResponse {
    public int code;
    public String message;
}

class ClientResponse extends BasicResponse {
    public String name;
}

class AccountResponse extends BasicResponse {
    public AccountType accountType;
    public int accountNumber;
}

class CreditResponse extends BasicResponse {
    public double amountOfCredit;
}

@Path("/")
public class Service {

    static private IBank bank = Bank.getBank();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Welcome to your online bank!";
    }

    @POST
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse postToClient(final NewClient newClient) {
        Client created = bank.createClient(newClient);
        ClientResponse response = new ClientResponse();
        if (created != null) {
            response.code = 1;
            response.name = created.getName();
            response.message = "Success.";
        } else {
            response.code = 0;
            response.message = "Oops... Something happened!";
        }

        return response;
    }

    @POST
    @Path("/account")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountResponse postToAccount(final NewAccount newAccount) {
        AccountResponse response = new AccountResponse();
        Account created = bank.createAccount(newAccount);
        if (created != null) {
            response.code = 1;
            response.message = "Success.";
            response.accountNumber = created.getNumber();
            response.accountType = created.getType();
        } else {
            response.code = 0;
            response.message = "Oops... Something happened!";
        }
        return response;
    }

    @POST
    @Path("/account/credit")
    @Produces(MediaType.APPLICATION_JSON)
    public CreditResponse postToAccountCredit(final AccountNumber accountNumber) {
        CreditResponse response = new CreditResponse();
        response.amountOfCredit = bank.allowCredit(accountNumber.accountNumber);
        response.code = 1;
        response.message = "Check the amount of credit allowed to account number " + accountNumber.accountNumber + ".";
        return response;
    }

    @POST
    @Path("/account/limit")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToLimit() {

    }

    @POST
    @Path("/account/deposit")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToDeposit() {

    }

    @POST
    @Path("/account/withdrawal")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToWithdrawal() {

    }

    @POST
    @Path("/account/addClient/{document}")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToAddClient() {

    }

    @POST
    @Path("/account/transfer/from/{sender_number}/to/{recipient_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToTransfer() {

    }

    @GET
    @Path("/account/all")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllAccounts() {

    }

    @GET
    @Path("/account/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getSingleAccounts() {

    }

    @POST
    @Path("/client/updateIncome")
    @Produces(MediaType.APPLICATION_JSON)
    public void postToUpdateIncome() {

    }

    @GET
    @Path("/client/all")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllClients() {

    }

    @GET
    @Path("/client/{document}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getSingleClient() {

    }

}
