package com.kdt.mission1.domain;

import com.kdt.mission1.domain.order.Member;
import com.kdt.mission1.domain.order.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberOrderTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버를 저장할 수 있다")
    void saveTest() {
        Member member = new Member(1L,
                "Mamma",
                "JJJ",
                333,
                "My address",
                "----",
                Collections.emptyList());

        Member savedMember = memberRepository.save(member);
        assertThat(savedMember)
                .hasFieldOrPropertyWithValue("id", member.getId())
                .hasFieldOrPropertyWithValue("name", member.getName())
                .hasFieldOrPropertyWithValue("nickName", member.getNickName())
                .hasFieldOrPropertyWithValue("age", member.getAge())
                .hasFieldOrPropertyWithValue("address", member.getAddress())
                .hasFieldOrPropertyWithValue("description", member.getDescription());

    }
}
