package si.rso.customers.persistence;

import javax.persistence.*;

@Entity
@Table(name = "addresses", indexes = {
    @Index(name = "ADDR_ACCOUNT_ID_INDEX", columnList = "account_id")
})
@NamedQueries({
    @NamedQuery(name = AddressEntity.FIND_BY_ACCOUNT_ID,
        query = "SELECT a FROM AddressEntity a WHERE a.accountId = :accountId")
})
public class AddressEntity extends BaseEntity {
    
    public static final String FIND_BY_ACCOUNT_ID = "Addresses.findByAccountId";
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column
    private String street;
    
    @Column(name = "street_number")
    private String streetNumber;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Column
    private String post;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getStreetNumber() {
        return streetNumber;
    }
    
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getPost() {
        return post;
    }
    
    public void setPost(String post) {
        this.post = post;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
