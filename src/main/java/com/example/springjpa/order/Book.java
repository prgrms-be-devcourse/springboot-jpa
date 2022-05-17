package com.example.springjpa.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Book")
public class Book extends Item{
	private String title;

	protected Book() {}
	public Book(int price, int stockQuantity, String title) {
		super(price, stockQuantity);
		this.title = title;
	}
}
