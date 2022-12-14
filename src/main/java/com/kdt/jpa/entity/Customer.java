package com.kdt.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 10, nullable = false)
    private String firstName;

    @Column(length = 10, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Orders> orderList = new ArrayList<>();


    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addOrder(Orders order) {
        order.setCustomer(this);
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
}
