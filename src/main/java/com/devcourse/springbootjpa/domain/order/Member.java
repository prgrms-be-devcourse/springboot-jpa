package com.devcourse.springbootjpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30, unique = true)
	private String nickname;

	@Column(nullable = false)
	private int age;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String description;

	@Builder
	public Member(String name, String nickname, int age, String address, String description) {
		this.name = name;
		this.nickname = nickname;
		this.age = age;
		this.address = address;
		this.description = description;
	}
}
