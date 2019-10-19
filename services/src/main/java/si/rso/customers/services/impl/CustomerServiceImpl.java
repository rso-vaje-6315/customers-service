package si.rso.customers.services.impl;

import si.rso.customers.integrations.keycloak.KeycloakService;
import si.rso.customers.lib.Account;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.lib.CustomerDetails;
import si.rso.customers.lib.CustomerPreference;
import si.rso.customers.mappers.AddressMapper;
import si.rso.customers.mappers.PreferencesMapper;
import si.rso.customers.persistence.AddressEntity;
import si.rso.customers.persistence.CustomerPreferencesEntity;
import si.rso.customers.services.CustomerService;
import si.rso.rest.exceptions.NotFoundException;
import si.rso.rest.exceptions.RestException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private KeycloakService keycloakService;
    
    @Override
    public List<Account> queryAccounts(String query) {
        return keycloakService.getAccounts(query, 0, 25);
    }
    
    @Override
    public Account getAccount(String accountId) {
        return keycloakService.getAccount(accountId);
    }
    
    @Override
    public CustomerDetails getCustomer(String accountId) {
        CustomerDetails details = new CustomerDetails();
        
        Account account = keycloakService.getAccount(accountId);
        details.setAccount(account);
        
        List<CustomerAddress> addresses = getAddresses(accountId);
        details.setAddresses(addresses);
        
        List<CustomerPreference> preferences = getPreferences(accountId);
        details.setPreferences(preferences);
        
        return details;
    }
    
    @Override
    public List<CustomerAddress> getAddresses(String accountId) {
        TypedQuery<AddressEntity> addressQuery = em.createNamedQuery(AddressEntity.FIND_BY_ACCOUNT_ID, AddressEntity.class);
        addressQuery.setParameter("accountId", accountId);
        return addressQuery.getResultStream()
            .map(AddressMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public CustomerAddress getAddress(String addressId) {
        AddressEntity entity = em.find(AddressEntity.class, addressId);
        if (entity == null) {
            throw new NotFoundException(AddressEntity.class, addressId);
        }
        return AddressMapper.fromEntity(entity);
    }
    
    @Override
    public CustomerAddress createAddress(CustomerAddress address) {
        return null;
    }
    
    @Override
    public CustomerAddress updateAddress(String addressId, CustomerAddress address) {
        return null;
    }
    
    @Override
    public void removeAddress(String addressId) {
        AddressEntity entity = em.find(AddressEntity.class, addressId);
        if (entity == null) {
            throw new NotFoundException(AddressEntity.class, addressId);
        }
        em.remove(entity);
    }
    
    @Override
    public List<CustomerPreference> getPreferences(String accountId) {
        TypedQuery<CustomerPreferencesEntity> preferencesQuery = em.createNamedQuery(CustomerPreferencesEntity.FIND_BY_ACCOUNT_ID, CustomerPreferencesEntity.class);
        preferencesQuery.setParameter("accountId", accountId);
        return preferencesQuery.getResultStream()
            .map(PreferencesMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public CustomerPreference getPreference(String accountId, String key) {
        TypedQuery<CustomerPreferencesEntity> preferencesQuery = em.createNamedQuery(CustomerPreferencesEntity.FIND_BY_ACCOUNT_ID, CustomerPreferencesEntity.class);
        preferencesQuery.setParameter("accountId", accountId);
        preferencesQuery.setParameter("key", key);
        try {
            CustomerPreferencesEntity entity = preferencesQuery.getSingleResult();
            return PreferencesMapper.fromEntity(entity);
        } catch (NoResultException e) {
            throw new NotFoundException(CustomerPreferencesEntity.class, accountId + "#" + key);
        } catch (NonUniqueResultException e) {
            throw new RestException("Returned multiple results for unique pair!");
        }
    }
    
    @Override
    public CustomerPreference setPreference(CustomerPreference preference) {
        return null;
    }
}
