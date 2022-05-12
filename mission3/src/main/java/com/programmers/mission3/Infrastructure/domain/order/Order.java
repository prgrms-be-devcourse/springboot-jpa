package com.programmers.mission3.Infrastructure.domain.order;

import com.programmers.mission3.Infrastructure.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);
    }

    public void setMember(Member member){
        if(Objects.nonNull(this.member)){
            member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    @Builder
    public Order(String uuid, OrderStatus orderStatus, String memo, Member member, List<OrderItem> orderItems) {
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
        this.orderItems = orderItems;
    }

    public void updateOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public void updateMemo(String memo){
        this.memo = memo;
    }
}
