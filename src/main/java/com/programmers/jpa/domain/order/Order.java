package com.programmers.jpa.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @CreatedDate
    @Column(name = "order_datetime")
    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "memo", columnDefinition = "text")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(
            OrderStatus orderStatus,
            String memo,
            Member member,
            List<OrderItem> orderItems
    ) {
        this.uuid = UUID.randomUUID().toString();
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
        this.orderItems = orderItems;
    }

    public static Order createOrder(
            OrderStatus orderStatus,
            String memo,
            Member member,
            List<OrderItem> orderItems
    ) {
        return new Order(orderStatus, memo, member, orderItems);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.attachToOrder(this);
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }
}
