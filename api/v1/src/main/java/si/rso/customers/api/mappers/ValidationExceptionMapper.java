package si.rso.customers.api.mappers;

import si.rso.rest.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(exception.getResponse().getStatus()).entity(exception.getResponse()).build();
    }
}
