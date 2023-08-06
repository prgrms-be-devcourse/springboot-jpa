package prgms.jpamission2.config.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order(String uuid, OrderStatus orderStatus, String memo, Member member) {
        this.uuid = uuid;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
        this.orderDatetime = LocalDateTime.now();
    }

    protected Order() {

    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getOrderDatetime() {
        return orderDatetime;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }
}
