package si.rso.customers.services;

import si.rso.customers.lib.*;

import java.util.List;

public interface CustomerService {
    
    List<Account> queryAccounts(String query);
    
    Account getAccount(String accountId);
    
    CustomerDetails getCustomer(String accountId);
    
    List<CustomerAddress> getAddresses(String accountId);
    
    CustomerAddress getAddress(String addressId);
    
    CustomerAddress createAddress(String accountId, CustomerAddress address);
    
    CustomerAddress updateAddress(String addressId, CustomerAddress address);
    
    void removeAddress(String addressId);
    
    List<CustomerPreference> getPreferences(String accountId);
    
    CustomerPreference getPreference(String accountId, String key);
    
    CustomerPreference setPreference(String accountId, CustomerPreference preference);
    
    void registerUser(AccountRegistration account);
    
}
