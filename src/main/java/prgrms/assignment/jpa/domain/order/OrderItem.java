package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "item_price")
    private int itemPrice;

    @Column(name = "order_quantity")
    private long orderQuantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int itemPrice, long orderQuantity) {
        this.itemPrice = itemPrice;
        this.orderQuantity = orderQuantity;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItems().add(this);
    }
}
