package programmers.jpaWeekly.order.entity;

import jakarta.persistence.*;
import programmers.jpaWeekly.item.entity.Item;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {

    }

    public OrderItem(Item item, int quantity) {
        this.price = item.getPrice() * quantity;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItemList().remove(this);
        }

        this.order = order;
        order.getOrderItemList().add(this);
    }
}
