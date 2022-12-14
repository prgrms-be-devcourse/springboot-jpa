package com.kdt.jpa.entity;

import com.kdt.jpa.type.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 30, nullable = false)
    private String address;

    @ManyToOne(fetch = LAZY)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Orders(String address, OrderStatus orderStatus) {
        this.address = address;
        this.orderStatus = orderStatus;
    }

    public void setCustomer(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            this.customer.getOrderList().remove(this);
        }

        this.customer = customer;
        customer.getOrderList().add(this);
    }
}
