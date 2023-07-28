package com.example.kdt.spring.jpa.domain.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "item")
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Long id;

    @Min(value = 1, message = "가격은 최소 1원입니다.")
    private int price;

    @Min(value = 1, message = "재고는 최소 1개입니다.")
    private int stockQuantity;

    @OneToMany
    private List<OrderItem> orderItems;

    protected Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void incrementStockQuantity(int quantity) {
        stockQuantity += quantity;
    }

    public void decrementStockQuantity(int quantity) {
        if(stockQuantity < quantity) {
            throw new RuntimeException("재고보다 초과되는 주문량입니다.");
        }
        stockQuantity -= quantity;
    }
}
