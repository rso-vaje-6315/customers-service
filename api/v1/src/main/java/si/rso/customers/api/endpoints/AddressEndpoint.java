package si.rso.customers.api.endpoints;

import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.RolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.lib.config.AuthRole;
import si.rso.customers.services.CustomerService;
import si.rso.rest.exceptions.dto.ExceptionResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addresses")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class AddressEndpoint {
    
    @Inject
    private CustomerService customerService;
    
    @Operation(description = "Returns address with given id.",
        summary = "Returns address.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns address.",
                content = @Content(schema = @Schema(implementation = CustomerAddress.class))),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("/{addressId}")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getAddress(@PathParam("addressId") String addressId) {
        return Response.ok(customerService.getAddress(addressId)).build();
    }
    
    @Operation(description = "Updates address with new information.",
        summary = "Updates address.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "200", description = "Address was updated.",
                content = @Content(schema = @Schema(implementation = CustomerAddress.class))),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @PUT
    @Path("/{addressId}")
    @AuthenticatedAllowed
    public Response updateAddress(@PathParam("addressId") String addressId, CustomerAddress address) {
        CustomerAddress updatedAddress = customerService.updateAddress(addressId, address);
        return Response.ok(updatedAddress).build();
    }
    
    @Operation(description = "Deletes address.",
        summary = "Deletes address.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "200", description = "Address was deleted.",
                content = @Content(schema = @Schema(implementation = CustomerAddress.class))),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @DELETE
    @Path("/{addressId}")
    @AuthenticatedAllowed
    public Response removeAddress(@PathParam("addressId") String addressId) {
        customerService.removeAddress(addressId);
        return Response.noContent().build();
    }
}
