package weekjpa.weekjpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weekjpa.weekjpa.domain.Customer;
import weekjpa.weekjpa.dto.CustomerCreateRequest;
import weekjpa.weekjpa.dto.CustomerGetResponse;
import weekjpa.weekjpa.dto.CustomerUpdateRequest;
import weekjpa.weekjpa.dto.CustomerUpdateResponse;
import weekjpa.weekjpa.exception.CustomerNotFoundException;
import weekjpa.weekjpa.repository.CustomerRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long create(CustomerCreateRequest createRequest) {
        Customer customer = Customer.builder()
                .lastName(createRequest.lastName())
                .firstName(createRequest.firstName())
                .build();
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }

    public CustomerGetResponse find(Long id) {
        Customer customer = getCustomerObject(id);

        return CustomerGetResponse.from(customer);
    }

    @Transactional
    public CustomerUpdateResponse update(Long id, CustomerUpdateRequest request) {
        Customer customer = getCustomerObject(id);
        customer.update(request.firstName(), request.lastName());
        return CustomerUpdateResponse.from(customer);
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    private Customer getCustomerObject(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());
        return customer;
    }
}
