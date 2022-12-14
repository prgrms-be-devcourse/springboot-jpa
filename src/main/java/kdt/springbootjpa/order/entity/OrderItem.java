package kdt.springbootjpa.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "uuid")
    private Order order;

    @Builder
    public OrderItem(Long id, int price, int quantity, Item item, Order order) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.item = item;
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }
}
