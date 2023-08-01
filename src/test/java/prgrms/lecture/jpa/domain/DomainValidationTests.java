package prgrms.lecture.jpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DomainValidationTests {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager em;
    private Validator validator;

    @BeforeEach
    public void startMethod() {
        em = emf.createEntityManager();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    public void finishMethod() {
        em.close();
    }

    @Test
    @DisplayName("[유효성]: OrderItem 생성시, order와 item을 반드시 제공")
    public void orderItemFiledValidator() {
        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);
        Item item = getItemData();

        // When
        Set<ConstraintViolation<OrderItem>> orderViolations = validator.validate(
                new OrderItem(1000, 3, null, item)
        );
        Set<ConstraintViolation<OrderItem>> ItemViolations = validator.validate(
                new OrderItem(1000, 3, order, null)
        );
        Set<ConstraintViolation<OrderItem>> allViolations = validator.validate(
                new OrderItem(1000, 3, null, null)
        );
        Set<ConstraintViolation<OrderItem>> noViolation = validator.validate(
                new OrderItem(1000, 3, order, item)
        );

        // Then
        assertThat(orderViolations).hasSize(1);
        assertThat(ItemViolations).hasSize(1);
        assertThat(allViolations).hasSize(2);
        assertThat(noViolation).hasSize(0);
    }

    @Test
    @DisplayName("[유효성]: Order 생성 시, Member를 반드시 제공해야 함")
    public void orderFiledValidator() {
        // Given
        Member member = getMemberData();

        // When
        Set<ConstraintViolation<Order>> noViolations = validator.validate(
                new Order(UUID.randomUUID().toString(),
                        "메모",
                        OrderStatus.CANCELLED,
                        LocalDateTime.now(),
                        member)
        );

        Set<ConstraintViolation<Order>> violations = validator.validate(
                new Order(UUID.randomUUID().toString(),
                        "메모",
                        OrderStatus.CANCELLED,
                        LocalDateTime.now(),
                        null)
        );
        // Then
        assertThat(noViolations).hasSize(0);
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("[유효성]: Item 생성 시, 가격과 재고수량은 0 이상이어야 한다.")
    public void ItemFiledValidator() {

        //== when ==//
        Set<ConstraintViolation<Item>> priceViolations = validator.validate(
                new Item(-100L, 10L)
        );
        Set<ConstraintViolation<Item>> stockQuantityViolations = validator.validate(
                new Item(100L, -10L)
        );
        Set<ConstraintViolation<Item>> noViolation = validator.validate(
                new Item(0, 0)
        );
        Set<ConstraintViolation<Item>> allViolation = validator.validate(
                new Item(-10, -1)
        );

        //== then ==//
        assertThat(priceViolations).hasSize(1);
        assertThat(stockQuantityViolations).hasSize(1);
        assertThat(noViolation).hasSize(0);
        assertThat(allViolation).hasSize(2);
    }



    private Member getMemberData() {
        Member member = new Member("lee", "leeNickName", 30, "lee입니다.", "대한민국");
        em.persist(member);
        return member;
    }

    private Order getOrderData(Member member) {
        Order order = new Order(UUID.randomUUID().toString(), "Test order", OrderStatus.OPENED, LocalDateTime.now(), member);
        em.persist(order);
        return order;
    }

    private Item getItemData() {
        Item item = new Item(1000L, 50L);
        em.persist(item);
        return item;
    }

    private OrderItem getOrderItemData(Order order, Item item) {
        OrderItem orderItem = new OrderItem(item.getPrice(), 3,  order, item);
        em.persist(orderItem);
        return orderItem;
    }
}
