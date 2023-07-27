package com.example.kdtjpa.domain;

import java.util.InputMismatchException;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	private long id;
	private String firstName;
	private String lastName;

	public Customer(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = validateName(firstName);
		this.lastName = validateName(lastName);
	}

	public Customer() {
	}

	private String validateName(String name) {
		boolean validator = Pattern.matches("^[a-zA-Z\\d]+", name);

		if(validator) {
			return name;
		}

		throw new InputMismatchException("Not validatedName");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
