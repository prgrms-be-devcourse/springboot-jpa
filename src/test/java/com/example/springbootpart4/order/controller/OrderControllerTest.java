package com.example.springbootpart4.order.controller;

import com.example.springbootpart4.domain.order.OrderRepository;
import com.example.springbootpart4.domain.order.OrderStatus;
import com.example.springbootpart4.item.dto.ItemDto;
import com.example.springbootpart4.item.dto.ItemType;
import com.example.springbootpart4.member.dto.MemberDto;
import com.example.springbootpart4.order.dto.OrderDto;
import com.example.springbootpart4.order.dto.OrderItemDto;
import com.example.springbootpart4.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

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
    void saveCallTest() throws Exception {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("문앞 보관 해주세요.")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("김소현")
                                .nickName("soso._.hyeon")
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

        // When // Then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-save",
                        requestFields(
                                fieldWithPath("uuid").type(JsonFieldType.STRING).description("UUID"),
                                fieldWithPath("orderDatetime").type(JsonFieldType.STRING).description("orderDatetime"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.id").type(JsonFieldType.NULL).description("memberDto.id"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("memberDto.nickName").type(JsonFieldType.STRING).description("memberDto.nickName"),
                                fieldWithPath("memberDto.age").type(JsonFieldType.NUMBER).description("memberDto.age"),
                                fieldWithPath("memberDto.address").type(JsonFieldType.STRING).description("memberDto.address"),
                                fieldWithPath("memberDto.description").type(JsonFieldType.STRING).description("memberDto.description"),
                                fieldWithPath("orderItemDtos[]").type(JsonFieldType.ARRAY).description("orderItemDtos"),
                                fieldWithPath("orderItemDtos[].id").type(JsonFieldType.NULL).description("orderItemDtos.id"),
                                fieldWithPath("orderItemDtos[].price").type(JsonFieldType.NUMBER).description("orderItemDtos.price"),
                                fieldWithPath("orderItemDtos[].quantity").type(JsonFieldType.NUMBER).description("orderItemDtos.quantity"),
                                fieldWithPath("orderItemDtos[].itemDtos[]").type(JsonFieldType.ARRAY).description("orderItemDtos.itemDtos"),
                                fieldWithPath("orderItemDtos[].itemDtos[].price").type(JsonFieldType.NUMBER).description("orderItemDtos.itemDtos.price"),
                                fieldWithPath("orderItemDtos[].itemDtos[].stockQuantity").type(JsonFieldType.NUMBER).description("orderItemDtos.itemDtos.stockQuantity"),
                                fieldWithPath("orderItemDtos[].itemDtos[].type").type(JsonFieldType.STRING).description("orderItemDtos.itemDtos.type"),
                                fieldWithPath("orderItemDtos[].itemDtos[].chef").type(JsonFieldType.STRING).description("orderItemDtos.itemDtos.chef")

                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/orders/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/orders")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}