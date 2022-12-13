package com.programmers.jpamission1.domain.order;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30, unique = true)
	private String nickName;

	@Column(nullable = false)
	private int age;

	@Column(name = "address", nullable = false,length = 30)
	private String address;

	@Column(name = "description", nullable = true,length = 30)
	private String description;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();

	public Member(String name, String nickName, int age, String address) {
		this.name = name;
		this.nickName = nickName;
		this.age = age;
		this.address = address;
	}

	public void addOrder(Order order) {
		order.updateMember(this);
		orders.add(order);
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateNickName(String nickName) {
		this.nickName = nickName;
	}

	public void updateAge(int age) {
		this.age = age;
	}

	public void updateAddress(String address) {
		this.address = address;
	}

	public void updateDescription(String description) {
		this.description = description;
	}

	public void removeOrder(Order order) {
		this.orders.remove(order);
	}
}
