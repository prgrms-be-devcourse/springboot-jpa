package prgms.jpamission2.orderTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.jpamission2.config.domain.Furniture;
import prgms.jpamission2.config.domain.Item;
import prgms.jpamission2.config.domain.Member;
import prgms.jpamission2.config.domain.Order;
import prgms.jpamission2.config.domain.OrderItem;
import prgms.jpamission2.config.domain.OrderRepository;
import prgms.jpamission2.config.domain.OrderStatus;

import java.util.ArrayList;
import java.util.UUID;


@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private static Order order1;
    private static Order order2;

    @BeforeEach
    void setUp() {
        String name1 = "오세한";
        int age1 = 27;
        String nickName1 = "sehan";
        String address1 = "경기도 용인시";
        String description1 = "백둥이";

        Member member1 = new Member(name1,nickName1,age1,address1,description1);

        String name2 = "세한오";
        int age2 = 27;
        String nickName2 = "hanse";
        String address2 = "충북 청주시";
        String description2 = "프롱이";

        Member member2 = new Member(name2,nickName2,age2,address2,description2);

        String uuid1 = UUID.randomUUID().toString();
        String memo1 = "부재시 전화주세요.";
        OrderStatus orderStatus1 = OrderStatus.OPENED;

        order1 = new Order(uuid1,orderStatus1,memo1,member1);

        String uuid2 = UUID.randomUUID().toString();
        String memo2 = "문 앞에 놔주세요";
        OrderStatus orderStatus2 = OrderStatus.CANCELLED;

        order2 = new Order(uuid2,orderStatus2,memo2,member2);

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
        Member newMember = new Member(null,null,0,null,null);

        //When
        order1.addMember(newMember);
        Order order = newMember.getOrders().get(0);

        //Then
        Assertions.assertThat(order).usingRecursiveComparison().isEqualTo(order1);
    }

    @Test
    void 연관관계_메소드_테스트_아이템추가() {
        ArrayList<Object> objects = new ArrayList<>();
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
