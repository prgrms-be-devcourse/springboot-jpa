package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "order_quantity")
    private long orderQuantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private OrderItem(int orderPrice, long orderQuantity) {
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }

    public static OrderItem createOrderItem(int orderPrice, long orderQuantity, Item item) {
        var orderItem = new OrderItem(orderPrice, orderQuantity);
        orderItem.setItem(item);

        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItems().add(this);
    }
}
