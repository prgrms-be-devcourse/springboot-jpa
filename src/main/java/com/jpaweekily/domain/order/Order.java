package com.jpaweekily.domain.order;

import com.jpaweekily.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String address, OrderStatus orderStatus, LocalDateTime createAt, User user) {
        this.address = address;
        this.orderStatus = orderStatus;
        this.createAt = createAt;
        this.user = user;
    }
}
