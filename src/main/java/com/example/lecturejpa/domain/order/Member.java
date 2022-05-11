package com.example.lecturejpa.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;

@Entity
@Getter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name ="name",nullable = false, length = 20 )
	private String name;
	@Column(name="nick_name",nullable = false, length = 30, unique = true)
	private String nickName;
	@Column(name ="age",nullable = false)
	private int age;
	@Column(name ="address",nullable = false)
	private String address;
	@Column(name ="description")
	private String description;

	@OneToMany(mappedBy = "member")
	private final List<Order> orders =  new ArrayList<>();

	protected Member() {
	}

	public Member(String name, String nickName, int age, String address, String description) {
		this.name = name;
		this.nickName = nickName;
		this.age = age;
		this.address = address;
		this.description = description;
	}

	public void addOrder(Order order) {
		order.setMember(this);
	}

}
