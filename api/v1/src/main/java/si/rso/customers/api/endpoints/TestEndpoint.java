package si.rso.customers.api.endpoints;

import si.rso.customers.lib.Account;
import si.rso.customers.lib.CustomerDetails;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/customers-unsecure")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestEndpoint {
    
    @GET
    public Response getCustomers() {
        return Response.ok(getCustomersList()).build();
    }
    
    private List<CustomerDetails> getCustomersList() {
        List<CustomerDetails> list = new ArrayList<>();
        
        CustomerDetails customer1 = new CustomerDetails();
        Account account1 = new Account();
        account1.setId("1");
        account1.setUsername("username1");
        customer1.setAccount(account1);
    
        CustomerDetails customer2 = new CustomerDetails();
        Account account2 = new Account();
        account2.setId("2");
        account2.setUsername("username2");
        customer2.setAccount(account2);
        
        list.add(customer1);
        list.add(customer2);
        
        return list;
    }
    
}
