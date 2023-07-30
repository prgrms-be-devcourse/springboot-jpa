package weekjpa.weekjpa.dto;

import weekjpa.weekjpa.domain.Customer;

public record CustomerResponse(
        String firstName, String lastName
) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getFirstName(), customer.getLastName());
    }
}
