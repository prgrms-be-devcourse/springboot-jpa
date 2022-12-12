package com.devcourse.mission.domain.customer.repository;

import com.devcourse.mission.domain.customer.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestEntityManager
@ActiveProfiles("test")
public class JpaPersistenceTest {

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("비영속 상태인 customer를 DB를 통해 조회한다.")
    void find_detached_customer() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Insert_customer :: DB
        em.flush();

        // 3.Persistence_Context :: Clear
        em.clear();

        // 4.Select_customer :: DB
        // 5.customer :: Managed
        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("영속 상태인 customer는 1차 캐시를 통해 조회한다.")
    void find_managed_customer() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Select_customer :: First_Level_Cache
        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("비영속 상태인 customer 삭제 상태로 만들고 조회했을 때 null을 반환한다.")
    void delete_new_customer_then_find() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Insert_customer :: DB
        em.flush();

        // 3.Persistence_Context :: Clear
        // 4.customer :: Detach
        em.clear();

        // 5.Select_customer :: DB
        // 6.customer :: Managed
        Customer findCustomer = em.find(Customer.class, customer.getId());

        // 7.Select_customer :: First_Level_Cache
        // 8.Delete_customer :: Write_Behind
        // 9.customer :: Removed
        em.remove(findCustomer);

        // 10.Select_customer :: First_Level_Cache[REMOVED]
        Customer findAgainCustomer = em.find(Customer.class, customer.getId());

        // SUCCESS
        assertThat(findAgainCustomer).isNull();
    }

    @Test
    @DisplayName("영속 상태인 customer를 삭제 상태로 만들고 조회했을 때 null을 반환한다.")
    void delete_managed_customer_then_find() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Delete_customer :: First_Level_Cache
        // 3.Delete_customer :: Write_Behind
        em.remove(customer);

        // 4.Select_customer :: First_Level_Cache[REMOVED]
        Customer findAgainCustomer = em.find(Customer.class, customer.getId());

        // SUCCESS
        assertThat(findAgainCustomer).isNull();
    }

    @Test
    @DisplayName("비영속 상태인 customer들을 삭제하고 DB 조회했을 때 null을 반환한다.")
    void delete_all_new_customers_then_find() {
        // 1.customers :: Managed
        customerDummy().forEach(em::persist);

        // 2.Insert_customers :: DB
        em.flush();

        // 3.Persistence_Context :: Clear
        // 4.customer :: Detach
        em.clear();

        // 4.Select_customers :: DB
        List<Customer> findCustomers = customerDummy()
                .stream()
                .map(customer -> em.find(Customer.class, customer.getId()))
                .toList();

        // 5. Delete_customers :: Write_Behind
        for (Customer customer : findCustomers) {
            em.remove(customer);
        }

        // 6.Delete_customer :: DB
        em.flush();

        // 7.Persistence_Context :: Clear
        em.clear();

        // 8.Select_customers :: DB
        boolean isFindCustomersNull = findCustomers.stream()
                .map(customer -> em.find(Customer.class, customer.getId()))
                .allMatch(Objects::isNull);

        // SUCCESS
        assertThat(isFindCustomersNull).isTrue();
    }

    @Test
    @DisplayName("비영속 상태인 customer를 merge를 이용하여 수정한다.")
    void update_detached_customer() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Insert_customer :: DB
        em.flush();

        // 3.customer :: Detach
        em.clear();

        Customer updateCustomer = Customer.builder()
                .id(customer.getId())
                .name("newName")
                .age(1000)
                .address("new도시")
                .build();

        // 4.Select_customer :: DB
        // 5.updateCustomer :: Managed
        // 6.Update_customer :: Write_Behind
        em.merge(updateCustomer);

        // 7.Updated_customer :: DB
        em.flush();

        // 8.updateCustomer :: Detach
        em.clear();

        // 9.Select_customer
        Customer findCUstomer = em.find(Customer.class, customer.getId());

        // SUCCESS
        assertThat(updateCustomer)
                .usingRecursiveComparison()
                .isEqualTo(findCUstomer);
    }

    @Test
    @DisplayName("영속 상태인 customer를 수정한다.")
    void update_managed_customer() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        em.persist(customer);

        // 2.Insert_customer :: DB
        em.flush();

        // 4.Select_customer :: DB
        // 5.updateCustomer :: Managed
        // 6.Update_customer :: Write_Behind
        customer.changeAddress("수정된 주소입니다.");
        customer.changeName("수정된 이름입니다.");
        customer.changeAge(5);

        // 7.Update_customer :: DB
        em.flush();

        // 8.Select_customer :: First_Level_Cache
        Customer findCustomer = em.find(Customer.class, customer.getId());

        // SUCCESS
        assertThat(findCustomer)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("address", "수정된 주소입니다.")
                .hasFieldOrPropertyWithValue("name", "수정된 이름입니다.")
                .hasFieldOrPropertyWithValue("age", 5);
    }

    private Customer getCustomer() {
        return new Customer(1L, "박현서", "도시", 1);
    }

    private List<Customer> customerDummy() {
        return List.of(
                new Customer(1L, "박현서1", "도시1", 1),
                new Customer(2L, "박현서2", "도시2", 2),
                new Customer(3L, "박현서3", "도시3", 3),
                new Customer(4L, "박현서4", "도시4", 4),
                new Customer(5L, "박현서5", "도시5", 5)
        );
    }
}
