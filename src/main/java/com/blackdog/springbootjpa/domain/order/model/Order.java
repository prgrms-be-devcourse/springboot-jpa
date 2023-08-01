package com.blackdog.springbootjpa.domain.order.model;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    protected Order(
            OrderStatus orderStatus,
            Customer customer
    ) {
        validate(orderStatus, customer);
        this.orderStatus = orderStatus;
        this.customer = customer;
    }

    private void validate(OrderStatus orderStatus, Customer customer) {
        Assert.notNull(orderStatus, "OrderStatus 가 존재하지 않습니다.");
        Assert.notNull(customer, "Customer 가 존재하지 않습니다.");
    }

    public void addOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
        }
    }

}
