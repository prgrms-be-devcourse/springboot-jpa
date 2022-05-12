package com.prgrms.springJpa.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;

    @Lob
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime")
    private LocalDateTime orderDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
    }

    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDateTime) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
    }

    public void changeMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.removeOrder(this);
        }
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.changeOrder(this);
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }
}
