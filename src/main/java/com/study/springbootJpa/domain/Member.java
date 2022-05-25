package com.study.springbootJpa.domain;

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

@Table(name = "member")
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 10)
	private String name;

	@Column(nullable = false, length = 30, unique = true)
	private String nickName;

	@Column
	private int age;

	@Column(nullable = false)
	private String address;

	@Column
	private String description;


	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	private Member(String name, String nickName, int age, String address, String description) {
		this.name = name;
		this.nickName = nickName;
		this.age = age;
		this.address = address;
		this.description = description;
	}

	public static Member create(String name, String nickName, int age, String address, String description) {
		return new Member(name,nickName,age,address,description);
	}

	protected Member() {
	}

	public void addOrder(Order order) {
		order.setMember(this);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNickName() {
		return nickName;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
