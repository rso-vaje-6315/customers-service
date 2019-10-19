package si.rso.customers.integrations.keycloak.exceptions;

import si.rso.rest.exceptions.RestException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class KeycloakException extends RestException {
    
    public KeycloakException(String message) {
        super(message);
    }
    
    public KeycloakException(String message, Response.Status status) {
        super(message, status);
    }
    
    public KeycloakException(String message, int status) {
        super(message, status);
    }
    
    public KeycloakException(WebApplicationException e) {
        super(e.getMessage(), e.getResponse().getStatus());
    }
}
