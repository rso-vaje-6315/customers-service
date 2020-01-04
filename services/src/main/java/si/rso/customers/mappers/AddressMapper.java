package si.rso.customers.mappers;

import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.persistence.AddressEntity;

public class AddressMapper {
    
    public static CustomerAddress fromEntity(AddressEntity entity) {
        CustomerAddress address = new CustomerAddress();
        address.setId(entity.getId());
        address.setTimestamp(entity.getTimestamp());
        
        address.setFirstName(entity.getFirstName());
        address.setLastName(entity.getLastName());
        address.setAccountId(entity.getAccountId());
        address.setStreet(entity.getStreet());
        address.setStreetNumber(entity.getStreetNumber());
        address.setPost(entity.getPost());
        address.setPostalCode(entity.getPostalCode());
        address.setPhoneNumber(entity.getPhoneNumber());
        address.setCountry(entity.getCountry());
        address.setEmail(entity.getEmail());
        
        return address;
    }
    
    public static AddressEntity toEntity(CustomerAddress address) {
        AddressEntity entity = new AddressEntity();
        
        entity.setFirstName(address.getFirstName());
        entity.setLastName(address.getLastName());
        entity.setEmail(address.getEmail());
        entity.setPhoneNumber(address.getPhoneNumber());
        entity.setPost(address.getPost());
        entity.setPostalCode(address.getPostalCode());
        entity.setCountry(address.getCountry());
        entity.setStreet(address.getStreet());
        entity.setStreetNumber(address.getStreetNumber());
        
        return entity;
    }
    
}
