package org.devcourse.springbootjpa.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.devcourse.springbootjpa.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(order)) {
            order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }
}
