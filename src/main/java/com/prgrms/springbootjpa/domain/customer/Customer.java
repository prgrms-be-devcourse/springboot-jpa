package com.prgrms.springbootjpa.domain.customer;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static com.prgrms.springbootjpa.global.util.Validator.validateFieldLength;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        validateCustomerField(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }

    private void validateCustomerField(String firstName, String lastName) {
        if(!StringUtils.hasText(firstName)) {
            throw new WrongFieldException("Customer.firstName", firstName, "please input firstName");
        }

        if(!validateFieldLength(firstName, 1, 30)) {
            throw new WrongFieldException("Customer.firstName", firstName, "1 <= firstName <= 30");
        }

        if(!StringUtils.hasText(lastName)) {
            throw new WrongFieldException("Customer.lastName", lastName, "please input lastName");
        }

        if(!validateFieldLength(lastName, 1, 30)) {
            throw new WrongFieldException("Customer.lastName", lastName, "1 <= lastName <= 30");
        }
    }
}
