package com.kdt.lecture.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Lob
    private String memo;

    // member_fk
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    public Order(){}

    public Order(String uuid, OrderStatus orderStatus, LocalDateTime orderDateTime, String memo, Member member) {
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.memo = memo;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.addOrder(this);
    }

    public void addOrderItem(OrderItem item) {
        items.add(item);
    }
}
