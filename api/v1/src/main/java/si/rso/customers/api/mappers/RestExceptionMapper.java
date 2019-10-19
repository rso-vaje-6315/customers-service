package si.rso.customers.api.mappers;

import si.rso.rest.exceptions.RestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    
    @Override
    public Response toResponse(RestException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());
        return Response.status(e.getStatus()).entity(body).build();
    }
}
