package devcourse.jpa.domain.orderitem;

import devcourse.jpa.domain.item.Item;
import devcourse.jpa.domain.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private int orderQuantity;

    private int orderPrice;

    public OrderItem() {
    }

    public OrderItem(Item item) {
        this.item = item;
    }

    public void enrollOrder(Order order) {
        this.order = order;
        order.addOrderItem(this);
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }
}
