package com.devcourse.springbootjpa.domain;

import java.util.Objects;

import com.devcourse.springbootjpa.exception.InvalidCustomerException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "customers")
@Getter
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(length = 20, nullable=false)
	private String firstName;

	@Column(length = 10, nullable=false)
	private String lastName;

	protected Customer() {
	}

	@Builder
	public Customer(long id, String firstName, String lastName) {
		validateName(firstName, lastName);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	void validateName(String firstName, String lastName) {
		if (firstName == null || firstName.isEmpty() || firstName.isBlank() || firstName.length() > 10) {
			throw new InvalidCustomerException("이름을 1~10자 사이로 입력해주세요");
		}
		if (lastName == null || lastName.isEmpty() || lastName.isBlank() || lastName.length() > 5) {
			throw new InvalidCustomerException("성을 1~5자 사이로 입력해주세요");
		}
	}

	public void changeFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void changeLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Customer customer = (Customer)o;
		return (id == customer.id) &&
				(Objects.equals(firstName, customer.firstName)) &&
				(Objects.equals(lastName, customer.lastName));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName);
	}

}
