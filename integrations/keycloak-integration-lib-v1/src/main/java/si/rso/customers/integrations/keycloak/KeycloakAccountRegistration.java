package si.rso.customers.integrations.keycloak;

import java.util.List;

public class KeycloakAccountRegistration {
    
    private String email;
    
    private String username;
    
    private String firstName;
    
    private String lastName;
    
    private List<String> realmRoles;
    
    private List<Credentials> credentials;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public List<String> getRealmRoles() {
        return realmRoles;
    }
    
    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }
    
    public List<Credentials> getCredentials() {
        return credentials;
    }
    
    public void setCredentials(List<Credentials> credentials) {
        this.credentials = credentials;
    }
    
    public static class Credentials {
        private String value;
    
        /**
         * <tt>password</tt>
         */
        private String type;
    
        /**
         * <tt>false</tt>
         */
        private Boolean temporary;
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public Boolean getTemporary() {
            return temporary;
        }
        
        public void setTemporary(Boolean temporary) {
            this.temporary = temporary;
        }
    }
    
}
