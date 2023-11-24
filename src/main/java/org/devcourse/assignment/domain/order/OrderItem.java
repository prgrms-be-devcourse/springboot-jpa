package org.devcourse.assignment.domain.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "quantity", nullable = false)
    @Positive
    @NotNull
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Builder
    public OrderItem(Integer quantity, Order order, Item item) {
        this.quantity = quantity;
        this.order = order;
        this.item = item;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getPrice() {
        return item.getPrice() * quantity;
    }
}
