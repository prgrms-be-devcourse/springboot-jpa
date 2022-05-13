package com.example.springjpa.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("기본키 생성 전략이 SEQUENCE 일 때")
class CustomerJpaRepositoryTest {
	@Autowired
	private CustomerJpaRepository customerRepository;

	private Customer customer1;

	@BeforeEach
	public void setUp() {
		this.customer1 = new Customer("lee", "noo");
	}

	@Test
	@DisplayName("save 시 INSERT 쿼리가 날아가지 않아도 엔티티의 Id 값이 세팅된다")
	public void take_persistedCustomer() {
		Customer savedCustomer = customerRepository.save(customer1);

		Assertions.assertThat(savedCustomer.getId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("시퀀스 제네레이터 항당 크기 이하 횟수의 save 라면 db 에 대한 시퀀스 값 요청은 초기요청만 날아간다")
	public void multiple_save() {
		String templateFirst = "a";
		String templateLast = "b";

		for (int i = 0; i < 5; i++) {
			templateFirst += "a";
			templateLast += "b";

			customerRepository.save(createCustomer(templateFirst, templateLast));
		}
	}

	@Test
	@DisplayName("save 결과 리턴되어오는 영속화된 customer 의 이름을 변경하여 flush 하면 update 쿼리가 날아간다")
	public void change_persistedCustomersName() {
		Customer savedCustomer = customerRepository.save(customer1);

		savedCustomer.changeFirstName("Han");

		customerRepository.flush();
	}

	private Customer createCustomer(String firstName, String LastName) {
		return new Customer(firstName, LastName);
	}
}