package com.kdt.lecture.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Lob
    private String memo;

    // member_fk
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    public Order(){}

    public Order(String uuid, OrderStatus orderStatus, LocalDateTime orderDateTime, String memo, Member member) {
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.memo = memo;
        setMember(member);
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem item) {
        item.setOrder(this);
    }
}
