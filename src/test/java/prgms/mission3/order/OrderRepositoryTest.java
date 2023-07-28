package prgms.mission3.order;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.mission3.order.domain.Member;
import prgms.mission3.order.domain.Order;
import prgms.mission3.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private static Order order1;
    private static Order order2;

    @BeforeAll
    void setUp () {
        Member member1 = new Member();
        member1.setName("오세한");
        member1.setAge(27);
        member1.setNickName("sehan");
        member1.setAddress("경기도 용인시");
        member1.setDescription("백둥이");

        Member member2 = new Member();
        member2.setName("세한오");
        member2.setAge(27);
        member2.setNickName("hanse");
        member2.setAddress("충북 청주시");
        member2.setDescription("프롱이");

        Order order1 = new Order();
        order1.setUuid(UUID.randomUUID().toString());
        order1.setMemo("부재시 전화주세요.");
        order1.setOrderDatetime(LocalDateTime.now());
        order1.setOrderStatus(OrderStatus.OPENED);

        order1.setMember(member1);

        Order order2 = new Order();
        order2.setUuid(UUID.randomUUID().toString());
        order2.setMemo("문 앞에 놔주세요");
        order2.setOrderDatetime(LocalDateTime.now());
        order2.setOrderStatus(OrderStatus.CANCELLED);

        order2.setMember(member2);

        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    void 주문_조회_테스트() {
        

    }

    @Test
    void 회원_조회_테스트() {

    }

    @Test
    void 연관관계_메소드_테스트() {

    }





}
