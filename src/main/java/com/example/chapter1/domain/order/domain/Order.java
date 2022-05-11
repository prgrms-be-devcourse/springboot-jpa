package com.example.chapter1.domain.order.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseIdEntity {

    @Column(name = "memo")
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;

    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(String memo) {
        this.memo = memo;
        this.orderStatus = OrderStatus.OPENED;
        this.orderDatetime = LocalDateTime.now();
    }

    public static Order create(String memo) {
        return new Order(memo);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItem.changeOrder(this);
        orderItems.add(orderItem);
    }
}
