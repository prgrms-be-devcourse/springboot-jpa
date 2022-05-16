package devcoursejpa.jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {

    }

    public Order(String uuid, LocalDateTime orderDateTime, OrderStatus orderStatus, String memo) {
        this.uuid = uuid;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.memo = memo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getMemo() {
        return memo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().remove(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}