package grepp.prgrms.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    void insert(){
        //given
        Long id = 1L;
        String firstName = "first";
        String lastName = "last";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customerRepository.save(customer);

        //when
        Customer found = customerRepository.findById(id).get();

        //then
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getFirstName()).isEqualTo(firstName);
        assertThat(found.getLastName()).isEqualTo(lastName);
    }

    @Test
    @Transactional
    void update(){
        //given
        Long id = 1L;
        String firstName = "first";
        String lastName = "last";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customerRepository.save(customer);

        //when
        Customer found = customerRepository.findById(1L).get();
        String newFirstName = "newFirst";
        String newLastName = "newLast";
        found.setFirstName(newFirstName);
        found.setLastName(newLastName);
        Customer foundAfterUpdate = customerRepository.findById(1L).get();

        //then
        assertThat(foundAfterUpdate.getId()).isEqualTo(id);
        assertThat(foundAfterUpdate.getFirstName()).isEqualTo(newFirstName);
        assertThat(foundAfterUpdate.getLastName()).isEqualTo(newLastName);

    }

    @Test
    @Transactional
    void delete(){
        //given
        Long id = 1L;
        String firstName = "first";
        String lastName = "last";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        Customer saved = customerRepository.save(customer);

        //when
        customerRepository.delete(saved);

        //then
        Optional<Customer> found = customerRepository.findById(id);

        assertThat(found).isEmpty();


    }
}