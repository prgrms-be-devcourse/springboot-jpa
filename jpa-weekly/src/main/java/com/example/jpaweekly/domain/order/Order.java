package com.example.jpaweekly.domain.order;

import com.example.jpaweekly.domain.BaseEntity;
import com.example.jpaweekly.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String address, OrderStatus orderStatus, User user) {
        this.address = address;
        this.orderStatus = orderStatus;
        this.user = user;
    }
}
