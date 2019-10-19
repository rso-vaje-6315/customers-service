package si.rso.customers.providers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestScoped
public class AuthContextProvider {
    
    @Inject
    private HttpServletRequest request;
    
    @Produces
    @RequestScoped
    public AuthContext produceAuthContext() {
        Principal principal = request.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        if (keycloakPrincipal == null) return null;
        KeycloakSecurityContext ctx = keycloakPrincipal.getKeycloakSecurityContext();
        AccessToken token = ctx.getToken();
        
        AuthContext context = new AuthContext();
        context.setId(token.getSubject());
        context.setUsername(token.getPreferredUsername());
        return context;
    }

}
