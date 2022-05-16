package com.kdt.exercise.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.exercise.item.dto.ItemDto;
import com.kdt.exercise.item.dto.ItemType;
import com.kdt.exercise.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemService itemService;

    @Test
    void saveItem () throws Exception {
        ItemDto item = ItemDto.builder()
                .type(ItemType.CAR)
                .price(100000)
                .stockQuantity(10)
                .power(100)
                .build();


        this.mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(item))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("item-save",
                        requestFields(
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("stockQuantity").type(JsonFieldType.NUMBER).description("재고수량"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("타입"),
                                fieldWithPath("chef").type(JsonFieldType.STRING).description("음식-쉐프").optional(),
                                fieldWithPath("power").type(JsonFieldType.NUMBER).description("자동차-파워").optional(),
                                fieldWithPath("width").type(JsonFieldType.NUMBER).description("가구-넓이").optional(),
                                fieldWithPath("height").type(JsonFieldType.NUMBER).description("가구-높이").optional()
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void getItem () throws Exception {
        // Given
        ItemDto item = ItemDto.builder()
                .type(ItemType.CAR)
                .price(100000)
                .stockQuantity(10)
                .power(100)
                .build();
        Long id = itemService.save(item);

        // When
        this.mockMvc.perform(get("/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("item-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),

                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data.stockQuantity").type(JsonFieldType.NUMBER).description("재고수량"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("타입"),
                                fieldWithPath("data.chef").type(JsonFieldType.STRING).description("음식-쉐프").optional(),
                                fieldWithPath("data.power").type(JsonFieldType.NUMBER).description("자동차-파워").optional(),
                                fieldWithPath("data.width").type(JsonFieldType.NUMBER).description("가구-넓이").optional(),
                                fieldWithPath("data.height").type(JsonFieldType.NUMBER).description("가구-높이").optional()
                        )
                ));

    }

}