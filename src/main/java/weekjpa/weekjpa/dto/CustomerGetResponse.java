package weekjpa.weekjpa.dto;

import jakarta.validation.constraints.NotBlank;
import weekjpa.weekjpa.domain.Customer;

public record CustomerGetResponse(
        String firstName, String lastName) {

    public static CustomerGetResponse from(Customer customer) {
        return new CustomerGetResponse(customer.getFirstName(), customer.getLastName());
    }
}
