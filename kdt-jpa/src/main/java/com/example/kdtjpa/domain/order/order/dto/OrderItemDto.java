package com.example.kdtjpa.domain.order.order.dto;

import com.example.kdtjpa.domain.order.dto.ItemDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Integer price;
    private Integer quantity;

    private List<ItemDto> itemDtos;
}
