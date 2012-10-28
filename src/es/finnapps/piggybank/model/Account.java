package es.finnapps.piggybank.model;

public class Account {
    private String accountId;
    private String accountBank;

    public Account(String accountId, String accountBank) {
        super();
        this.accountId = accountId;
        this.accountBank = accountBank;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getAccountBank() {
        return accountBank;
    }
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

}
