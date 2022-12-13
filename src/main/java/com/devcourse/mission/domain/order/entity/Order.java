package com.devcourse.mission.domain.order.entity;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Table(name = "orders")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(Customer customer) {
        this.customer = customer;
    }

    public static Order createOrder(Customer customer) {
        return new Order(customer);
    }

    public void pointCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.enrollOrder(this);
        Optional<OrderItem> mayBeOrderItem = getMayBeOrderItem(orderItem);
        if (mayBeOrderItem.isPresent()) {
            mayBeOrderItem.get().combineFrom(orderItem);
            return;
        }
        this.orderItems.add(orderItem);
    }
    public void removeOrderItemByItemId(long itemId) {
        Optional<OrderItem> mayBeOrderItem = getMayBeOrderItem(itemId);
        mayBeOrderItem.ifPresent(orderItem -> {
            this.orderItems.remove(orderItem);
        });
    }

    private Optional<OrderItem> getMayBeOrderItem(OrderItem orderItem) {
        return this.orderItems
                .stream()
                .filter(orderItem::isSame)
                .findAny();
    }

    private Optional<OrderItem> getMayBeOrderItem(long itemId) {
        return this.orderItems
                .stream()
                .filter(orderItem -> orderItem.isSameItem(itemId))
                .findFirst();
    }
}
