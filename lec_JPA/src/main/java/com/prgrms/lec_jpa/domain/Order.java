package com.prgrms.lec_jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id
    @Column(name = "id")
    private String uuid;

    @Lob
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "created_order_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOrderAt;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    private Order(OrderBuilder builder) {

        super(LocalDateTime.now(), builder.createdBy);
        this.uuid = UUID.randomUUID().toString();
        this.memo = builder.memo;
        this.orderStatus = OrderStatus.OPENED;
        this.createdOrderAt = LocalDateTime.now();
    }

    public void setMember(Member member) {

        this.member = member;
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

    public LocalDateTime getCreatedOrderAt() {

        return createdOrderAt;
    }

    public Member getMember() {

        return member;
    }

    public static class OrderBuilder {

        private String memo;

        private String createdBy;

        public OrderBuilder memo(String value) {

            this.memo = value;

            return this;
        }

        public OrderBuilder createdBy(String value) {

            this.createdBy = value;

            return this;
        }

        public Order build() {

            return new Order(this);
        }
    }

    public static OrderBuilder builder() {

        return new OrderBuilder();
    }
}
