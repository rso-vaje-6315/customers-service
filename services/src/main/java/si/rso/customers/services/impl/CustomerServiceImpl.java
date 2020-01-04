package si.rso.customers.services.impl;

import com.mjamsek.auth.keycloak.exceptions.KeycloakException;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.rso.customers.config.ServiceConfig;
import si.rso.customers.integrations.keycloak.KeycloakAccountRegistration;
import si.rso.customers.integrations.keycloak.KeycloakService;
import si.rso.customers.lib.*;
import si.rso.customers.lib.config.AuthRole;
import si.rso.customers.mappers.AddressMapper;
import si.rso.customers.mappers.PreferencesMapper;
import si.rso.customers.persistence.AddressEntity;
import si.rso.customers.persistence.CustomerPreferencesEntity;
import si.rso.customers.services.CustomerService;
import si.rso.rest.exceptions.NotFoundException;
import si.rso.rest.exceptions.RestException;
import si.rso.rest.exceptions.ValidationException;
import si.rso.rest.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private KeycloakService keycloakService;
    
    @Inject
    private Validator validator;
    
    @Inject
    private ServiceConfig serviceConfig;
    
    @Timeout
    @CircuitBreaker
    @Fallback(fallbackMethod = "queryAccountsFallback")
    @Override
    public List<Account> queryAccounts(String query) {
        return keycloakService.getAccounts(query, 0, 25);
    }
    
    private List<Account> queryAccountsFallback(String query) {
        return new ArrayList<>();
    }
    
    @CircuitBreaker
    @Timeout(value = 3000)
    @Override
    public Account getAccount(String accountId) {
        return keycloakService.getAccount(accountId);
    }
    
    @CircuitBreaker
    @Timeout
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
    
    @Transactional
    @Override
    public CustomerAddress createAddress(String accountId, CustomerAddress address) {
        AddressEntity entity = AddressMapper.toEntity(address);
        entity.setAccountId(accountId);
        
        em.persist(entity);
        
        return AddressMapper.fromEntity(entity);
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
    public CustomerPreference setPreference(String accountId, CustomerPreference preference) {
        return null;
    }
    
    @CircuitBreaker
    @Timeout
    @Override
    @Transactional
    public void registerUser(AccountRegistration account) {
        
        if (!serviceConfig.isRegistrationEnabled()) {
            throw new RestException("Registration temporary disabled!");
        }
        
        validator.assertNotBlank(account.getPassword());
        validator.assertNotBlank(account.getPasswordConfirmation());
        
        if (!account.getPassword().equals(account.getPasswordConfirmation())) {
            throw new ValidationException("fields.mismatch.password")
                .isValidationError()
                .withDescription("Password mismatch!");
        }
        
        validator.assertNotBlank(account.getUsername());
        validator.assertNotBlank(account.getEmail());
        validator.assertNotBlank(account.getFirstName());
        validator.assertNotBlank(account.getLastName());
        
        KeycloakAccountRegistration keycloakAccount = new KeycloakAccountRegistration();
        keycloakAccount.setEmail(account.getEmail());
        keycloakAccount.setFirstName(account.getFirstName());
        keycloakAccount.setLastName(account.getLastName());
        keycloakAccount.setUsername(account.getUsername());
        
        keycloakAccount.setRealmRoles(new ArrayList<>());
        keycloakAccount.getRealmRoles().add(AuthRole.CUSTOMER);
        
        var credential = new KeycloakAccountRegistration.Credentials();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(account.getPassword());
        
        keycloakAccount.setCredentials(new ArrayList<>());
        keycloakAccount.getCredentials().add(credential);
        
        try {
            String accountId = keycloakService.registerAccount(keycloakAccount);
    
            CustomerPreferencesEntity customerPreference = new CustomerPreferencesEntity();
            customerPreference.setAccountId(accountId);
            customerPreference.setKey("lang");
            customerPreference.setValue("sl");
            em.persist(customerPreference);
            em.flush();
        } catch (KeycloakException e) {
            throw new RestException("Error communicating with Keycloak!", 503);
        }
    }
}
