package com.part4.jpa2.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private int price;

    private int quantity;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

}
