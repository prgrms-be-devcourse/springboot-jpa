package com.kdt.kdtjpa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    private Long id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "first_name"))
    private Name firstName;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "last_name"))
    private Name lastName;

    @Builder(builderMethodName = "createCustomer")
    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return firstName.getName() + " " + lastName.getName();
    }

    public void changeFirstName(String firstName) {
        this.firstName = new Name(firstName);
    }

    public void changeLastName(String lastName) {
        this.lastName = new Name(lastName);
    }
}
