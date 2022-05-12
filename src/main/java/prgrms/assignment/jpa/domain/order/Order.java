package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prgrms.assignment.jpa.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;
import static prgrms.assignment.jpa.domain.order.OrderStatus.ACCEPTED;


@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String memo;

    @Enumerated(value = STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(String memo) {
        this.memo = memo;
        this.orderStatus = ACCEPTED;
        this.orderDatetime = now();
    }

    public static Order createOrder(String memo, Member member, OrderItem... orderItems) {
        var order = new Order(memo);
        order.setMember(member);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItems(orderItem);
        }

        return order;
    }

    private void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem) {
        if (this.orderItems.contains(orderItem)) {
            orderItems.remove(orderItem);
        }

        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
    }
}
