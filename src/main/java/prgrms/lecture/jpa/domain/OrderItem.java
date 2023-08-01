package prgrms.lecture.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    public OrderItem(long price, int quantity, Order order, Item item) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.item = item;
        if(Objects.nonNull(order) && Objects.nonNull(item)) {
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.add(this);
        }
    }
}

