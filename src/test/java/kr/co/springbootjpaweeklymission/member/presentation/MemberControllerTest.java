package kr.co.springbootjpaweeklymission.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberCreatorRequest creatorRequest;
    private MemberCreatorRequest invalidRequest;

    @BeforeEach
    void setUp() {
        creatorRequest = MemberCreatorFactory.createMemberCreatorRequest();
        invalidRequest = MemberCreatorFactory.createMemberCreatorRequest(" ", " ", " ");
    }

    @Test
    void 유저_등록_API() throws Exception {
        // When, Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creatorRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void 이름_이메일_핸드폰번호_형식이_잘못된_유저_등록_API_요청() throws Exception {
        // When, Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationExceptions.size()").value(3));
    }
}
