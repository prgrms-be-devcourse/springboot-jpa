package com.programmers.springbootjpa.domain.mission3.order;

import com.programmers.springbootjpa.domain.mission3.member.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    private Member member;

    @BeforeEach
    public void setUp() {
        member = new Member("hyemin", "nick", 26, "서울특별시 성북구");
    }

    @DisplayName("주문을 생성한다")
    @CsvSource(value = {
            "OPENED : orderOpened",
            "CANCELLED : orderClosed"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(OrderStatus orderStatus, String memo) {
        //given
        //when
        com.programmers.springbootjpa.domain.mission3.order.Order order = new com.programmers.springbootjpa.domain.mission3.order.Order(orderStatus, memo, member);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(orderStatus);
        assertThat(order.getMemo()).isEqualTo(memo);
        assertThat(order.getMember()).isEqualTo(member);
    }

    @DisplayName("주문을 수정한다")
    @Test
    void testUpdate() {
        //given
        com.programmers.springbootjpa.domain.mission3.order.Order order = new Order(OrderStatus.OPENED, "memo", member);
        Member member2 = new Member("Jae", "jh", 26, "서울특별시 성북구");

        //when
        order.updateOrderStatus(OrderStatus.CANCELLED);
        order.updateMemo("newMemo");
        order.updateMember(member2);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getMemo()).isEqualTo("newMemo");
        assertThat(order.getMember()).isEqualTo(member2);
    }
}