package com.programmers.springbootjpa.domain.order;

import com.programmers.springbootjpa.domain.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Builder
    public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo) {
        this.uuid = uuid;
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
    }

    public void setCustomer(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            this.customer.getOrders().remove(this);
        }

        this.customer = customer;
        customer.getOrders().add(this);
    }
}
