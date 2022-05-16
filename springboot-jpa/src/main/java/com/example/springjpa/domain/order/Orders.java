package com.example.springjpa.domain.order;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Orders {
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public Orders() {
        orders = new ArrayList<>();
    }

    public void remove(Order order) {
        orders.remove(order);
    }

    public void add(Order order) {
        orders.add(order);
    }

    public int length() {
        return orders.size();
    }

    public Order get(int i) {
        return orders.get(i);
    }
}
