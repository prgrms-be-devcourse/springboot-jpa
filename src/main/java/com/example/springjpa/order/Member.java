package com.example.springjpa.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member extends CreationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;
	@Column(name = "nickName", nullable = false, length = 30, unique = true)
	private String nickName;
	@Column(name = "age", nullable = false)
	private int age;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "member",
		cascade = CascadeType.ALL
	)
	private List<Order> orders = new ArrayList<>();

	protected Member() {
	}

	public Member(String name, String nickName, int age, String address) {
		this.name = name;
		this.nickName = nickName;
		this.age = age;
		this.address = address;
	}

	public void addOrder(Order order) {
		order.orderedByMember(this);
	}
}
