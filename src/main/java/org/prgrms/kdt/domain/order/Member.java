package org.prgrms.kdt.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Table(name = "member")
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30, unique = true)
	private String nickName;
	private int age;

	@Column(nullable = false)
	private String address;

	@Column(nullable = true)
	private String description;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	protected Member() {
	}

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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("name", name)
			.append("nickName", nickName)
			.append("age", age)
			.append("address", address)
			.append("description", description)
			.toString();
	}
}
