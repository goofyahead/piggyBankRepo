package es.finnapps.piggybank.bankapi;

import java.util.List;

import es.finnapps.piggybank.model.Account;
import es.finnapps.piggybank.model.Operation;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.model.UserInfo;

public class MockBankApi implements BankApiInterface {

    public Account createAccount(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Operation> getOperations(String account) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Piggy> getAccounts(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean createClient(String args) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getAccountStatus(String account) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean registerClient(UserInfo userInfo) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    public long getAccountAmount(String account, String token) {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean transferFunds(String fromAccount, String toAccount, String token, String concept, String userNumber,
            float amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean depositFunds(String token, String account) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean transferFundsForShared(String fromAccount, String toAccount, String token, String concept,
            String userNumber, float amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getAccountNumber(String accountId, String token) {
        // TODO Auto-generated method stub
        return null;
    }

}
