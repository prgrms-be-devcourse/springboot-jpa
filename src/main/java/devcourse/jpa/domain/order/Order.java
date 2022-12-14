package devcourse.jpa.domain.order;

import devcourse.jpa.domain.member.Member;
import devcourse.jpa.domain.orderitem.OrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDateTime;

    @Lob
    private String memo;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(Member member, OrderStatus orderStatus, LocalDateTime orderDateTime) {
        this.member = member;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public String getMemo() {
        return memo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
