package si.rso.customers.integrations.keycloak.impl;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import si.rso.customers.integrations.keycloak.KeycloakConfig;
import si.rso.customers.integrations.keycloak.KeycloakService;
import si.rso.customers.integrations.keycloak.TokenResponse;
import si.rso.customers.integrations.keycloak.api.KeycloakAPI;
import si.rso.customers.integrations.keycloak.exceptions.KeycloakException;
import si.rso.customers.lib.Account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class KeycloakServiceImpl implements KeycloakService {
    
    @Inject
    private KeycloakConfig keycloakConfig;
    
    private KeycloakAPI keycloakAPI;
    
    private String accessToken = null;
    
    @PostConstruct
    private void init() {
        this.keycloakAPI = RestClientBuilder
            .newBuilder()
            .baseUri(URI.create(this.keycloakConfig.getAuthServerUrl()))
            .build(KeycloakAPI.class);
    }
    
    @Override
    public String getToken() throws KeycloakException {
        
        Form form = new Form();
        form.param("grant_type", "client_credentials");
        
        String authHeader = generateAuthHeader();
        
        try {
            TokenResponse response = keycloakAPI.getToken(keycloakConfig.getRealm(), authHeader, form);
            this.accessToken = response.getAccessToken();
            return response.getAccessToken();
        } catch (WebApplicationException e) {
            e.printStackTrace();
            throw new KeycloakException(e);
        }
    }
    
    @Override
    public <T> T callKeycloak(Function<String, T> func) throws KeycloakException {
        // if no token present, retrieve one, otherwise used cached one
        if (this.accessToken == null) {
            getToken();
        }
        
        try {
            // call requested function
            return func.apply(this.accessToken);
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                // failed due to old token
                getToken();
                try {
                    // retry call with newly gathered token
                    return func.apply(this.accessToken);
                } catch (WebApplicationException e2) {
                    // failed call for other reasons
                    e2.printStackTrace();
                    throw new KeycloakException(e2);
                }
            } else {
                // failed call for other reasons
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        }
    }
    
    @Override
    public List<Account> getAccounts(String query, int offset, int limit) {
        return callKeycloak((token) -> {
            try {
                return keycloakAPI.getAccounts(
                    keycloakConfig.getRealm(),
                    generateAuthHeader(token),
                    query,
                    limit,
                    offset
                );
            } catch (WebApplicationException e) {
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        });
    }
    
    @Override
    public Account getAccount(String accountId) {
        return callKeycloak((token) -> {
            try {
                return keycloakAPI.getAccount(
                    keycloakConfig.getRealm(),
                    accountId,
                    generateAuthHeader(token)
                );
            } catch (WebApplicationException e) {
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        });
    }
    
    private String generateAuthHeader() {
        String credentials = keycloakConfig.getClientId() + ":" + keycloakConfig.getClientSecret();
        String credentialsEncoded = new String(Base64.getEncoder().encode(credentials.getBytes()));
        return "Basic " + credentialsEncoded;
    }
    
    private String generateAuthHeader(String token) {
        return "Bearer " + token;
    }
}
