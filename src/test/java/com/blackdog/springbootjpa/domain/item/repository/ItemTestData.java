package com.blackdog.springbootjpa.domain.item.repository;

import com.blackdog.springbootjpa.domain.item.model.Item;
import com.blackdog.springbootjpa.domain.item.vo.OriginNation;
import com.blackdog.springbootjpa.domain.item.vo.Price;

public class ItemTestData {

    public static Item buildItem() {
        return Item.builder()
                .price(new Price(1000))
                .nation(new OriginNation("KR"))
                .build();
    }

    public static Item buildNewItem() {
        return Item.builder()
                .price(new Price(2000))
                .nation(new OriginNation("JP"))
                .build();
    }
}
