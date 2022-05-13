package com.kdt.jpa.domain.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.jpa.domain.item.dto.ItemDto;
import com.kdt.jpa.domain.item.dto.ItemType;
import com.kdt.jpa.domain.member.dto.MemberDto;
import com.kdt.jpa.domain.order.OrderItem;
import com.kdt.jpa.domain.order.OrderStatus;
import com.kdt.jpa.domain.order.dto.OrderDto;
import com.kdt.jpa.domain.order.dto.OrderItemDto;
import com.kdt.jpa.domain.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private String orderId = UUID.randomUUID().toString();
    private ItemDto itemDto;
    private MemberDto memberDto;
    private OrderItemDto orderItemDto;
    private OrderDto orderDto;


    @BeforeEach
    void setup() {
        itemDto = ItemDto.builder()
                .type(ItemType.FOOD)
                .chef("범식")
                .price(1000)
                .build();

        memberDto = MemberDto.builder()
                .name("beomsic ko")
                .nickName("beomsic")
                .age(26)
                .address("서울시 구로구")
                .description("testtest")
                .build();

        orderItemDto = OrderItemDto.builder()
                .price(1000)
                .quantity(200)
                .itemDtos(List.of(itemDto))
                .build();

        orderDto = OrderDto.builder()
                .orderId(orderId)
                .memo("테스트")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(memberDto)
                .orderItemDtos(List.of(orderItemDto))
                .build();

//        String saveId = orderService.save(orderDto);
//
//        assertThat(orderId.equals(saveId), is(true));
    }

    @Test
    @DisplayName("order 등록 테스트")
    void testSaveApi() throws Exception {
        // Given
//        itemDto = ItemDto.builder()
//                .type(ItemType.FOOD)
//                .chef("범석")
//                .price(1000)
//                .build();
//
//        memberDto = MemberDto.builder()
//                .name("beomsic kko")
//                .nickName("beomsic")
//                .age(26)
//                .address("서울시 구로구")
//                .description("test")
//                .build();
//
//        orderItemDto = OrderItemDto.builder()
//                .price(1000)
//                .quantity(200)
//                .itemDtos(List.of(itemDto))
//                .build();
//
//        orderDto = OrderDto.builder()
//                .orderId(orderId)
//                .memo("테스트1")
//                .orderDatetime(LocalDateTime.now())
//                .orderStatus(OrderStatus.OPENED)
//                .memberDto(memberDto)
//                .orderItemDtos(List.of(orderItemDto))
//                .build();

        // When
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }

    @Test
    @DisplayName("아이디 통해 주문 조회 테스트")
    void getOne() throws Exception {
        // Given

        // When
        String saveId = orderService.save(orderDto);

        mockMvc.perform(get("/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print());

        // Then
        assertThat(orderId.equals(saveId), is(true));
    }

    @Test
    @DisplayName("모든 주문 정보 조회")
    void getAll() throws Exception {
        // Given
        orderService.save(orderDto);
        // When
        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        // Then

    }
}