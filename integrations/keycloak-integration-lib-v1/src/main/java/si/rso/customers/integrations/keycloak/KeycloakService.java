package si.rso.customers.integrations.keycloak;

import com.mjamsek.auth.keycloak.exceptions.KeycloakException;
import si.rso.customers.lib.Account;

import java.util.List;

public interface KeycloakService {
    
    List<Account> getAccounts(String query, int offset, int limit) throws KeycloakException;
    
    Account getAccount(String accountId) throws KeycloakException;
    
    String registerAccount(KeycloakAccountRegistration account) throws KeycloakException;
    
}
