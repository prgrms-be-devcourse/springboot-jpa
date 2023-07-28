package com.kdt.module.order.domain;

import com.kdt.module.customer.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime orderTime;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Builder
    public Order(OrderStatus status, LocalDateTime orderTime, String memo) {
        this.status = status;
        this.orderTime = orderTime;
        this.memo = memo;
    }

    public void setCustomer(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            this.customer.removeOrder(this);
        }

        this.customer = customer;
        customer.getOrders().add(this);
    }
}
