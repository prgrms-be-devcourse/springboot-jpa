package com.example.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Long id, LocalDateTime orderDateTime, OrderStatus orderStatus) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
    }

}
