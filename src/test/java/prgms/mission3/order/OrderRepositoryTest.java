package prgms.mission3.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.mission3.order.domain.Furniture;
import prgms.mission3.order.domain.Item;
import prgms.mission3.order.domain.Member;
import prgms.mission3.order.domain.Order;
import prgms.mission3.order.domain.OrderItem;
import prgms.mission3.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    Order order1;
    Order order2;

    @BeforeEach
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

        order1 = new Order();
        order1.setUuid(UUID.randomUUID().toString());
        order1.setMemo("부재시 전화주세요.");
        order1.setOrderDatetime(LocalDateTime.now());
        order1.setOrderStatus(OrderStatus.OPENED);

        order1.setMember(member1);

        order2 = new Order();
        order2.setUuid(UUID.randomUUID().toString());
        order2.setMemo("문 앞에 놔주세요");
        order2.setOrderDatetime(LocalDateTime.now());
        order2.setOrderStatus(OrderStatus.CANCELLED);

        order2.setMember(member2);
    }

    @Test
    void 주문회원_조회_테스트() {
        //When
        Member member = order1.getMember();

        //Then
        Assertions.assertThat(member.getName()).isEqualTo("오세한");
    }

    @Test
    void 주문_저장소_조회_테스트() {
        //Given
        orderRepository.save(order1);
        orderRepository.save(order2);

        //When
        int size = orderRepository.findAll().size();

        //Then
        Assertions.assertThat(size).isEqualTo(2);
    }

    @Test
    void 연관관계_메소드_테스트_멤버설정() {
        //Given
        Member newMember = new Member();
        
        //When
        order1.setMember(newMember);
        Order order = newMember.getOrders().get(0);

        //Then
        Assertions.assertThat(order).usingRecursiveComparison().isEqualTo(order1);
    }

    @Test
    void 연관관계_메소드_테스트_아이템추가() {
        //Given
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        Item item = new Furniture();

        //When
        item.setOrderItem(orderItem1);
        item.setOrderItem(orderItem2);

        Item getItem = orderItem2.getItems().get(0);

        //Then
        Assertions.assertThat(getItem).usingRecursiveComparison().isEqualTo(item);
    }





}
