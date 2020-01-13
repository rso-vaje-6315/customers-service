package si.rso.customers.api.filters;

import com.kumuluz.ee.logs.cdi.Log;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

@Log
@Provider
public class LogFilter implements ContainerRequestFilter {
    
    public static final String LOG_HEADER = "X-Log-Id";
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String uniqueRequestId = requestContext.getHeaderString(LOG_HEADER);
        if (uniqueRequestId == null) {
            requestContext.getHeaders().putSingle(LOG_HEADER, UUID.randomUUID().toString());
        }
    }
}
