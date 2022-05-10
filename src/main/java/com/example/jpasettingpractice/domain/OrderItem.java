package com.example.jpasettingpractice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "order_id", insertable = false, updatable = false)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items = new ArrayList<>();

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return price == orderItem.price && quantity == orderItem.quantity
                && id.equals(orderItem.id) && orderId.equals(orderItem.orderId)
                && order.equals(orderItem.order) && items.equals(orderItem.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, orderId, order, items);
    }
}
