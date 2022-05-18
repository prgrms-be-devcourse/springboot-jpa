package com.programmers.jpa.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.programmers.jpa.domain.order.OrderStatus.OPENED;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String uuid;

    @Column(name = "memo")
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Order(String uuid, Member member, String memo, OrderStatus orderStatus, LocalDateTime orderDatetime) {
        this.uuid = uuid;
        this.member = member;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDatetime = orderDatetime;
    }

    public static Order createOrder(Member member, String memo, OrderItem... orderItems) {
        Order order = new Order(UUID.randomUUID().toString(), member, memo, OPENED, LocalDateTime.now());
        member.addOrder(order);
        Arrays.stream(orderItems)
                .forEach(oi -> oi.setOrder(order));
        return order;
    }

}
