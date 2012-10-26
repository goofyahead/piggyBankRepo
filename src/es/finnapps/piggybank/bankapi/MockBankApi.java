package es.finnapps.piggybank.bankapi;

import java.util.List;

import es.finnapps.piggybank.model.Operation;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.model.UserInfo;

public class MockBankApi implements BankApiInterface {

    public String createAccount(String token) {
        // TODO Auto-generated method stub
        return "";
    }

    public List<Operation> getOperations(String account) {
        // TODO Auto-generated method stub
        return null;
    }

    public float getAccountAmount(String account) {
        // TODO Auto-generated method stub
        return 0;
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

    public boolean transferFunds(String tokenFrom, String tokenTo) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean depositFunds(String token, float amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean registerClient(UserInfo userInfo) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

}
