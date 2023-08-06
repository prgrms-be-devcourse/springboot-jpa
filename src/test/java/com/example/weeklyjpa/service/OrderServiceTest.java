package com.example.weeklyjpa.service;

import com.example.weeklyjpa.domain.item.Keyboard;
import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.repository.ItemRepository;
import com.example.weeklyjpa.repository.MemberRepository;
import com.example.weeklyjpa.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    @DisplayName("주문 생성에 성공한다.")
    void CREATE_ORDER_SUCCESS(){
        //given
        Member member = new Member("joje", "chemi", 22, "paju", "hihitean");
        Keyboard keyboard = Keyboard.createKeyboard("keykron", 100000, 1000, "joje");
        Long memberId = memberRepository.save(member).getId();
        Long itemId = itemRepository.save(keyboard).getId();

        //when
        Long orderId = orderService.createOrder(memberId, itemId, 100);

        //then
        assertThat(orderId).isEqualTo(1L);
    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {11,100})
    @DisplayName("주문이 재고 부족으로 실패한다.")
    void CREATE_ORDER_FAIL_BY_OUT_OF_STOCK(int stockQuantity){
        //given
        Member member = new Member("joje", "chemi", 22, "paju", "hihitean");
        Keyboard keyboard = Keyboard.createKeyboard("keykron", 100000, 10, "joje"); // 질문
        Long memberId = memberRepository.save(member).getId();
        Long itemId = itemRepository.save(keyboard).getId();

        //when
        assertThatThrownBy(() -> orderService.createOrder(memberId, itemId, stockQuantity)).isInstanceOf(IllegalArgumentException.class);
    }

}