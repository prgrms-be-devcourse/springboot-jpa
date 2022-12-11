package com.example.springbootpart4.order.dto;

import com.example.springbootpart4.domain.order.OrderStatus;
import com.example.springbootpart4.member.dto.MemberDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String uuid;
    private LocalDateTime orderDatetime;
    private OrderStatus orderStatus;
    private String memo;

    private MemberDto memberDto;
    private List<OrderItemDto> orderItemDtos;
}
