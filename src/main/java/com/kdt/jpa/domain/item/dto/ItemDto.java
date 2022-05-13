package com.kdt.jpa.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null은 제외
public class ItemDto {

    private Long id;
    private int price;
    private int stockQuantity;

    private ItemType type;

    // Food
    private String chef;

    // Car
    private Integer power;

    // Furniture
    private Integer width;
    private Integer height;

}
