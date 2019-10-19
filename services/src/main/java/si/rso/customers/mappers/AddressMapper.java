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
        
        return address;
    }
    
}
