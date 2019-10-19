package si.rso.customers.mappers;

import si.rso.customers.lib.CustomerPreference;
import si.rso.customers.persistence.CustomerPreferencesEntity;

public class PreferencesMapper {
    
    public static CustomerPreference fromEntity(CustomerPreferencesEntity entity) {
        CustomerPreference pref = new CustomerPreference();
        pref.setId(entity.getId());
        pref.setTimestamp(entity.getTimestamp());
        
        pref.setAccountId(entity.getAccountId());
        pref.setKey(entity.getKey());
        pref.setValue(entity.getValue());
        
        return pref;
    }
    
}
