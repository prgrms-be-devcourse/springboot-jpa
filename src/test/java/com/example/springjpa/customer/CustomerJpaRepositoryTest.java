package com.example.springjpa.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@DisplayName("기본키 생성 전략이 SEQUENCE 일 때 JpaRepository 를 사용하여 테스트한다")
class CustomerJpaRepositoryTest {

	@Autowired
	private CustomerJpaRepository customerRepository;

	private Customer customer1;

	@BeforeEach
	void setUp() {
		this.customer1 = new Customer("lee", "noo");
		customerRepository.save(customer1);
	}

	@AfterEach
	void tearDown() {
		log.info("AfterEach 의 deleteAllInBatch 쿼리가 날아기기 전에 쓰기지연 저장소 내부 쿼리들이 날아간다");
		customerRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("save 시 INSERT 쿼리가 날아가기 전 이미 엔티티의 Id 값이 세팅된다")
	public void take_persistedCustomer() {
		Customer foundCustomer = customerRepository.findById(customer1.getId()).get();

		Assertions.assertThat(foundCustomer.getId()).isNotNull();
	}

	@Test
	@DisplayName("시퀀스 제네레이터 항당 크기 이하 횟수의 save 라면 db 에 대한 시퀀스 값 요청은 시퀀스 세팅을 위한 요청 두 번만 날아간다")
	public void multiple_save() {
		String firstName = "first";
		String lastName = "second";
		for (int i = 0; i < 50; i++) {
			customerRepository.save(
				createCustomer(firstName, lastName));
		}
	}

	@Test
	@DisplayName("DB 에 존재하지 않는 영속화된 엔티티의 값을 변경하면 flush 될 때 insert, update 쿼리가 날아간다")
	public void change_persistedCustomersName() {
		Customer savedCustomer = customerRepository.save(customer1);

		savedCustomer.changeFirstName("Han");
	}

	@Test
	@DisplayName("DB 에 insert 한 커스토머 삭제 결과 해당 엔티티는 영속성 컨텍스트와 DB 모두 에서 찾을 수 없다")
	public void delete_customer() {
		Customer savedCustomer = customerRepository.save(customer1);

		customerRepository.flush(); // insert

		customerRepository.delete(savedCustomer);
		customerRepository.flush(); // delete where

		boolean present = customerRepository.findById(
			savedCustomer.getId())
			.isPresent(); // select

		Assertions.assertThat(present).isFalse();
	}

	@Test
	@DisplayName("flush 이후에도 영속성 컨텍스트 내부 엔티티는 관리하에 있어 변경감지가 발생한다")
	public void given_changed_customer_when_changeAfterFlushing_thenUpdate() {
		Customer savedCustomer = customerRepository.save(customer1); // insert

		savedCustomer.changeFirstName("kim"); // update
		customerRepository.flush();
		logger.info("AFTER FLUSH");
		savedCustomer.changeFirstName("Han"); // update
	}

	private Customer createCustomer(String firstName, String LastName) {
		return new Customer(firstName, LastName);
	}
}