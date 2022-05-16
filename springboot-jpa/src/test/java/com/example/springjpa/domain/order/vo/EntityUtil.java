package com.example.springjpa.domain.order.vo;

import com.example.springjpa.domain.order.Member;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.springjpa.domain.order.Member.MemberBuilder;

public class EntityUtil {
    private EntityUtil() {
    }

    public static Member getNewMember() {
        return new MemberBuilder()
                .name("태산")
                .nickName(UUID.randomUUID().toString())
                .age(10)
                .address("주소")
                .description("Desc")
                .orders(new ArrayList<>())
                .build();
    }
}
