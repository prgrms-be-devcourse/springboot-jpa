package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@Slf4j
@ContextConfiguration(classes = WeekApplication.class)
public class CustomerTest {

  @Autowired
  EntityManagerFactory emf;

  @ParameterizedTest
  @CsvSource(value = {"은지은지은지은지은지|박", "명한|유유유유유유유", "범준범준범준범준범준|고고고고고고고고"}, delimiter = '|' )
  @DisplayName("성과 이름의 길이가 옳바르지 않을 경우 고객을 생성할 수 없다.")
  void create_Customer_Fail(String firstName, String lastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);

    assertThatThrownBy(em::flush)
            .isInstanceOf(DataException.class);
    log.info("고객 생성: {}, {}, {}", customer.getId(), customer.getFirstName(), customer.getLastName());
  }

}
