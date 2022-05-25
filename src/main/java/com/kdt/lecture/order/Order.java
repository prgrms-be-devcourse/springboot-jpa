package com.kdt.lecture.order;

import com.kdt.lecture.common.BaseEntity;
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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    private String id;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 30)
    private OrderStatus orderStatus;

    @Lob
    @Column(name = "memo")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(String id, LocalDateTime orderDateTime, OrderStatus orderStatus, String memo) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.memo = memo;
    }

    public Order(String id, LocalDateTime orderDateTime, OrderStatus orderStatus, String memo,
        Member member) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
    }

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

}
