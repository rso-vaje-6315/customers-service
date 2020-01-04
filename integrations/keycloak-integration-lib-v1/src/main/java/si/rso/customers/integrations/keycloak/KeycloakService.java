package si.rso.customers.integrations.keycloak;

import si.rso.customers.lib.Account;

import java.util.List;

public interface KeycloakService {
    
    List<Account> getAccounts(String query, int offset, int limit);
    
    Account getAccount(String accountId);
    
    String registerAccount(KeycloakAccountRegistration account);
    
}
