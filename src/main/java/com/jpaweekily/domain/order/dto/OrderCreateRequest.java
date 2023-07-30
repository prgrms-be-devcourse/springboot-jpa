package com.jpaweekily.domain.order.dto;

import java.util.List;

public record OrderCreateRequest (
    String nickName,
    String address,
    List<OrderProductCreate> orderProductCreateList
){
}
