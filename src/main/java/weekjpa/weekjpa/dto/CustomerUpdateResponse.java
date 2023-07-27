package weekjpa.weekjpa.dto;

import weekjpa.weekjpa.domain.Customer;

public record CustomerUpdateResponse(
        String firstName, String lastName) {
    public static CustomerUpdateResponse from(Customer customer) {
        return new CustomerUpdateResponse(customer.getFirstName(), customer.getLastName());
    }
}
