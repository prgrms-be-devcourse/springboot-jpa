package com.kdt.JpaWeekly.domain.order;

import com.kdt.JpaWeekly.common.domain.BaseEntity;
import com.kdt.JpaWeekly.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column()
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
        super();
    }

    private Order(Builder builder) {
        super(builder.createdBy, builder.createdAt);
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDatetime = orderDatetime;
        this.member = member;
        this.orderItems = orderItems;
    }

    public static class Builder {
        private String memo;
        private OrderStatus orderStatus;
        private LocalDateTime orderDatetime;
        private Member member;
        private List<OrderItem> orderItems;
        private String createdBy;
        private LocalDateTime createdAt;

        public Builder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder orderDatetime(LocalDateTime orderDatetime) {
            this.orderDatetime = orderDatetime;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}