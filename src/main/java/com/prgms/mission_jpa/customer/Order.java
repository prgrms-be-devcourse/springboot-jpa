package com.prgms.mission_jpa.customer;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "memo")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Builder
    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Member member) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.member = member;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);
    }
}
