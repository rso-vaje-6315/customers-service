package si.rso.customers.api.mappers;

import si.rso.rest.exceptions.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BasicExceptionMapper implements ExceptionMapper<RuntimeException> {
    
    @Override
    public Response toResponse(RuntimeException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setStatusCode(500);
        return Response.status(500).entity(response).build();
    }
}
