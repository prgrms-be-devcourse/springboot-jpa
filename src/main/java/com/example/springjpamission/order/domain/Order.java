package com.example.springjpamission.order.domain;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.gobal.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memo;

    @Embedded
    @Column(nullable = false)
    private Price price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() { }

    public Order(String memo) {
        this.memo = memo;
    }

    public Order(String memo, Price price, Customer customer) {
        this.memo = memo;
        this.price = price;
        this.customer = customer;
    }

    public void changeCustomer(Customer customer) {
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
