package si.rso.customers.api;

import si.rso.customers.api.config.AuthRole;
import si.rso.customers.api.endpoints.CustomerEndpoint;
import si.rso.customers.api.mappers.RestExceptionMapper;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
@DeclareRoles({AuthRole.ADMIN, AuthRole.SELLER, AuthRole.CUSTOMER})
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(CustomerEndpoint.class);
        classes.add(RestExceptionMapper.class);
        
        return classes;
    }
}
