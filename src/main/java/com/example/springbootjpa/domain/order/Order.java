package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.customer.Customer;
import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_datetime", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order createOrder(Customer customer, List<OrderItem> orderItems) {
        validateOrderItem(orderItems);

        Order order = new Order();
        order.updateMember(customer);
        orderItems.forEach(order::addOrderItem);
        order.updateOrderStatus(OrderStatus.ORDER);
        order.updateOrderDate(LocalDateTime.now());

        orderItems.forEach(orderItem
                -> orderItem.getItem().removeStock(orderItem.getQuantity()));

        return order;
    }

    private static void validateOrderItem(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ORDER_ITEMS);
        }
    }

    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void updateMember(Customer customer) {
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.addToOrder(this);
    }

    public int getTotalPrice() {
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        return totalPrice;
    }
}
