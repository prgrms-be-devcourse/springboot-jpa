package com.programmers.jpapractice.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name = "first_name")
	@NotBlank
	@Size(max = 2)
	private String firstName;

	@Column(nullable = false, name = "last_name")
	@NotBlank
	@Size(max = 5)
	private String lastName;

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void changeFullName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

