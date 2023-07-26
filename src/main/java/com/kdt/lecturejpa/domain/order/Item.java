package com.kdt.lecturejpa.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="item")
@Getter
@Setter
public class Item {

	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private Long id;

	private int price;
	private int stockQuantity;
}
