package si.rso.customers.api.mappers;

import com.mjamsek.auth.keycloak.exceptions.KeycloakException;
import si.rso.rest.exceptions.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class KeycloakExceptionMapper implements ExceptionMapper<KeycloakException> {
    @Override
    public Response toResponse(KeycloakException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setStatusCode(500);
        return Response.status(500).entity(response).build();
    }
}
