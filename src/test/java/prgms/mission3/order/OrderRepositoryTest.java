package prgms.mission3.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.mission3.order.domain.Member;
import prgms.mission3.order.domain.Order;
import prgms.mission3.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class OrderRepositoryTest {

    String uuid = UUID.randomUUID().toString();

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp () {
        Member member = new Member();
        member.setName("강홍구");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setAddress("서울시 동작구만 움직이면 쏜다.");
        member.setDescription("");

        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        order.setMember(member);

        orderRepository.save(order); // INSERT
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    void JPA_query() {
        Order order = orderRepository.findById(uuid).get(); // SELECT * FROM orders WHERE id = ?
        List<Order> all = orderRepository.findAll(); // SELECT * FROM orders
        orderRepository.existsById(uuid);
    }



}
