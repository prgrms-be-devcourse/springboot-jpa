package org.prgms.jpa.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, unique = true, length = 30)
	private String nickName;

	private int age;

	private String address;

	@Lob
	private String description;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	// 연관관계 편의 메서드 START

	public void setOrder(Order order) {
		order.setMember(this);
	}

	// 연관관계 편의 메서드 END
}
