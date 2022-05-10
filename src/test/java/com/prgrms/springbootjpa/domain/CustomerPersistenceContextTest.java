package com.prgrms.springbootjpa.domain;

import com.prgrms.springbootjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;

@Slf4j
@SpringBootTest
public class CustomerPersistenceContextTest {

  @Autowired
  CustomerRepository repository;

  @Autowired
  EntityManagerFactory emf;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

}
