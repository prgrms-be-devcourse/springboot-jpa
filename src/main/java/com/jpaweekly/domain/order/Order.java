package com.jpaweekly.domain.order;

import com.jpaweekly.common.BaseEntity;
import com.jpaweekly.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Order(String address, OrderStatus orderStatus, User user) {
        Assert.notNull(user, "User must be provided");
        Assert.notNull(address, "Address must be provided");
        this.address = address;
        this.orderStatus = orderStatus;
        this.user = user;
    }

    public void changeOrderStatusToDelivering() {
        if (this.orderStatus == OrderStatus.READY_FOR_DELIVERY) {
            this.orderStatus = OrderStatus.DELIVERING;
        } else throw new RuntimeException("message");
    }

    public void changeOrderStatusToComplete() {
        if (this.orderStatus == OrderStatus.DELIVERING) {
            this.orderStatus = OrderStatus.COMPLETED;
        } else throw new RuntimeException("message");
    }

    public void cancelOrder() {
        if (this.orderStatus != OrderStatus.COMPLETED) {
            this.orderStatus = OrderStatus.CANCELLED;
        } else throw new RuntimeException("message");
    }

}
