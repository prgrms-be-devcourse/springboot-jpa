package com.programmers.jpa.order.dto;

import lombok.Builder;

@Builder
public record OrderItemDto(
        Long id,
        Integer price,
        Integer quantity,
        ItemDto itemDto) {
}
