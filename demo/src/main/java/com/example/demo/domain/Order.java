package com.example.demo.domain;

import com.example.demo.converter.OrderStatusConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Column(name = "order_status")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(String memo, Member member) {
        this.uuid = UUID.randomUUID().toString();
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = OrderStatus.OPENED;
        this.memo = memo;
        this.member = member;
        member.getOrders().add(this);

        this.orderItems = new ArrayList<>();
    }

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)){
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}
