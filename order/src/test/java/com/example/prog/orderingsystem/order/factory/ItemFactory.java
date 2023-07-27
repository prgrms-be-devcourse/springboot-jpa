package com.example.prog.orderingsystem.order.factory;

import com.example.prog.orderingsystem.order.domain.Item;

public class ItemFactory {

    public static Item getNewItem() {
        return  Item.builder()
                .name("Seyeon")
                .price(10000L)
                .capacity(180L)
                .description("memo")
                .build();
    }
}
