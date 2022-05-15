package com.blessing333.kdtjpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PRIVATE)
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    private int price;
    private int quantity;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @MapsId("itemId")
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public static OrderItem createNewOrderItem(OrderItemId id, Order order, Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(order);
        orderItem.setItem(item);
        return orderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItem orderItem = (OrderItem) o;
        return id != null && Objects.equals(id, orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
