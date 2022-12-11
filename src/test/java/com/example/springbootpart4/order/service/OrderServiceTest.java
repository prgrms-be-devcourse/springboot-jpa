package com.example.springbootpart4.order.service;

import com.example.springbootpart4.domain.order.OrderRepository;
import com.example.springbootpart4.domain.order.*;
import com.example.springbootpart4.item.dto.ItemDto;
import com.example.springbootpart4.item.dto.ItemType;
import com.example.springbootpart4.member.dto.MemberDto;
import com.example.springbootpart4.order.dto.OrderDto;
import com.example.springbootpart4.order.dto.OrderItemDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void save_test() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("문앞 보관 해주세요.")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("강홍구")
                                .nickName("guppy.kang")
                                .address("서울시 동작구만 움직이면 쏜다.")
                                .age(33)
                                .description("---")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDtos(List.of(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("백종원")
                                                .price(1000)
                                                .build()
                                ))
                                .build()
                ))
                .build();

        // When
        String savedUuid = orderService.save(orderDto);

        // Then
        assertThat(uuid).isEqualTo(savedUuid);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void findOneTest() throws NotFoundException {
        // Given
        String orderId = uuid;

        // When
        OrderDto one = orderService.findOne(uuid);

        // Then
        assertThat(one.getUuid()).isEqualTo(orderId);
    }

    @Test
    void findAllTest() {
        //Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        Page<OrderDto> all = orderService.findAll(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

}