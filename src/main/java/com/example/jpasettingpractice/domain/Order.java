package com.example.jpasettingpractice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    private String uuid;

    @Column(name = "memo", nullable = true)
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private Orderstatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP",
            nullable = false)
    private LocalDateTime orderDatetime;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }
}
