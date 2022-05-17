package com.prgms.springbootjpa.domain.order.vo;

import com.prgms.springbootjpa.domain.order.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Orders {

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void remove(Order order) {
        orders.remove(order);
    }

    public void add(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders.stream()
            .toList();
    }
}
