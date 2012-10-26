package es.finnapps.piggybank.bankapi;

import java.util.List;

import es.finnapps.piggybank.model.Operation;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.model.UserInfo;

/**
 * API specs
 * http://www.finappsparty.com/wp-content/uploads/2012/10/FinAppsPartyAPI
 * -TechnicalSpecification.pdf
 */
public interface BankApiInterface {

    public String createAccount(String token);

    public List<Operation> getOperations(String account);

    public long getAccountAmount(String account, String token);

    public List<Piggy> getAccounts(String token);

    public String getAccountStatus(String account);

    public boolean transferFunds(String fromAccount, String toAccount, String token, String concept, String userNumber, float amount);

    public boolean depositFunds(String token, float amount);

    public boolean registerClient(UserInfo userInfo);

    public String getToken ();
}
