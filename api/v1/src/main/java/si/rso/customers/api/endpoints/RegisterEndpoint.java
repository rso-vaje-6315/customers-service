package si.rso.customers.api.endpoints;

import si.rso.customers.lib.AccountRegistration;
import si.rso.customers.services.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterEndpoint {
    
    @Inject
    private CustomerService customerService;
    
    @POST
    public Response registerUser(AccountRegistration accountRegistration) {
        customerService.registerUser(accountRegistration);
        return Response.ok().build();
    }
    
}
