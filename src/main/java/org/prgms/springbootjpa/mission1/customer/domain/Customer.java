package org.prgms.springbootjpa.mission1.customer.domain;

import org.apache.ibatis.type.Alias;

@Alias("customers")
public class Customer {
    private final Long id;
    private String firstName;
    private final String lastName;

    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeFirstName(String changeName) {
        firstName = changeName;
    }
}
