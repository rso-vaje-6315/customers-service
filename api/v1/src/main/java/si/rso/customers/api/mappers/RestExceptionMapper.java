package si.rso.customers.api.mappers;

import si.rso.rest.exceptions.RestException;
import si.rso.rest.exceptions.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    
    @Override
    public Response toResponse(RestException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatusCode(e.getStatus());
        
        return Response.status(e.getStatus()).entity(response).build();
    }
}
