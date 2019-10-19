package si.rso.customers.lib;

import java.util.List;

public class CustomerDetails {
    
    private Account account;
    
    private List<CustomerAddress> addresses;
    
    private List<CustomerPreference> preferences;
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public List<CustomerAddress> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }
    
    public List<CustomerPreference> getPreferences() {
        return preferences;
    }
    
    public void setPreferences(List<CustomerPreference> preferences) {
        this.preferences = preferences;
    }
}
