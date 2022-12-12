package com.programmers.jpapractice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

