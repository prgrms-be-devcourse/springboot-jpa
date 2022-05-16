package com.kdt.exercise.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.exercise.domain.order.MemberRepository;
import com.kdt.exercise.member.dto.MemberDto;
import com.kdt.exercise.member.service.MemberService;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void saveMember() throws Exception {
        memberRepository.deleteAll();
        // Given
        MemberDto request = MemberDto.builder()
                .id(1L)
                .name("kanghonggu")
                .nickName("guppy.kang")
                .age(33)
                .address("seoul")
                .description("--")
                .build();
        String requestString = objectMapper.writeValueAsString(request);

        // When // Then
        this.mockMvc.perform(post("/members")
                        .content(requestString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }


    @Test
    void updateMember() throws Exception {
        // Given
        Long memberId = memberService.saveMember(
                MemberDto.builder()
                        .id(1L)
                        .name("kanghonggu")
                        .nickName("guppy.kang")
                        .age(33)
                        .address("seoul")
                        .description("--")
                        .build()
        );

        MemberDto request = MemberDto.builder()
                .id(1L)
                .name("kanghonggu2")
                .nickName("guppy.kang2")
                .age(43)
                .address("seoul2")
                .description("--2")
                .build();
        String requestString = objectMapper.writeValueAsString(request);

        // When // Then
        this.mockMvc.perform(post("/members/{id}", memberId)
                        .content(requestString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void getMember() throws Exception {
        // Given
        Long memberId = memberService.saveMember(
                MemberDto.builder()
                        .id(1L)
                        .name("kanghonggu")
                        .nickName("guppy.kang")
                        .age(33)
                        .address("seoul")
                        .description("--")
                        .build()
        );

        // When // Then
        this.mockMvc.perform(get("/members/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원이름"),
                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("회원별명"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("설명")
                        )
                ));
    }

}