package com.kdt.jpa.domain.order.dto;

import com.kdt.jpa.domain.item.dto.ItemDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long id;
    private Integer price;
    private Integer quantity;

    private List<ItemDto> itemDtos;

}
