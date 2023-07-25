package com.programmers.jpa_mission.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerTest {
    @Test
    void 이름_검증_테스트() {
        //when & then
        Assertions.assertThatThrownBy(() -> new Customer("13aa", "Shin"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
