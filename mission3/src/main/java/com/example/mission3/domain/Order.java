package com.example.mission3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(String uuid, String email, String address, String createdBy ) {
        this.uuid = uuid;
        this.orderStatus = OrderStatus.OPENED;
        this.email = email;
        this.address = address;
        super.setCratedAt(LocalDateTime.now());
        super.setCreatedBy(createdBy);
    }

    public void addOrderItem(OrderItem orderItems) {
        orderItems.setOrder(this);
    }

    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}
