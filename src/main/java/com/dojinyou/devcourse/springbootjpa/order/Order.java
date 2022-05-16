package com.dojinyou.devcourse.springbootjpa.order;

import com.dojinyou.devcourse.springbootjpa.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    protected Order() {
    }

    public Long getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    Order(Builder builder) {
        this.id = builder.id;
        this.memo = builder.memo;
        this.orderStatus = OrderStatus.OPENED;
        this.orderDateTime = builder.orderDateTime;
        this.orderItems = builder.orderItems;
    }

    public static class Builder {
        private Long id;
        private String memo;
        private LocalDateTime orderDateTime;
        private List<OrderItem> orderItems;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public Builder orderDateTime(LocalDateTime orderDateTime) {
            this.orderDateTime = orderDateTime;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            if (orderDateTime == null || orderItems == null) {
                throw new IllegalArgumentException();
            }
            return new Order(this);
        }
    }
}
