package com.example.demo.domain;

import com.example.demo.converter.OrderStatusConverter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
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

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)){
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}
