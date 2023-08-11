package com.example.springjpamission.customer.domain;

import com.example.springjpamission.gobal.BaseEntity;
import com.example.springjpamission.order.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Table(name = "customers")
@Entity
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @Embedded
    @Column(nullable = false)
    private Name name;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    protected Customer() { }

    public Customer(Name name) {
        this.name = name;
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

}
