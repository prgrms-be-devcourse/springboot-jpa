package com.prgrms.be.jpa.domain.order;

import com.prgrms.be.jpa.repository.MemberRepository;
import com.prgrms.be.jpa.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.be.jpa.domain.order.OrderStatus.OPENED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("멤버를 생성하고 테이블에 추가할 수 있다.")
    void member_insert_test() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구", "백엔드 개발지망생입니다.");

        // when
        memberRepository.save(member);

        // then
        Optional<Member> entity = memberRepository.findById(member.getId());
        assertThat(entity.isPresent(), is(true));
        assertThat(entity.get(), samePropertyValuesAs(member));
    }

    @Test
    @DisplayName("주문과 멤버 사이의 1:N 연관관계가 매핑된 것을 알 수 있다.")
    void orderToMember_relation_test() {
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
}
