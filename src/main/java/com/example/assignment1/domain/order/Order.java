package com.example.assignment1.domain.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @Column(name = "memo")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Member member, String memo) {
        this.member = member;
        this.memo = memo;
        this.orderStatus = OrderStatus.ACCEPTED;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public void updateMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this); // 원래 주인의 주문 제거
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (orderItems.contains(orderItem)) {
            return;
        }
        orderItems.remove(orderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}