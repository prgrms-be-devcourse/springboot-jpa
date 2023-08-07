package com.jpaweekly.domain.order.dto;

import com.jpaweekly.domain.order.OrderProduct;

public record OrderProductResponse (
        String productName,
        int quantity,
        int totalPrice
){
    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getProduct().getProductName(),
                orderProduct.getQuantity(),
                orderProduct.getTotalPrice()
        );
    }
}
