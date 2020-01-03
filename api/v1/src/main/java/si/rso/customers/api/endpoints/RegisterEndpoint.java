package si.rso.customers.api.endpoints;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import si.rso.customers.lib.AccountRegistration;
import si.rso.customers.services.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterEndpoint {
    
    private static final Logger LOG = LogManager.getLogger(RegisterEndpoint.class.getSimpleName());
    
    @Inject
    private CustomerService customerService;
    
    @GET
    @Log
    public Response test() {
        return Response.ok().build();
    }
    
    @POST
    public Response registerUser(AccountRegistration accountRegistration) {
        customerService.registerUser(accountRegistration);
        return Response.ok().build();
    }
    
}
