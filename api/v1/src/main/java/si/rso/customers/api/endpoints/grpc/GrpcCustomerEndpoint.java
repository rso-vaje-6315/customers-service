package si.rso.customers.api.endpoints.grpc;

import com.kumuluz.ee.grpc.annotations.GrpcService;
import grpc.Customers;
import grpc.CustomersServiceGrpc;
import io.grpc.stub.StreamObserver;
import si.rso.customers.lib.Account;
import si.rso.customers.lib.CustomerAddress;
import si.rso.customers.services.CustomerService;

import javax.enterprise.inject.spi.CDI;

@GrpcService
public class GrpcCustomerEndpoint extends CustomersServiceGrpc.CustomersServiceImplBase {
    
    @Override
    public void getCustomer(Customers.CustomerRequest request, StreamObserver<Customers.CustomerResponse> responseObserver) {
        CustomerService customerService = CDI.current().select(CustomerService.class).get();
        
        try {
            
            Account account = customerService.getAccount(request.getCustomerId());
            CustomerAddress address = customerService.getAddress(request.getAddressId());
    
            var accountResponseBuilder = Customers.Account.newBuilder()
                .setId(account.getId())
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName());
    
            var addressResponseBuilder = Customers.CustomerAddress.newBuilder()
                .setId(address.getId())
                .setAccountId(address.getAccountId())
                .setEmail(address.getEmail())
                .setPhoneNumber(address.getPhoneNumber())
                .setFirstName(address.getFirstName())
                .setLastName(address.getLastName())
                .setStreet(address.getStreet())
                .setStreetNumber(address.getStreetNumber())
                .setPost(address.getPost())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry());
    
            var response = Customers.CustomerResponse.newBuilder()
                .setAccount(accountResponseBuilder)
                .setAddress(addressResponseBuilder)
                .build();
    
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
