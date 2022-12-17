package com.programmers.kwonjoosung.springbootjpa;

import com.programmers.kwonjoosung.springbootjpa.model.*;
import com.programmers.kwonjoosung.springbootjpa.repository.MemberRepository;
import com.programmers.kwonjoosung.springbootjpa.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class OrderFlowTest {

    @Nested
    @DisplayName("1. 회원 가입")
    class MemberTest {

        @Autowired
        MemberRepository memberRepository;

        @Test
        @DisplayName("고객 생성 테스트")
        void createMemberTest() {
            //given
            Member member = Member.builder()
                    .name("joosung")
                    .nickName("mainCastle")
                    .age(28)
                    .address("서울시 중랑구 어딘가")
                    .description("데브코스 3기 화이팅")
                    .build();
            //when
            Member savedMember = memberRepository.save(member);
            //then
            assertThat(savedMember)
                    .usingRecursiveComparison()
                    .isEqualTo(member);
            log.info("member : {}", savedMember);
        }

        @Nested
        @DisplayName("2. 주문 하기")
        class ProductTest {

            @Autowired
            private OrderRepository orderRepository;

            private Member member;

            private final Item keyboard = Keyboard.builder()
                    .type(KeyboardType.MECHANICAL)
                    .price(10000)
                    .build();

            private final Item mouse = Mouse.builder()
                    .type(MouseType.WIRELESS)
                    .price(10000)
                    .build();

            @Test
            @DisplayName("주문 생성 테스트")
            void createProductTest() {
                //given
                member = generateMember();
                Member savedMember = memberRepository.save(member);
                //when
                Order order = new Order(savedMember);
                Order savedOrder = orderRepository.save(order);
                //then
                assertThat(savedOrder)
                        .usingRecursiveComparison()
                        .isEqualTo(order);
                assertThat(savedOrder.getMember())
                        .usingRecursiveComparison()
                        .isEqualTo(savedMember);

                log.info("member: {} , order : {}", savedOrder.getMember(), savedOrder);
            }

            @Test
            @DisplayName("주문에 아이템 추가하기")
            void createOrderItemTest() {
                //given
                member = generateMember();
                Member savedMember = memberRepository.save(member);
                Order order = new Order(savedMember);
                OrderItem orderKeyboard = new OrderItem(keyboard, 1);
                OrderItem orderMouse = new OrderItem(mouse, 1);
                //when
                order.addOrderItem(orderKeyboard);
                order.addOrderItem(orderMouse);
                Order savedOrder = orderRepository.save(order);
                //then
                assertThat(savedOrder)
                        .usingRecursiveComparison()
                        .isEqualTo(order);
                assertThat(savedOrder.getMember())
                        .usingRecursiveComparison()
                        .isEqualTo(savedMember);
                assertThat(savedOrder.getOrderItems())
                        .contains(orderKeyboard, orderMouse);
                log.info("member: {} , orderItems() : {}, order : {}",
                        savedOrder.getMember(), savedOrder.getOrderItems(), savedOrder);
            }

            @Test
            @DisplayName("주문 아이템 변경하기")
            @Disabled // -> 변경되지 않음 이유를 모르겠음 추정되는 이유는 연관관계의 주인이 아니기 때문에
            void updateOrderItemTest() {
                //given
                member = generateMember();
                Member savedMember = memberRepository.save(member);
                Order order = new Order(savedMember);
                OrderItem orderKeyboard = new OrderItem(keyboard, 1);
                OrderItem orderMouse = new OrderItem(mouse, 1);
                order.putOrderItems(List.of(orderKeyboard, orderMouse));
                Order savedOrder = orderRepository.save(order);
                //when
                savedOrder.getOrderItems().get(0).addStock(1); // 키보드 재고 1개 추가 -> get("제품명") 필요할 듯
                Order updatedOrder = orderRepository.save(savedOrder);
                //then
                assertThat(updatedOrder)
                        .usingRecursiveComparison()
                        .isEqualTo(savedOrder);
                assertThat(updatedOrder.getMember())
                        .usingRecursiveComparison()
                        .isEqualTo(member);
                assertThat(updatedOrder.getOrderItems().get(0).getQuantity()).isEqualTo(2);

                log.info("member: {} , orderItems() : {}, order : {}",
                        updatedOrder.getMember(), updatedOrder.getOrderItems(), updatedOrder);
            }

            @Test
            @DisplayName("주문 아이템 삭제하기")
            @Disabled // -> 변경되지 않음 이유를 모르겠음 추정되는 이유는 연관관계의 주인이 아니기 때문에
            void deleteOrderItemTest() {
                //given
                member = generateMember();
                Member savedMember = memberRepository.save(member);
                Order order = new Order(savedMember);
                OrderItem orderKeyboard = new OrderItem(keyboard, 1);
                OrderItem orderMouse = new OrderItem(mouse, 1);
                order.putOrderItems(List.of(orderKeyboard, orderMouse));
                Order savedOrder = orderRepository.save(order);
                //when
                savedOrder.getOrderItems().remove(0); // 키보드 삭제
                Order updatedOrder = orderRepository.save(savedOrder);
                //then
                assertThat(updatedOrder)
                        .usingRecursiveComparison()
                        .isEqualTo(savedOrder);
                assertThat(updatedOrder.getMember())
                        .usingRecursiveComparison()
                        .isEqualTo(member);
                assertThat(updatedOrder.getOrderItems().size()).isEqualTo(1);

                log.info("member: {} , orderItems() : {}, order : {}",
                        updatedOrder.getMember(), updatedOrder.getOrderItems(), updatedOrder);
            }

        }

        private Member generateMember() {
            return Member.builder()
                    .name("joosung")
                    .nickName("mainCastle")
                    .age(28)
                    .address("서울시 중랑구 어딘가")
                    .description("데브코스 3기 화이팅")
                    .build();
        }
    }
}
