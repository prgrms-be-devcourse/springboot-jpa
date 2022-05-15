package com.kdt.JpaWeekly;

import com.kdt.JpaWeekly.domain.customer.Customer;
import com.kdt.JpaWeekly.domain.customer.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private final long id = 1L;

    @AfterAll
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @Order(1)
    void customer을_저장할_때_전달받은_customer가_정상적인_entity라면_정상적으로_저장하고_entity를_반환한다() {
        //given
        Customer customer = customer();

        //when
        Customer savedCustomer = repository.save(customer);

        //then
        assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    @Order(2)
    void customer를_id값으로_단건조회할_때_존재한다면_entity를_반환한다() {
        //given
        Customer customer = customer();

        //when
        Optional<Customer> foundEntity = repository.findById(id);

        //then
        assertThat(foundEntity).isNotEmpty();
        assertThat(foundEntity.get()).isEqualTo(customer);
    }

    @Test
    @Order(3)
    void customer를_id값으로_단건조회할_때_존재하지_않는다면_Optional_empty를_반환한다() {
        //given
        long id = 0L;

        //when
        Optional<Customer> foundEntity = repository.findById(id);

        //then
        assertThat(foundEntity).isEmpty();
    }

    @Test
    @Order(4)
    void customer를_수정할_때_전달받은_customer가_정상적인_entity라면_정상적으로_수정하고_entity를_반환한다() {
        //given
        String firstName = "update first name";
        String lastName = "update last name";
        Customer customer = new Customer.Builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        //when
        Customer updatedCustomer = repository.save(customer);

        //then
        assertThat(updatedCustomer).isEqualTo(customer);
    }

    @Test
    @Order(5)
    void customer를_id값으로_삭제할_때_전달받은_id값을_가진_데이터가_존재하지_않는다면_EmptyResultDataAccessException이_발생한다() {
        //given
        long id = 0L;

        //when, then
        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(id));
    }

    @Test
    @Order(6)
    void customer를_id값으로_삭제할_때_전달받은_id값을_가진_데이터가_존재한다면_예외가_발생하지_않는다() {
        //when, then
        assertDoesNotThrow(() -> repository.deleteById(id));
    }

    private Customer customer() {
        return new Customer.Builder()
                .id(id)
                .firstName("youngjun")
                .lastName("ryu")
                .build();
    }
}
