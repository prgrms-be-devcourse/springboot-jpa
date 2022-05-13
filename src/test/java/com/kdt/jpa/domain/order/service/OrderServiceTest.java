package com.kdt.jpa.domain.order.service;

import com.kdt.jpa.domain.item.dto.ItemDto;
import com.kdt.jpa.domain.item.dto.ItemType;
import com.kdt.jpa.domain.member.Member;
import com.kdt.jpa.domain.member.dto.MemberDto;
import com.kdt.jpa.domain.order.OrderRepository;
import com.kdt.jpa.domain.order.OrderStatus;
import com.kdt.jpa.domain.order.dto.OrderDto;
import com.kdt.jpa.domain.order.dto.OrderItemDto;
import com.sun.javadoc.MemberDoc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    String orderId = UUID.randomUUID().toString();


    @BeforeEach
    @DisplayName("order save 테스트")
    void testSave() {

        ItemDto itemDto = ItemDto.builder()
                .type(ItemType.FOOD)
                .chef("범식")
                .price(1000)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .name("beomsic ko")
                .nickName("beomsic")
                .age(26)
                .address("서울시 구로구")
                .description("testtest")
                .build();

        OrderItemDto orderItemDto = OrderItemDto.builder()
                .price(1000)
                .quantity(200)
                .itemDtos(List.of(itemDto))
                .build();

        OrderDto orderDto = OrderDto.builder()
                .orderId(orderId)
                .memo("테스트")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(memberDto)
                .orderItemDtos(List.of(orderItemDto))
                .build();

        String saveId = orderService.save(orderDto);
        assertThat(Objects.equals(orderId, saveId), is(true));
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        // Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        Page<OrderDto> orders = orderService.findAll(page);

        // Then
        assertThat(orders.getTotalElements() == 1, is(true));
    }

    @Test
    void testFindOne() {
        // Given

        // When
        OrderDto one = orderService.findOne(orderId);

        // Then
        assertThat(Objects.equals(one.getOrderId(), orderId), is(true));
    }
}