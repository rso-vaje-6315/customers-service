package si.rso.customers.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import si.rso.customers.api.config.AuthRole;
import si.rso.customers.api.endpoints.AddressEndpoint;
import si.rso.customers.api.endpoints.CustomerEndpoint;
import si.rso.customers.api.mappers.RestExceptionMapper;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
@DeclareRoles({AuthRole.SERVICE, AuthRole.ADMIN, AuthRole.SELLER, AuthRole.CUSTOMER})
@OpenAPIDefinition(
    info = @Info(title = "Customer service", version = "1.0.0", contact = @Contact(name = "Miha Jamsek"),
        description = "Orchestration service for integration with Keycloak server."),
    servers = @Server(url = "http://localhost:8080/v1")
)
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(CustomerEndpoint.class);
        classes.add(AddressEndpoint.class);
        
        classes.add(RestExceptionMapper.class);
        
        return classes;
    }
}
