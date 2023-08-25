package com.programmers.jpa.order.dto;

import com.programmers.jpa.domain.order.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        String uuid,
        LocalDateTime updateAt,
        OrderStatus orderStatus,
        String memo,
        MemberDto memberDto,
        List<OrderItemDto> orderItemDtos) {
}
