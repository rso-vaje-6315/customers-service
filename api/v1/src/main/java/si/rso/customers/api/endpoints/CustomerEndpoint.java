package si.rso.customers.api.endpoints;

import com.kumuluz.ee.logs.cdi.Log;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.RolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.auth.keycloak.models.AuthContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.rso.customers.lib.Account;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.lib.CustomerDetails;
import si.rso.customers.lib.CustomerPreference;
import si.rso.customers.lib.config.AuthRole;
import si.rso.customers.services.CustomerService;
import si.rso.rest.exceptions.dto.ExceptionResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Log
@Path("/customers")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class CustomerEndpoint {
    
    @Inject
    private CustomerService customerService;
    
    @Inject
    private AuthContext authContext;
    
    @Operation(description = "Retrieves accounts that match query.",
        summary = "Returns account infos.", tags = "account",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns accounts.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class))))
        })
    @GET
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    @Timed(name = "account-query-time")
    public Response queryAccounts(@QueryParam("query") @DefaultValue("") String query) {
        return Response.ok(customerService.queryAccounts(query)).build();
    }
    
    @Operation(description = "Retrieves account information for user.",
        summary = "Returns account info.", tags = "account",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns account.",
                content = @Content(schema = @Schema(oneOf = {CustomerDetails.class, Account.class}))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("/{accountId}")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    @Timed(name = "get-account-time")
    public Response getAccount(
        @PathParam("accountId") String accountId,
        @QueryParam("expand") @DefaultValue("true") boolean expand
    ) {
        if (expand) {
            return Response.ok(customerService.getCustomer(accountId)).build();
        }
        return Response.ok(customerService.getAccount(accountId)).build();
    }
    
    @Operation(description = "Retrieves account information for current user.",
        summary = "Returns account info.", tags = "account",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns account.",
                content = @Content(schema = @Schema(oneOf = {CustomerDetails.class, Account.class}))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("/me")
    @AuthenticatedAllowed
    @Timed(name = "get-account-time-me")
    public Response getAccount(@QueryParam("expand") @DefaultValue("true") boolean expand) {
        if (expand) {
            return Response.ok(customerService.getCustomer(authContext.getId())).build();
        }
        return Response.ok(customerService.getAccount(authContext.getId())).build();
    }
    
    @Operation(description = "Retrieves all addresses for user.",
        summary = "Returns addresses.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns addresses.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerAddress.class)))),
            @ApiResponse(responseCode = "404", description = "Addresses for user not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("{accountId}/addresses")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getAddresses(@PathParam("accountId") String accountId) {
        return Response.ok(customerService.getAddresses(accountId)).build();
    }
    
    @Operation(description = "Retrieves all addresses for current user.",
        summary = "Returns addresses.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns addresses.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerAddress.class)))),
            @ApiResponse(responseCode = "404", description = "Addresses for user not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("me/addresses")
    @AuthenticatedAllowed
    public Response getAddresses() {
        return Response.ok(customerService.getAddresses(authContext.getId())).build();
    }
    
    @Operation(description = "Creates address for current user.",
        summary = "Creates address.", tags = "address",
        responses = {
            @ApiResponse(responseCode = "201", description = "Creates address.",
                content = @Content(schema = @Schema(implementation = CustomerAddress.class)))
        })
    @POST
    @Path("me/addresses")
    @AuthenticatedAllowed
    @Counted(name = "create-address-count")
    public Response createAddress(CustomerAddress address) {
        CustomerAddress createdAddress = customerService.createAddress(authContext.getId(), address);
        return Response
            .created(URI.create("/addresses/" + createdAddress.getId()))
            .entity(createdAddress).build();
    }
    
    @Operation(description = "Retrieves all preferences for user.",
        summary = "Returns preferences.", tags = "preference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns preferences.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerPreference.class)))),
            @ApiResponse(responseCode = "404", description = "Preferences for user not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("{accountId}/preferences")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getPreferences(@PathParam("accountId") String accountId) {
        return Response.ok(customerService.getPreferences(accountId)).build();
    }
    
    @Operation(description = "Retrieves all preferences for current user.",
        summary = "Returns preferences.", tags = "preference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns preferences.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerPreference.class)))),
            @ApiResponse(responseCode = "404", description = "Preferences for user not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("me/preferences")
    @AuthenticatedAllowed
    public Response getPreferences() {
        return Response.ok(customerService.getPreferences(authContext.getId())).build();
    }
    
    @Operation(description = "Retrieve preference value for user.",
        summary = "Returns preference.", tags = "preference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns preference.",
                content = @Content(schema = @Schema(implementation = CustomerPreference.class))),
            @ApiResponse(responseCode = "404", description = "Preference not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("{accountId}/preferences/{key}")
    @RolesAllowed({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER})
    public Response getPreference(@PathParam("accountId") String accountId, @PathParam("key") String key) {
        return Response.ok(customerService.getPreference(accountId, key)).build();
    }
    
    @Operation(description = "Retrieve preference value for current user.",
        summary = "Returns preference.", tags = "preference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns preference.",
                content = @Content(schema = @Schema(implementation = CustomerPreference.class))),
            @ApiResponse(responseCode = "404", description = "Preference not found.",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    @GET
    @Path("me/preferences/{key}")
    @AuthenticatedAllowed
    public Response getPreference(@PathParam("key") String key) {
        return Response.ok(customerService.getPreference(authContext.getId(), key)).build();
    }
    
    @Operation(description = "Updates or creates preference for current user.",
        summary = "sets preference.", tags = "preference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Preference was set.",
                content = @Content(schema = @Schema(implementation = CustomerPreference.class)))
        })
    @POST
    @Path("me/preferences")
    @AuthenticatedAllowed
    @Counted(name = "set-preference-count")
    public Response setPreference(CustomerPreference preference) {
        CustomerPreference setPreference = customerService.setPreference(authContext.getId(), preference);
        return Response.ok(setPreference).build();
    }
    
}
