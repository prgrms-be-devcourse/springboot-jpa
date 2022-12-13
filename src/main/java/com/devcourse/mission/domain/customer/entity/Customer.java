package com.devcourse.mission.domain.customer.entity;

import com.devcourse.mission.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "customers")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id @GeneratedValue
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private int age;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer(Long id, String name, String address, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    @Builder
    public Customer(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public void order(Order order) {
        this.orders.add(order);
        order.pointCustomer(this);
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
