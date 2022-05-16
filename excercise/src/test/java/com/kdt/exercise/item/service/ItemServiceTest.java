package com.kdt.exercise.item.service;

import com.kdt.exercise.item.dto.ItemDto;
import com.kdt.exercise.item.dto.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    Long saveId = null;

    @BeforeEach
    void setUp() {
        // Given
        ItemDto itemDto = ItemDto.builder()
                .type(ItemType.FOOD)
                .chef("백종원")
                .price(1000)
                .stockQuantity(100)
                .build();

        // When
        saveId = itemService.save(itemDto);

        // Then
        log.info("saved id : {}", saveId);
    }

    @Test
    void getItem() {
        // Given
        Long id = saveId;

        // When
        ItemDto item = itemService.getItem(id);

        // Then
        assertThat(item.getType()).isEqualTo(ItemType.FOOD);
    }
}