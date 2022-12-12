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
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestEntityManager
@ActiveProfiles("test")
public class DataJpaPersistenceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("비영속 상태인 customer를 영속 상태로 변경하여 1차 캐시를 통해 조회한다.")
    void save() {
        Customer customer = getCustomer();

        // 1.Select_customer :: DB
        // 2.customers :: Managed
        // 3.Insert_customer :: Write_Behind
        customerRepository.save(customer);

        // 4.Select_customer :: First_Level_Cache
        Customer findCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않은 고객입니다."));

        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("영속 상태인 customer를 1차 캐시를 통해 조회한다.")
    void find_managed_customer() {
        Customer customer = getCustomer();

        // 1.Select_customer :: DB
        // 2.customer :: Managed
        // 3.Insert_customer :: Write_Behind
        // 4.Insert_customer :: DB
        customerRepository.saveAndFlush(customer);

        // 5.Select_customer :: First_Level_Cache
        Customer findCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않은 고객입니다."));

        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("영속 상태인 customer를 수정한다.")
    void update_managed_customer() {
        Customer customer = getCustomer();

        // 1.customer :: Managed
        // 2.Insert_customer :: Write_Behind
        em.persist(customer);
        // 3.Insert_customer :: DB
        em.flush();
        em.clear();

        // 1.Select_customer :: DB
        // 2.customers :: Managed
        Customer findCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않은 고객입니다."));

        // 3.Update_customer :: Write_Behind
        findCustomer.changeName("updatedName");
        findCustomer.changeAddress("updatedAddress");
        findCustomer.changeAge(99);

        // 4.Update_customer :: DB
        customerRepository.flush();
        customerRepository.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않은 고객입니다."));

        assertThat(findCustomer)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "updatedName")
                .hasFieldOrPropertyWithValue("address", "updatedAddress")
                .hasFieldOrPropertyWithValue("age", 99);
    }

    @Test
    @DisplayName("비영속 상태인 customer 전체 삭제, 전체 저장할 때 1차 캐시에서 잘못된 정보를 가져와 예외가 발생한다.")
    void delete_all_save_all_exception() {
        // 1.customers :: Managed
        // 2.Insert_customer :: Write_Behind
        customerDummy().forEach(em::persist);
        // 3.Insert_customer :: DB
        em.flush();
        em.clear();

        // 1.Select_customers :: DB
        // 2.customers :: Managed
        // 3.Delete_customers :: Write_Behind
        customerRepository.deleteAll();

        // 4.Select_customers :: First_Level_Cache
        // 5.Delete_customers :: DB
        // 6.Insert_customers :: Write_Behind
        customerRepository.saveAll(customerDummy());

        // 7.Select_customers :: DB
        List<Customer> findCustomers = customerRepository.findAll();

        // 8.Insert_customers :: DB
        assertThat(findCustomers)
                .usingRecursiveComparison()
                .isEqualTo(customerDummy());
    }

    @Test
    @DisplayName("비영속 상태인 customer 모든 회원 삭제, 각각 회원을 조회 후 모든 회원 저장에 성공한다.")
    void delete_all_find_each_save_all_exception() {
        // 1.Select_customer :: First_Level_Cache
        // 2.customers :: Managed
        // 3.Insert_customer :: Write_Behind
        customerDummy().forEach(em::persist);
        // 4.Insert_customers :: DB
        em.flush();
        em.clear();

        // 1.Select_customers :: DB
        // 2.customers :: Managed
        // 3.Delete_customers :: Write_Behind
        customerRepository.deleteAll();

        // 4.Select_customer :: First_Level_Cache
        customerDummy().forEach(customer -> customerRepository.findById(customer.getId()));

        // 5.Select_customers :: First_Level_Cache
        // 6.Delete_customers :: DB
        // 7.Select_customers :: DB
        // 8.Insert_customers :: Write_Behind
        // 9.customers :: Managed
        List<Customer> savedCustomers = customerRepository.saveAll(customerDummy());

        // 10.Select_customers :: First_Level_Cache
        assertThat(savedCustomers)
                .usingRecursiveComparison()
                .isEqualTo(customerDummy());
    }

    @Test
    @DisplayName("비영속 상태인 customer 모든 회원 삭제, 모든 회원을 조회 후 모든 회원 저장에 성공한다.")
    void delete_all_find_all_save_all_exception() {
        // 1.Select_customer :: First_Level_Cache
        // 2.customers :: Managed
        // 3.Insert_customer :: Write_Behind
        customerDummy().forEach(em::persist);
        // 4.Insert_customers :: DB
        em.flush();
        em.clear();

        // 1.Select_customers :: DB
        // 2.Delete_customers :: Write_Behind
        customerRepository.deleteAll();

        // 3.Delete_customers :: DB
        // 4.Select_customers :: DB
        customerRepository.findAll();

        // 5.Select_customer :: DB
        // 6.Insert_customer :: Write_Behind
        List<Customer> savedCustomers = customerRepository.saveAll(customerDummy());

        assertThat(savedCustomers)
                .usingRecursiveComparison()
                .isEqualTo(customerDummy());
    }

    @Test
    @DisplayName("비영속 상태인 모든 회원 삭제, 모든 회원 수정 후 모든 회원 검색 결과가 []로 성공한다.")
    void delete_all_update_each_find_all_exception() {
        // 1.Select_customers :: DB
        // 2.customers :: Managed
        // 3.Insert_customers :: Write_Behind
        customerDummy().forEach(em::persist);
        // 4.Insert_customers :: DB
        em.flush();
        em.clear();

        // 1.Select_customer :: DB
        List<Customer> findCustomers = customerDummy()
                .stream()
                .map(customer -> customerRepository.findById(customer.getId())
                        .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않은 고객입니다." + customer)))
                .toList();

        // 2.Select_customers :: DB
        // 3.Delete_customers :: Write_Behind
        customerRepository.deleteAll();

        // 4.Select_customer :: First_Level_Cache
        // 5.Update_customer :: Write_Behind
        findCustomers.forEach(customer -> customer.changeName("수정된 이름"));

        // 6.Delete_customer :: DB
        customerRepository.flush();

        // 7.Select_customers :: DB
        List<Customer> all = customerRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("비영속 상태인 customer 모든 회원 삭제, 모든 회원 조회, 모든 회원 저장 후 모든 회원 각각 검색에 성공한다.")
    void delete_all_find_all_save_all_then_find_all_each() {
        // 1.Select_customers :: DB
        // 2.customers :: Managed
        // 3.Insert_customers :: Write_Behind
        customerDummy().forEach(em::persist);
        // 4.Insert_customers :: DB
        em.flush();
        em.clear();

        // 1.Select_customers :: DB
        // 2.Delete_customers :: Write_Behind
        customerRepository.deleteAll();

        // 3.Delete_customers :: DB
        // 4.Select_customers :: DB
        customerRepository.findAll();

        // 5.Select_customers :: DB
        customerRepository.saveAll(customerDummy());

        // 6.Insert_customers :: DB
        // 7.Select_customers :: First_Level_Cache
        List<Customer> findCustomers = customerDummy().stream()
                .map(customer -> customerRepository.findById(customer.getId()).get())
                .toList();

        assertThat(findCustomers)
                .usingRecursiveComparison()
                .isEqualTo(customerDummy());
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
