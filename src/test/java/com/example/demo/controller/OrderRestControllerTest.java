package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder() throws Exception {
        OrderItemDto orderItemDto = new OrderItemDto().builder()
                .quantity(10L)
                .item(new ItemDto().builder()
                        .name("사과")
                        .quantity(100L)
                        .price(1000L)
                        .build()
                )
                .build();

        OrderDto orderDto = new OrderDto().builder()
                .address("서울")
                .postcode("12345")
                .orderStatus("accepted")
                .customer(
                    new CustomerDto().builder()
                            .firstName("jung hyun")
                            .lastName("moon")
                            .email("y005@naver.com")
                            .build()
                )
                .orderItems(List.of(orderItemDto))
                .build();

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("order-create",
                                requestFields(
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("address"),
                                        fieldWithPath("postcode").type(JsonFieldType.STRING).description("postcode"),
                                        fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                        fieldWithPath("customer.firstName").type(JsonFieldType.STRING).description("first name"),
                                        fieldWithPath("customer.lastName").type(JsonFieldType.STRING).description("last name"),
                                        fieldWithPath("customer.email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("orderItems[]").type(JsonFieldType.ARRAY).description("orderItems"),
                                        fieldWithPath("orderItems[].quantity").type(JsonFieldType.NUMBER).description("number of items ordered by the customer"),
                                        fieldWithPath("orderItems[].item").type(JsonFieldType.OBJECT).description("item"),
                                        fieldWithPath("orderItems[].item.name").type(JsonFieldType.STRING).description("item name"),
                                        fieldWithPath("orderItems[].item.quantity").type(JsonFieldType.NUMBER).description("item count in stock"),
                                        fieldWithPath("orderItems[].item.price").type(JsonFieldType.NUMBER).description("item price")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("order id"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }

    @Test
    void getOrder() throws Exception {
        OrderItemDto orderItemDto = new OrderItemDto().builder()
                .quantity(10L)
                .item(new ItemDto().builder()
                        .name("사과")
                        .quantity(100L)
                        .price(1000L)
                        .build()
                )
                .build();

        OrderDto orderDto = new OrderDto().builder()
                .address("서울")
                .postcode("12345")
                .orderStatus("accepted")
                .customer(
                        new CustomerDto().builder()
                                .firstName("jung hyun")
                                .lastName("moon")
                                .email("y005@naver.com")
                                .build()
                )
                .orderItems(List.of(orderItemDto))
                .build();

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderDto))
                );

        mockMvc.perform(
                        get("/api/v1/orders/{id}",1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("order-read",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("order information"),

                                        fieldWithPath("data.address").type(JsonFieldType.STRING).description("address"),
                                        fieldWithPath("data.postcode").type(JsonFieldType.STRING).description("postcode"),
                                        fieldWithPath("data.orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                        fieldWithPath("data.customer.firstName").type(JsonFieldType.STRING).description("first name"),
                                        fieldWithPath("data.customer.lastName").type(JsonFieldType.STRING).description("last name"),
                                        fieldWithPath("data.customer.email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("data.orderItems[]").type(JsonFieldType.ARRAY).description("orderItems"),
                                        fieldWithPath("data.orderItems[].quantity").type(JsonFieldType.NUMBER).description("number of items ordered by the customer"),
                                        fieldWithPath("data.orderItems[].item").type(JsonFieldType.OBJECT).description("item"),
                                        fieldWithPath("data.orderItems[].item.name").type(JsonFieldType.STRING).description("item name"),
                                        fieldWithPath("data.orderItems[].item.quantity").type(JsonFieldType.NUMBER).description("item count in stock"),
                                        fieldWithPath("data.orderItems[].item.price").type(JsonFieldType.NUMBER).description("item price"),
                                        fieldWithPath("data.orderDatetime").type(JsonFieldType.STRING).description("orderDatetime"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }

    @Test
    void deleteOrder() throws Exception {
        OrderItemDto orderItemDto = new OrderItemDto().builder()
                .quantity(10L)
                .item(new ItemDto().builder()
                        .name("사과")
                        .quantity(100L)
                        .price(1000L)
                        .build()
                )
                .build();

        OrderDto orderDto = new OrderDto().builder()
                .address("서울")
                .postcode("12345")
                .orderStatus("accepted")
                .customer(
                        new CustomerDto().builder()
                                .firstName("jung hyun")
                                .lastName("moon")
                                .email("y005@naver.com")
                                .build()
                )
                .orderItems(List.of(orderItemDto))
                .build();

        mockMvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
        );

        mockMvc.perform(
                        delete("/api/v1/orders/{id}",1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("order-delete",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.STRING).description("message"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }
}