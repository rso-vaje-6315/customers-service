package si.rso.customers.api.endpoints;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
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

@Log
@Path("/register")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterEndpoint {
    
    private static final Logger LOG = LogManager.getLogger(RegisterEndpoint.class.getSimpleName());
    
    @Inject
    private CustomerService customerService;
    
    @Operation(description = "Registers user.",
        summary = "registers user.", tags = "account",
        responses = {
            @ApiResponse(responseCode = "200", description = "User was created.")
        })
    @POST
    @Timed(name = "register-user-time")
    @Counted(name = "register-user-count")
    public Response registerUser(AccountRegistration accountRegistration) {
        customerService.registerUser(accountRegistration);
        return Response.ok().build();
    }
    
}
