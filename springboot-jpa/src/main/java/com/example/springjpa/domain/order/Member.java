package com.example.springjpa.domain.order;

import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private static final int AGE_MIN = 0;
    @Column(nullable = false, length = 50, unique = true)
    private String nickName;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String address;
    private String description;
    @Column(nullable = false, length = 50)
    private String name;
    @Embedded
    private Orders orders;
    private static final int NAME_MAX = 50;
    private static final String AGE_VALIDATE_ERR = "나이는 음수가 될 수 없습니다";
    private static final String NAME_VALIDATE_ERR = "이름(닉네임)은" + NAME_MAX + "자를 넘을 수 없습니다";
    private static final String ADDRESS_VALIDATE_ERR = "주소는 null을 허용하지 않습니다";

    protected Member() {
    }

    public Member(Long id, String name, String nickName, int age, String address, String description, Orders orders) {
        nameValidate(name);
        nameValidate(nickName);
        addressValidate(address);
        ageValidate(age);
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
        this.orders = orders == null ? new Orders() : orders;
    }

    private void addressValidate(String address) {
        Assert.isTrue(address != null, ADDRESS_VALIDATE_ERR);
    }

    private void nameValidate(String name) {
        Assert.isTrue(name != null, NAME_VALIDATE_ERR);
        Assert.isTrue(name.length() <= NAME_MAX, NAME_VALIDATE_ERR);
    }

    private void ageValidate(int age) {
        Assert.isTrue(age >= AGE_MIN, AGE_VALIDATE_ERR);
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

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public Orders getOrders() {
        return this.orders;
    }

    public static class MemberBuilder {
        private Long id;
        private String name;
        private String nickName;
        private int age;
        private String address;
        private String description;
        private Orders orders;

        MemberBuilder() {
        }

        public MemberBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder nickName(final String nickName) {
            this.nickName = nickName;
            return this;
        }

        public MemberBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public MemberBuilder address(final String address) {
            this.address = address;
            return this;
        }

        public MemberBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public MemberBuilder orders(final Orders orders) {
            this.orders = orders;
            return this;
        }

        public Member build() {
            return new Member(this.id, this.name, this.nickName, this.age, this.address, this.description, this.orders);
        }

        public String toString() {
            return "MemberBuilder(id=" + this.id + ", name=" + this.name + ", nickName=" + this.nickName + ", age=" + this.age + ", address=" + this.address + ", description=" + this.description + ", orders=" + this.orders + ")";
        }
    }
}
