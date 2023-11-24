package org.devcourse.assignment.repository;

import org.devcourse.assignment.domain.customer.Address;
import org.devcourse.assignment.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @DisplayName("고객을 생성할 수 있다.")
    @Test
    void save() {
        // given
        Address address = createAddress("서울", "동일로 179길", "123123");

        Customer customer = Customer.builder()
                .name("의진")
                .nickName("jinny")
                .age(23)
                .address(address)
                .description("안녕하세요:)")
                .build();

        // when
        Customer saved = customerRepository.save(customer);

        // then
        Optional<Customer> found = customerRepository.findById(saved.getId());
        assertDoesNotThrow(() -> found.get());
        assertThat(found.get())
                .extracting("name", "nickName", "age", "address", "description")
                .containsExactly(customer.getName(), customer.getNickName(), customer.getAge(), customer.getAddress(), customer.getDescription());
    }

    @DisplayName("고객 ID로 고객 정보를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        Customer customer = createCustomer("의진", "jinny");
        customerRepository.save(customer);

        // when
        Optional<Customer> found = customerRepository.findById(customer.getId());

        // then
        assertDoesNotThrow(() -> found.get());
        assertThat(found.get())
                .extracting("name", "nickName", "age", "description")
                .containsExactly(customer.getName(), customer.getNickName(), customer.getAge(), customer.getDescription());
    }

    @DisplayName("고객 정보를 수정할 수 있다.")
    @Test
    void update() {
        // given
        Customer customer = createCustomer("의진", "jinny");
        Customer saved = customerRepository.save(customer);

        assertThat(saved.getAddress()).isNull();

        Address address = createAddress("서울", "동일로 179길", "123123");

        // when
        saved.saveAddress(address);

        // then
        Customer found = customerRepository.findById(saved.getId()).get();
        assertThat(found.getAddress()).isNotNull();
        assertThat(found.getAddress())
                .extracting("city", "street", "zipCode")
                .containsExactly(address.city(), address.street(), address.zipCode());
    }

    @DisplayName("고객을 삭제할 수 있다.")
    @Test
    void deleteById() {
        // given
        Customer customer = createCustomer("의진", "jinny");
        customerRepository.save(customer);

        assertThat(customerRepository.findAll()).hasSize(1);

        // when
        customerRepository.deleteById(customer.getId());

        // then
        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertThatThrownBy(() -> found.get());
    }

    private static Address createAddress(String city, String street, String zipCode) {
        return Address.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .build();
    }

    private static Customer createCustomer(String name, String nickName) {
        return Customer.builder()
                .name(name)
                .nickName(nickName)
                .description("안녕하세요:)")
                .build();
    }
}
