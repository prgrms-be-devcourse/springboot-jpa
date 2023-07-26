package com.kdt.weeklyjpa.domain.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long itemId;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    @NotBlank
    private String name;

    @Column(name = "stock_quantity", nullable = false)
    @PositiveOrZero
    private Integer stockQuantity;

    @Column(name = "price", nullable = false)
    @Positive
    private Integer price;
}
