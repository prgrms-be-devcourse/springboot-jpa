package org.prgms.springbootjpa.customer.domain;

import org.apache.ibatis.type.Alias;

@Alias("customers")
public class Customer {
    private final long id;
    private String firstName;
    private final String lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
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
