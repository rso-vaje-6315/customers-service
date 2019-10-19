package si.rso.customers.integrations.keycloak;

import si.rso.customers.integrations.keycloak.exceptions.KeycloakException;
import si.rso.customers.lib.Account;

import java.util.List;
import java.util.function.Function;

public interface KeycloakService {
    
    /**
     * Retrieves access token from Keycloak server-
     *
     * @return access token
     * @throws KeycloakException on error retrieving token
     */
    String getToken() throws KeycloakException;
    
    /**
     * Perform call to keycloak with auto refreshing of token
     *
     * @param func call to be performed
     * @param <T>  return type of call
     * @return result returned from call
     * @throws KeycloakException on error calling keycloak
     */
    <T> T callKeycloak(Function<String, T> func) throws KeycloakException;
    
    List<Account> getAccounts(String query, int offset, int limit);
    
    Account getAccount(String accountId);
    
}
