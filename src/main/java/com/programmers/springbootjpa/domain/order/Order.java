package com.programmers.springbootjpa.domain.order;

import com.programmers.springbootjpa.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "memo")
    @Lob
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {}

    public Order(String uuid, String memo, OrderStatus orderStatus) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
    }

    public void setMember(Member member) {
        // 기존에 member - order 관계가 이미 있다면 관계 끊기
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    /* getter */
    public String getUuid() {
        return uuid;
    }

    public String getMemo() {
        return memo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Member getMember() {
        return member;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
