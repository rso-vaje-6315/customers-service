package si.rso.customers.integrations.keycloak.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.auth.keycloak.exceptions.KeycloakException;
import com.mjamsek.auth.keycloak.services.KeycloakClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import si.rso.customers.integrations.keycloak.KeycloakAccountRegistration;
import si.rso.customers.integrations.keycloak.KeycloakConfig;
import si.rso.customers.integrations.keycloak.KeycloakService;
import si.rso.customers.integrations.keycloak.api.KeycloakAPI;
import si.rso.customers.lib.Account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class KeycloakServiceImpl implements KeycloakService {
    
    public static final Logger LOG = LogManager.getLogger(KeycloakServiceImpl.class.getSimpleName());
    
    @Inject
    private KeycloakConfig keycloakConfig;
    
    private KeycloakAPI keycloakAPI;
    
    @PostConstruct
    private void init() {
        this.keycloakAPI = RestClientBuilder
            .newBuilder()
            .baseUri(URI.create(this.keycloakConfig.getAuthServerUrl()))
            .build(KeycloakAPI.class);
    }
    
    @Override
    public List<Account> getAccounts(String query, int offset, int limit) {
        return KeycloakClient.callKeycloak((token) -> {
            try {
                return keycloakAPI.getAccounts(
                    keycloakConfig.getRealm(),
                    generateAuthHeader(token),
                    query,
                    limit,
                    offset
                );
            } catch (WebApplicationException e) {
                LOG.error("Error calling keycloak. Status: {}, message: {}", e.getResponse().getStatus(), e.getMessage());
                if (e.getResponse().hasEntity()) {
                    String responseBody = e.getResponse().readEntity(String.class);
                    LOG.error("Response body: {}", responseBody);
                }
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        });
    }
    
    @Override
    public Account getAccount(String accountId) {
        return KeycloakClient.callKeycloak((token) -> {
            try {
                return keycloakAPI.getAccount(
                    keycloakConfig.getRealm(),
                    accountId,
                    generateAuthHeader(token)
                );
            } catch (WebApplicationException e) {
                LOG.error("Error calling keycloak. Status: {}, message: {}", e.getResponse().getStatus(), e.getMessage());
                if (e.getResponse().hasEntity()) {
                    String responseBody = e.getResponse().readEntity(String.class);
                    LOG.error("Response body: {}", responseBody);
                }
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        });
    }
    
    @Override
    public String registerAccount(KeycloakAccountRegistration account) {
        return KeycloakClient.callKeycloak((token) -> {
            try {
                Response response = keycloakAPI.registerAccount(
                    keycloakConfig.getRealm(),
                    generateAuthHeader(token),
                    account
                );
                
                // https://keycloak.mjamsek.com/auth/admin/realms/rso/users/f7b389d6-6812-4fd8-80ca-85f6162bf078
                String locationHeader = response.getHeaderString("Location");
                String[] locationParts = locationHeader.split("/");
                return locationParts[locationParts.length - 1];
            } catch (WebApplicationException e) {
                e.printStackTrace();
                throw new KeycloakException(e);
            }
        });
    }
    
    private String generateAuthHeader(String token) {
        return "Bearer " + token;
    }
}
