package com.kdt.lecturejpa.domain.member;

import java.util.ArrayList;
import java.util.List;

import com.kdt.lecturejpa.domain.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

	@OneToMany(mappedBy = "member")
	private final List<Order> orders = new ArrayList<Order>();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name", nullable = false, length = 10)
	private String name;

	@Column(name = "nick_name", nullable = false, length = 10)
	private String nickName;

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "description")
	private String description;

	@Builder
	public Member(String name, String nickName, int age, String address, String description) {
		this.name = name;
		this.nickName = nickName;
		this.age = age;
		this.address = address;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}
}
