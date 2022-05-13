package com.example.springjpa.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
	name = "CUSTOMER_SEQ_GENERATOR",
	sequenceName = "CUSTOMER_SEQ",
	initialValue = 1, allocationSize = 50)
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
		generator = "CUSTOMER_SEQ_GENERATOR")
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void changeFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void changeLastName(String lastName) {
		this.lastName = lastName;
	}
}
