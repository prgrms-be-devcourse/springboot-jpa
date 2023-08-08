package com.jpaweekly.domain.product.dto;

import com.jpaweekly.domain.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductCreateRequest(
        @NotBlank
        String productName,
        @Min(0)
        int price
) {
    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .price(this.price)
                .build();
    }
}
