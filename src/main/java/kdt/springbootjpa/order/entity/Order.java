package kdt.springbootjpa.order.entity;

import kdt.springbootjpa.customer.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    private String uuid;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Builder
    public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo, Member member) {
        this.uuid = uuid;
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
        this.orderItems = new ArrayList<>();
    }

    public void setMember(Member member) { //연관관계 편의 메소드, member와 order를 연결
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
