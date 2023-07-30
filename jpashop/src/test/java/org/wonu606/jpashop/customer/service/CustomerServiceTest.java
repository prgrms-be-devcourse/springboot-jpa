package org.wonu606.jpashop.customer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.wonu606.jpashop.customer.domain.CustomerRepository;
import org.wonu606.jpashop.customer.service.dto.CustomerCreateData;
import org.wonu606.jpashop.customer.service.dto.CustomerResult;
import org.wonu606.jpashop.customer.service.dto.CustomerResults;
import org.wonu606.jpashop.customer.service.exception.CustomerNotFoundException;

@DataJpaTest
class CustomerServiceTest {

    @Autowired
    private CustomerRepository repository;
    private CustomerService service;


    @BeforeEach
    void setup() {
        service = new CustomerService(repository);
    }

    @Test
    void create_유효한데이터인경우_저장된고객정보반환() {
        // Given
        CustomerCreateData creatingData = new CustomerCreateData("FirstName", "LastName");

        // When
        CustomerResult createdResult = service.create(creatingData);

        // Then
        assertThat(createdResult.id()).isNotNull();
        assertThat(createdResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(creatingData);
    }

    @Test
    void findById_존재하는아이디인경우_고객정보반환() {
        // Given
        CustomerCreateData creatingData = new CustomerCreateData("FirstName", "LastName");
        CustomerResult createdResult = service.create(creatingData);

        Long existingId = createdResult.id();

        // When
        CustomerResult foundResult = service.findById(existingId);

        // Then
        assertThat(foundResult.id()).isEqualTo(existingId);
        assertThat(foundResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdResult);
    }

    @Test
    void findById_존재하지않는아이디인경우_예외발생() {
        // Given
        Long nonExistingId = 0L;

        // When & Then
        assertThatThrownBy(() -> service.findById(nonExistingId))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void findAll_저장된모든고객들반환() {
        // Given
        CustomerCreateData creatingData = new CustomerCreateData("FirstName", "LastName");
        CustomerResult createdResult = service.create(creatingData);

        // When
        CustomerResults foundResults = service.findAll();

        // Then
        assertThat(foundResults.results())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(createdResult);
    }

    @Test
    void deleteById_존재하는아이디인경우_해당아이디고객삭제() {
        // Given
        CustomerCreateData creatingData = new CustomerCreateData("FirstName", "LastName");
        CustomerResult createdResult = service.create(creatingData);

        // When
        Long deletingId = createdResult.id();
        service.deleteById(deletingId);

        // Then
        assertThatThrownBy(() -> service.findById(deletingId))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
