package si.rso.customers.api.endpoints;

import com.kumuluz.ee.security.annotations.Secure;
import si.rso.customers.api.config.AuthRole;
import si.rso.customers.providers.AuthContext;
import si.rso.customers.services.CustomerService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
public class CustomerEndpoint {
    
    @Inject
    private CustomerService customerService;
    
    @Inject
    private AuthContext authContext;
    
    @GET
    @RolesAllowed({AuthRole.ADMIN, AuthRole.SELLER})
    public Response queryAccounts(@QueryParam("query") @DefaultValue("") String query) {
        return Response.ok(customerService.queryAccounts(query)).build();
    }
    
    @GET
    @Path("/{accountId}")
    @RolesAllowed({AuthRole.ADMIN, AuthRole.SELLER})
    public Response getAccount(
        @PathParam("accountId") String accountId,
        @QueryParam("expand") @DefaultValue("true") boolean expand
    ) {
        if (expand) {
            return Response.ok(customerService.getCustomer(accountId)).build();
        }
        return Response.ok(customerService.getAccount(accountId)).build();
    }
    
    @GET
    @Path("/me")
    @PermitAll
    public Response getAccount(@QueryParam("expand") @DefaultValue("true") boolean expand) {
        if (expand) {
            return Response.ok(customerService.getCustomer(authContext.getId())).build();
        }
        return Response.ok(customerService.getAccount(authContext.getId())).build();
    }
    
    
    
}
