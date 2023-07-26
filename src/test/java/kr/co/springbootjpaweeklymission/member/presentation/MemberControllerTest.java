package kr.co.springbootjpaweeklymission.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private MemberRepository memberRepository;

    private MemberCreatorRequest creatorRequest;
    private MemberCreatorRequest invalidRequest;

    @BeforeEach
    void setUp() {
        creatorRequest = MemberCreatorFactory.createMemberCreatorRequest();
        invalidRequest = MemberCreatorFactory.createMemberCreatorRequest(" ", " ", " ");
    }

    @DisplayName("유저 등록 API")
    @Test
    void memberRegisterAPITest() throws Exception {
        // When, Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creatorRequest)))
                .andExpect(status().isCreated());
    }

    @DisplayName("유저 이메일로 조회 API")
    @Test
    void find_member_by_id() throws Exception {
        // Given
        final Member member = MemberCreatorFactory.createMember();
        memberRepository.save(member);

        // When, Then
        mockMvc.perform(get("/api/v1/members/example@domain.top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.email").value(member.getEmail()))
                .andExpect(jsonPath("$.cellPhone").value(member.getCellPhone()));
    }

    @DisplayName("이름, 이메일, 핸드폰 번호 형식이 잘못된 유저 등록 API 요청")
    @Test
    void memberCreatorRequestMethodArgumentNotValidExceptionTest() throws Exception {
        // When, Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationExceptions.size()").value(3));
    }
}
