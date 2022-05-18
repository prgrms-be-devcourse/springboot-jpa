package com.example.springjpa.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;
    @Column(name = "memo")
    private String memo;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    protected Order() {
    }

    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Member member, List<OrderItem> orderItems) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.member = member;
        this.orderItems = orderItems == null ? new ArrayList<>() : orderItems;
    }

    public Member getMember() {
        return member;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMemo() {
        return memo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void changeMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    public static class OrderBuilder {
        private String uuid;
        private String memo;
        private OrderStatus orderStatus;
        private LocalDateTime orderDateTime;
        private Member member;
        private List<OrderItem> orderItems;

        OrderBuilder() {
        }

        public OrderBuilder uuid(final String uuid) {
            this.uuid = uuid;
            return this;
        }

        public OrderBuilder memo(final String memo) {
            this.memo = memo;
            return this;
        }

        public OrderBuilder orderStatus(final OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderBuilder orderDateTime(final LocalDateTime orderDateTime) {
            this.orderDateTime = orderDateTime;
            return this;
        }

        public OrderBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public OrderBuilder orderItems(final List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(this.uuid, this.memo, this.orderStatus, this.orderDateTime, this.member, this.orderItems);
        }

        public String toString() {
            return "OrderBuilder(uuid=" + this.uuid + ", memo=" + this.memo + ", orderStatus=" + this.orderStatus + ", orderDateTime=" + this.orderDateTime + ", orderItems=" + this.orderItems + ")";
        }
    }
}
