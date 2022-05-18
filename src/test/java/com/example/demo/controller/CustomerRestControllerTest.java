package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CustomerRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto().builder()
                .firstName("jung hyun")
                .lastName("moon")
                .email("y005@naver.com")
                .build();

        mockMvc.perform(
                    post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("customer-create",
                                requestFields(
                                        fieldWithPath("firstName").type(JsonFieldType.STRING).description("first name"),
                                        fieldWithPath("lastName").type(JsonFieldType.STRING).description("last name"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email")
                                        ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("customer id"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }

    @Test
    void getCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto().builder()
                .firstName("jung hyun")
                .lastName("moon")
                .email("y005@naver.com")
                .build();

        mockMvc.perform(
                post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
        );

        mockMvc.perform(
                        get("/api/v1/customers/{id}", 1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("customer-read",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("customer"),
                                        fieldWithPath("data.firstName").type(JsonFieldType.STRING).description("first name"),
                                        fieldWithPath("data.lastName").type(JsonFieldType.STRING).description("last name"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }

    @Test
    void deleteCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto().builder()
                .firstName("jung hyun")
                .lastName("moon")
                .email("y005@naver.com")
                .build();

        mockMvc.perform(
                post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
        );

        mockMvc.perform(
                        delete("/api/v1/customers/{id}", 1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("customer-delete",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data").type(JsonFieldType.STRING).description("message"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }
}