package com.kdt.lecture.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "memo", nullable = true)
    @Setter
    private String memo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_date_time", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime orderDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Builder
    public Order(OrderStatus orderStatus, LocalDateTime orderDateTime, Customer customer) {
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
    }

}
