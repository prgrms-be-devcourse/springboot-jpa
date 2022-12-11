package com.example.springbootpart4.order.dto;

import com.example.springbootpart4.item.dto.ItemDto;
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
