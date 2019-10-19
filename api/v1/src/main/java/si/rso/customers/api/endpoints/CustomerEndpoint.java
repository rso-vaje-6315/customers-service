package si.rso.customers.api.endpoints;

import com.kumuluz.ee.security.annotations.Secure;
import si.rso.customers.api.config.AuthRole;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.lib.CustomerPreference;
import si.rso.customers.providers.AuthContext;
import si.rso.customers.services.CustomerService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

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
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response queryAccounts(@QueryParam("query") @DefaultValue("") String query) {
        return Response.ok(customerService.queryAccounts(query)).build();
    }
    
    @GET
    @Path("/{accountId}")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
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
    
    @GET
    @Path("{accountId}/addresses")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getAddresses(@PathParam("accountId") String accountId) {
        return Response.ok(customerService.getAddresses(accountId)).build();
    }
    
    @GET
    @Path("me/addresses")
    @PermitAll
    public Response getAddresses() {
        return Response.ok(customerService.getAddresses(authContext.getId())).build();
    }
    
    @POST
    @Path("me/addresses")
    @PermitAll
    public Response createAddress(CustomerAddress address) {
        CustomerAddress createdAddress = customerService.createAddress(authContext.getId(), address);
        return Response
            .created(URI.create("/addresses/" + createdAddress.getId()))
            .entity(createdAddress).build();
    }
    
    @GET
    @Path("{accountId}/preferences")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getPreferences(@PathParam("accountId") String accountId) {
        return Response.ok(customerService.getPreferences(accountId)).build();
    }
    
    @GET
    @Path("me/preferences")
    @PermitAll
    public Response getPreferences() {
        return Response.ok(customerService.getPreferences(authContext.getId())).build();
    }
    
    @GET
    @Path("{accountId}/preferences/{key}")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getPreference(@PathParam("accountId") String accountId, @PathParam("key") String key) {
        return Response.ok(customerService.getPreference(accountId, key)).build();
    }
    
    @GET
    @Path("me/preferences/{key}")
    @PermitAll
    public Response getPreference(@PathParam("key") String key) {
        return Response.ok(customerService.getPreference(authContext.getId(), key)).build();
    }
    
    @POST
    @Path("me/preferences")
    @PermitAll
    public Response setPreference(CustomerPreference preference) {
        CustomerPreference setPreference = customerService.setPreference(authContext.getId(), preference);
        return Response.ok(setPreference).build();
    }
    
}
