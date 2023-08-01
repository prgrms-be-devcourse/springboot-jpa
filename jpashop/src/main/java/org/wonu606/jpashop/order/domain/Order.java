package org.wonu606.jpashop.order.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, LocalDateTime orderDateTime, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.orderLineItems.addAll(orderLineItems);
    }

    public Order(LocalDateTime orderDateTime, List<OrderLineItem> orderLineItems) {
        this(null, orderDateTime, orderLineItems);
    }

    public Long getId() {
        return id;
    }

    public void addOrderLineItem(OrderLineItem orderLineItem) {
        orderLineItems.add(orderLineItem);
        orderLineItem.changeOrder(this);
    }

    public void removeOrderLineItem(OrderLineItem orderLineItem) {
        orderLineItems.remove(orderLineItem);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDateTime=" + orderDateTime +
                ", orderLineItems=" + orderLineItems +
                '}';
    }
}
