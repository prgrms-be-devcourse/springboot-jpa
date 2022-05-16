package com.example.springjpa.domain.order.vo;

import com.example.springjpa.domain.order.Member;
import com.example.springjpa.domain.order.Orders;

import java.util.UUID;


public class EntityUtil {
    private EntityUtil() {
    }

    public static Member getNewMember() {
        return Member.builder()
                .name("태산")
                .nickName(UUID.randomUUID().toString())
                .age(1)
                .address("주소")
                .description("Desc")
                .orders(new Orders())
                .build();
    }
}
