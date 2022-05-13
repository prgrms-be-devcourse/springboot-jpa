package org.programmers.kdt.studyjpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }
}
