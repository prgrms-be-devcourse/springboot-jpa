package com.programmers.app.domain.order.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.programmers.app.domain.BaseEntity;
import com.programmers.app.domain.order.Order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30, unique = true)
	private String nickName;

	private int age;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "description", nullable = true)
	private String description;

	@OneToMany(mappedBy = "member")
	private final List<Order> orders = new ArrayList<>();

	@Builder
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
