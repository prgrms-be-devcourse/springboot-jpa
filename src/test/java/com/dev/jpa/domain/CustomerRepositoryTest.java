package com.dev.jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
@DisplayName("CustomerRepository CRUD Test")
class CustomerRepositoryTest {

    Logger logger = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Nested
    @DisplayName("save()를 실행하면")
    class save {

        @Test
        @DisplayName("DB에 customer이 저장된다.")
        void create() {
            Customer customer = new Customer();
            customer.setId(1L);
            customer.setFirstName("yeji");
            customer.setLastName("J");

            customerRepository.save(customer);

            Customer saveCustomer = customerRepository.findById(1L).get();
            assertThat(saveCustomer.getFirstName()).isEqualTo(customer.getFirstName());
            assertThat(saveCustomer.getLastName()).isEqualTo(customer.getLastName());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("여러명의 고객을 save한 뒤 findAll()을 실행하면")
    class find {

        Stream<Arguments> createCustomers() {
            return Stream.of(
                    Arguments.of(new Customer(1L, "a", "A"), 1),
                    Arguments.of(new Customer(2L, "b", "B"), 2),
                    Arguments.of(new Customer(3L, "c", "C"), 3)
            );
        }

        @ParameterizedTest
        @MethodSource("createCustomers")
        @DisplayName("저장했던 모든 고객 리스트가 불러와진다.")
        void findAll(Customer customer, int size) {
            customerRepository.save(customer);

            List<Customer> customers = customerRepository.findAll();

            assertThat(customers).hasSize(size);
            List<Long> idList = customers.stream().map(Customer::getId).collect(Collectors.toList());
            assertThat(idList, hasItem(customer.getId()));
        }
    }

    @Nested
    @DisplayName("DB에 존재하는 데이터를 save()할 경우")
    class update {
        @Test
        @DisplayName("기존 정보가 update 된다.")
        void update() {
            Customer customer = new Customer(1L, "kim", "min");
            customerRepository.save(customer);
            customer.setLastName("hee");

            customerRepository.save(customer);

            Customer findCustomer = customerRepository.findById(1L).get();
            int size = customerRepository.findAll().size();
            assertThat(size).isOne();
            assertThat(findCustomer.getLastName()).isEqualTo("hee");
        }
    }

    @Test
    @DisplayName("deleteAll()을 실행하면 모든 DB 데이터가 지워진다.")
    void deleteAll() {
        customerRepository.save(new Customer());

        customerRepository.deleteAll();

        int resultSize = customerRepository.findAll().size();
        assertThat(resultSize).isZero();
    }
}