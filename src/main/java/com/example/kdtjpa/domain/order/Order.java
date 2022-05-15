package com.example.kdtjpa.domain.order;

import com.example.kdtjpa.domain.BaseEntity;
import com.example.kdtjpa.domain.member.Member;
import com.example.kdtjpa.domain.orderItem.OrderItem;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static com.example.kdtjpa.domain.order.OrderStatus.OPENED;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "memo")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Order(String uuid, String memo, Member member, List<OrderItem> orderItems) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = OPENED;
        this.orderDatetime = LocalDateTime.now();
        this.member = member;
        for (OrderItem orderItem : orderItems) {
            addOrderItem(orderItem);
        }
    }

    protected Order() {

    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /** 연관관계 매핑 **/
    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}



