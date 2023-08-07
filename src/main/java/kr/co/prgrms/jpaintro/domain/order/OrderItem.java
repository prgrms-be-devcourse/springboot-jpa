package kr.co.prgrms.jpaintro.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.prgrms.jpaintro.domain.item.Item;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "order_item_quantity")
    private Integer quantity;

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.item = item;
    }

    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.addOrderItem(this);
    }

    public void changeItem(Item item) {
        this.item = item;
    }

    public void changeOrderItemQuantity(int quantity) {
        checkQuantity(quantity);
        this.quantity = quantity;
    }

    private void checkQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalValueException("[ERROR] 변경하려는 수량이 올바르지 않습니다.");
        }
    }
}
