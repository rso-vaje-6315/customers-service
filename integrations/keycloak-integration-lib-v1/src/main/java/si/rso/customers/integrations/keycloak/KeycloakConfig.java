package si.rso.customers.integrations.keycloak;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("kc")
public class KeycloakConfig {
    
    @ConfigValue("realm")
    private String realm;
    
    @ConfigValue("auth-server-url")
    private String authServerUrl;
    
    @ConfigValue("client-id")
    private String clientId;
    
    @ConfigValue("auth.client-secret")
    private String clientSecret;
    
    public String getRealm() {
        return realm;
    }
    
    public void setRealm(String realm) {
        this.realm = realm;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public String getAuthServerUrl() {
        return authServerUrl;
    }
    
    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
