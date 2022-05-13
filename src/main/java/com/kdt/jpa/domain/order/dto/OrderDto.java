package com.kdt.jpa.domain.order.dto;

import com.kdt.jpa.domain.member.dto.MemberDto;
import com.kdt.jpa.domain.order.OrderItem;
import com.kdt.jpa.domain.order.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String orderId;
    private LocalDateTime orderDatetime;
    private OrderStatus orderStatus;
    private String memo;

    private MemberDto memberDto;
    private List<OrderItemDto> orderItemDtos;
}
