package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private String address;
    private String postcode;
    private String orderStatus;
    private LocalDateTime orderDatetime;
    private CustomerDto customer;
    private List<OrderItemDto> orderItems;
}
