package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
    }

    public Order(String uuid, OrderStatus orderStatus, LocalDateTime createdAt) {
        validateOrderField(uuid, orderStatus, createdAt);
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Order(String uuid, OrderStatus orderStatus, String memo, LocalDateTime createdAt) {
        validateOrderField(uuid, orderStatus, createdAt);
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.createdAt = createdAt;
    }

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    private void validateOrderField(String uuid, OrderStatus orderStatus, LocalDateTime createdAt) {
        if(uuid == null) {
            throw new WrongFieldException("Order.uuid", uuid, "uuid is required field");
        }

        if(orderStatus == null) {
            throw new WrongFieldException("Order.orderStatus", orderStatus, "orderStatus is required field");
        }

        if(createdAt == null) {
            throw new WrongFieldException("Order.createdAt", orderStatus, "createdAt is required field");
        }
    }
}
