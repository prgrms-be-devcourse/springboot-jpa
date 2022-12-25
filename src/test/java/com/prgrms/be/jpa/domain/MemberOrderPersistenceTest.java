package com.prgrms.be.jpa.domain;

import com.prgrms.be.jpa.repository.MemberRepository;
import com.prgrms.be.jpa.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.be.jpa.domain.OrderStatus.OPENED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
public class MemberOrderPersistenceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문과 멤버 사이는 N:1 연관관계이다.")
    void checkManyToOneTest() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구", "백엔드 개발지망생입니다.");
        memberRepository.save(member);

        // when
        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "백엔드 재밌음.", member);
        orderRepository.save(order);

        // then
        Optional<Order> entity = orderRepository.findById(order.getUuid());
        assertThat(entity.isPresent(), is(true));
        assertThat(entity.get().getMember(), samePropertyValuesAs(member));
    }

    @Test
    @DisplayName("멤버와 주문은 1:N 관계이다.")
    void checkOneToManyTest() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구", "백엔드 개발지망생입니다.");
        memberRepository.save(member);

        // when
        Order order1 = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "백엔드 재밌음.", member);
        Order order2 = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "백엔드 재밌음.", member);
        Order order3 = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "백엔드 재밌음.", member);

        Optional<Member> updatedMember = memberRepository.findById(member.getId());
        Assertions.assertThat(updatedMember).isPresent();

        // then
        Assertions.assertThat(updatedMember.get().getOrders())
                .hasSize(3)
                .contains(order1, order2, order3);
    }
}
