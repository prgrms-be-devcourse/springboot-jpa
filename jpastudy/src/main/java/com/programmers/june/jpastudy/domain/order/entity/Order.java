package com.programmers.june.jpastudy.domain.order.entity;

import com.programmers.june.jpastudy.domain.customer.entity.Customer;
import com.programmers.june.jpastudy.domain.order_item.model.OrderStatus;
import com.programmers.june.jpastudy.domain.order_item.entity.OrderItem;
import com.programmers.june.jpastudy.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 주문 생성
    public static Order createOrder(Customer customer, OrderItem... orderItems) {
        Order order = new Order();

        order.setCustomer(customer);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.OPENED);

        return order;
    }

    // 주문 취소
    public void cancel(Order order) {
        this.setOrderStatus(OrderStatus.CANCELLED);

        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancle();
        }
    }
}
