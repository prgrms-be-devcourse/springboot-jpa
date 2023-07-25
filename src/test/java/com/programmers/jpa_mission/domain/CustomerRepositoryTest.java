package com.programmers.jpa_mission.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void 저장_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");

        //when
        Customer saved = repository.save(customer);

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 조회_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);
        long id = saved.getId();

        //when
        Customer result = repository.findById(id).get();

        //then
        assertThat(result.getId()).isEqualTo(saved.getId());
    }

    @Test
    void 조회_실패_테스트() {
        //when & then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 수정_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        Customer saved = repository.save(customer);

        //when
        saved.update("ChulBeom");

        //then
        Customer selected = repository.findById(saved.getId()).get();
        assertThat(selected.getId()).isEqualTo(saved.getId());
        assertThat(selected.getFirstName()).isNotEqualTo(saved.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(saved.getLastName());
    }

    @Test
    void 삭제_성공_테스트() {
        //given
        Customer customer = new Customer("BeomChul", "Shin");
        repository.save(customer);
        List<Customer> savedList = repository.findAll();

        //when
        repository.deleteAll();

        //then
        List<Customer> deletedList = repository.findAll();
        assertThat(deletedList.size()).isNotEqualTo(savedList.size());
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
