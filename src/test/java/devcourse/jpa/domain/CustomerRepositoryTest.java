package devcourse.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void save() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seonho");
        customer.setLastName("Kim");

        // when
        customerRepository.save(customer);

        // then
        Customer entity = customerRepository.findById(1L).get();
        log.info("{} {}", entity.getLastName(), entity.getFirstName());
    }
}