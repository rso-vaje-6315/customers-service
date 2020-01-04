package si.rso.customers.api.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import si.rso.customers.config.ServiceConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@ApplicationScoped
public class DoctoredHealthCheck implements HealthCheck {
    
    @Inject
    private ServiceConfig serviceConfig;
    
    @Override
    public HealthCheckResponse call() {
        if (serviceConfig.isSick()) {
            return HealthCheckResponse.named(DoctoredHealthCheck.class.getSimpleName()).down().build();
        }
        return HealthCheckResponse.named(DoctoredHealthCheck.class.getSimpleName()).up().build();
    }
}
