package si.rso.customers.persistence;

import javax.persistence.*;

@Entity
@Table(name = "customer_preferences", indexes = {
    @Index(name = "CUST_ACCOUNT_ID_INDEX", columnList = "account_id"),
    @Index(name = "ACCOUNT_KEY_UNIQ_INDEX", columnList = "account_id,preference_key", unique = true)
})
@NamedQueries({
    @NamedQuery(name = CustomerPreferencesEntity.FIND_BY_ACCOUNT_ID,
        query = "SELECT p FROM CustomerPreferencesEntity p WHERE p.accountId = :accountId"),
    @NamedQuery(name = CustomerPreferencesEntity.FIND_BY_KEY,
        query = "SELECT p FROM CustomerPreferencesEntity p WHERE p.key = :key"),
    @NamedQuery(name = CustomerPreferencesEntity.FIND_BY_ACCOUNT_AND_KEY,
        query = "SELECT p FROM CustomerPreferencesEntity p WHERE p.key = :key AND p.accountId = :accountId")
})
public class CustomerPreferencesEntity extends BaseEntity {
    
    public static final String FIND_BY_ACCOUNT_ID = "CustomerPreferences.findByAccountId";
    public static final String FIND_BY_KEY = "CustomerPreferences.findByKey";
    public static final String FIND_BY_ACCOUNT_AND_KEY = "CustomerPreferences.findByAccountAndKey";
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "preference_key")
    private String key;
    
    @Column(name = "preference_value")
    private String value;
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
