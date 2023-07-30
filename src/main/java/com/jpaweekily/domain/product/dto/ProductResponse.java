package com.jpaweekily.domain.product.dto;

import com.jpaweekily.domain.product.Product;

public record ProductResponse(
        Long id,
        String ProductName,
        int price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(),
                product.getProductName(),
                product.getPrice());
    }
}
