package si.rso.customers.integrations.keycloak.api;

import si.rso.customers.integrations.keycloak.TokenResponse;
import si.rso.customers.lib.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface KeycloakAPI {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/realms/{realm}/protocol/openid-connect/token")
    TokenResponse getToken(
        @PathParam("realm") String realm,
        @HeaderParam("Authorization") String authorizationHeader,
        Form form
    );
    
    @GET
    @Path("/admin/realms/{realm}/users")
    List<Account> getAccounts(
        @PathParam("realm") String realm,
        @HeaderParam("Authorization") String authorizationHeader,
        @QueryParam("search") String query,
        @QueryParam("max") int limit,
        @QueryParam("first") int offset
    );
    
    @GET
    @Path("/admin/realms/{realm}/users/{userId}")
    Account getAccount(
        @PathParam("realm") String realm,
        @PathParam("userId") String accountId,
        @HeaderParam("Authorization") String authorizationHeader
    );

}
