package com.programmers.jpa.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Integer price;
    private Integer quantity;

    private ItemDto itemDto;
}
