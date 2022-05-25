package com.prgrms.springbootjpa.domain;

import com.prgrms.springbootjpa.global.util.EntityFieldValidator;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static com.prgrms.springbootjpa.global.util.EntityFieldValidator.*;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
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
}
