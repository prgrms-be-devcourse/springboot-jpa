package com.programmers.jpa_mission.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void save() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");

        //when
        Customer saved = repository.save(customer);

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(customer);
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void saveAndFlush() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");

        //when
        Customer saved = repository.saveAndFlush(customer);

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(customer);
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void saveAll() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer customer2 = new Customer("Beom", "Kim");
        List<Customer> list = List.of(customer, customer2);

        //when
        List<Customer> customers = repository.saveAll(list);

        //then
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0 where c1_0.id=?
    @Test
    void findById() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);
        long id = saved.getId();

        //when
        Customer result = repository.findById(id).get();

        //then
        assertThat(result.getId()).isEqualTo(saved.getId());
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0 where c1_0.id=?
    //    Hibernate: update customers set first_name=?,last_name=? where id=?

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0 where c1_0.id=?
    @Test
    void update1() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);

        //when
        Customer updated = saved;
        updated.update("ChulBeom");

        repository.save(updated);

        //then
        Customer selected = repository.findById(updated.getId()).get();
        assertThat(selected.getId()).isEqualTo(updated.getId());
        assertThat(selected.getFirstName()).isEqualTo(updated.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(updated.getLastName());
    }

    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0
    //    Hibernate: select next value for customers_SEQ
    //    왜 insert가 없을까
    @Test
    @Transactional
    void update2() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);//영속화, flush x

        //when
        Customer updated = saved;
        updated.update("ChulBeom");
        repository.save(updated);//영속화, flush x
        em.flush();

        //then
        Customer selected = repository.findById(updated.getId()).get();//1차 캐시 조회
        assertThat(selected.getId()).isEqualTo(updated.getId());
        assertThat(selected.getFirstName()).isEqualTo(updated.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(updated.getLastName());
    }

    @Test
    void 단일_삭제_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);
        List<Customer> savedList = repository.findAll();

        //when
        repository.delete(saved);

        //then
        List<Customer> deletedList = repository.findAll();
        assertThat(deletedList.size()).isEqualTo(savedList.size() - 1);
    }

    @Test
    void 전체_삭제_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer customer2 = new Customer("BeomChulz", "Shinz");
        repository.saveAll(List.of(customer, customer2));

        //when
        repository.deleteAll();

        //then
        List<Customer> deletedList = repository.findAll();
        assertThat(deletedList.size()).isEqualTo(0);
    }

    @Test
    void 조회_실패_테스트() {
        //when & then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 삭제_실패_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);
        long id = saved.getId();

        //when
        repository.deleteAll();

        //then
        assertThatThrownBy(() -> repository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}
