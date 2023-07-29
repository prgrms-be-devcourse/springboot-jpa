package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.BaseTimeEntity;
import com.example.weeklyjpa.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.weeklyjpa.domain.order.OrderStatus.ACCEPTED;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseTimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "memo")
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Order createOrder(String memo,Member member, OrderItem... orderItems){
        Order order = new Order();
        order.setMemo(memo);
        order.changeMember(member);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(ACCEPTED);

        return order;
    }

    private void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    // 연관관계 편의 메서드
    public void changeMember(Member member){
        if(Objects.nonNull(this.member)){
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }
}
