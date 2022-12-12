package com.programmers.jpamission1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(name = "firstName", nullable = false, length = 8)
	@NotBlank
	@Size(min = 2, max = 10)
	private String firstName;

	@Column(name = "lastName", nullable = false, length = 8)
	@NotBlank
	@Size(min = 2, max = 10)
	private String lastName;

	public void updateFullName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
