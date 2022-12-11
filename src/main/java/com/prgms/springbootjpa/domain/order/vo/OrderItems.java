package com.prgms.springbootjpa.domain.order.vo;

import com.prgms.springbootjpa.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class OrderItems {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void remove(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    public void add(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems.stream()
            .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItems that = (OrderItems) o;
        return getOrderItems().equals(that.getOrderItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderItems());
    }
}
