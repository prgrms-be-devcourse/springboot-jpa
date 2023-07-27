package com.jpaweekily.domain.order.dto;

public record OrderCreateRequest (
    String nickName,
    String address
){
}
