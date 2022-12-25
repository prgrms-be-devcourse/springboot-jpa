package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.Member;
import com.prgrms.be.jpa.domain.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.prgrms.be.jpa.domain.OrderStatus.CANCELLED;
import static com.prgrms.be.jpa.domain.OrderStatus.OPENED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("주문상태를 이용해서 주문들을 찾을 수 있다.")
    void find_orderStatus_test() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구");
        Order orderOpened = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, member);
        Order orderCancelled = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), CANCELLED, member);

        // when
        memberRepository.save(member);
        orderRepository.save(orderOpened);
        orderRepository.save(orderCancelled);

        // then
        List<Order> allByOrderStatus = orderRepository.findAllByOrderStatus(OPENED);
        assertThat(allByOrderStatus, hasSize(1));
        assertThat(allByOrderStatus.get(0).getUuid(), is(orderOpened.getUuid()));
    }

    @Test
    @DisplayName("주문상태를 이용하여 OrderDatetime이 빠른 순서로 주문들을 찾을 수 있다.")
    void find_orderStatus_orderByOrderDatetime_test() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구");
        Order orderOpened = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, member);
        Order orderCancelled = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, member);

        // when
        memberRepository.save(member);
        orderRepository.save(orderOpened);
        orderRepository.save(orderCancelled);

        // then
        List<Order> allByOrderStatus = orderRepository.findAllByOrderStatusOrderByOrderDatetime(OPENED);
        assertThat(allByOrderStatus, hasSize(2));
        assertThat(allByOrderStatus.get(0).getUuid(), is(orderOpened.getUuid()));
        assertThat(allByOrderStatus.get(1).getUuid(), is(orderCancelled.getUuid()));
    }

    @Test
    @DisplayName("메모에 적힌 문자열을 이용하여 주문을 찾을 수 있다.")
    void find_memo_test() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구");
        Order orderOpened = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "1234", member);
        Order orderCancelled = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), CANCELLED, member);

        // when
        memberRepository.save(member);
        orderRepository.save(orderOpened);
        orderRepository.save(orderCancelled);

        // then
        List<Order> memoOrders = orderRepository.findByMemoContains("2");
        assertThat(memoOrders, hasSize(1));
        assertThat(memoOrders.get(0).getMemo().contains("2"), is(true));
    }
}