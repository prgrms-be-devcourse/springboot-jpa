package kdt.springbootjpa.order.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int stockQuantity;

    @Builder
    public Item(Long id, int price, int stockQuantity) {
        this.id = id;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
