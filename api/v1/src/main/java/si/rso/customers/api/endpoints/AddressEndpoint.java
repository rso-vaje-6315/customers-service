package si.rso.customers.api.endpoints;

import com.kumuluz.ee.security.annotations.Secure;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.providers.AuthContext;
import si.rso.customers.services.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addresses")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
public class AddressEndpoint {
    
    @Inject
    private CustomerService customerService;
    
    @Inject
    private AuthContext authContext;
    
    @GET
    @Path("/{addressId}")
    public Response getAddress(@PathParam("addressId") String addressId) {
        return Response.ok(customerService.getAddress(addressId)).build();
    }
    
    @PUT
    @Path("/{addressId}")
    public Response updateAddress(@PathParam("addressId") String addressId, CustomerAddress address) {
        CustomerAddress updatedAddress = customerService.updateAddress(addressId, address);
        return Response.ok(updatedAddress).build();
    }
    
    @DELETE
    @Path("/{addressId}")
    public Response removeAddress(@PathParam("addressId") String addressId) {
        customerService.removeAddress(addressId);
        return Response.noContent().build();
    }
}
