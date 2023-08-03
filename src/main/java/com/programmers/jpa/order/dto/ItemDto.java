package com.programmers.jpa.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDto(
        Long id,
        int price,
        int stockQuantity,
        ItemType type,
        String chef,
        Integer power,
        Integer width,
        Integer height) {
}
