package si.rso.customers.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle(value = "service-config", watch = true)
public class ServiceConfig {
    
    @ConfigValue(value = "sick", watch = true)
    private boolean sick;
    
    @ConfigValue("maintenance")
    private boolean maintenance;
    
    @ConfigValue("registration-enabled")
    private boolean registrationEnabled;
    
    public boolean isSick() {
        return sick;
    }
    
    public void setSick(boolean sick) {
        this.sick = sick;
    }
    
    public boolean isMaintenance() {
        return maintenance;
    }
    
    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }
    
    public boolean isRegistrationEnabled() {
        return registrationEnabled;
    }
    
    public void setRegistrationEnabled(boolean registrationEnabled) {
        this.registrationEnabled = registrationEnabled;
    }
}
