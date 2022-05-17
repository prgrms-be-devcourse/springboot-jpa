package com.prgms.springbootjpa.domain.order;

import com.prgms.springbootjpa.domain.order.vo.OrderItems;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Embedded
    private OrderItems orderItems;

    protected Order() {
    }

    public Order(String memo) {
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = OrderStatus.OPENED;
        this.memo = memo;
        this.orderItems = new OrderItems();
    }

    /* 연관관계 편의 메서드 */
    public void assignMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.assignOrder(this);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDatetime() {
        return orderDatetime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getMemo() {
        return memo;
    }

    public Member getMember() {
        return member;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }
}
