package com.prgrms.lec_jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    private Member(MemberBuilder builder) {

        super(LocalDateTime.now(), builder.createdBy);
        this.name = builder.name;
        this.nickName = builder.nickName;
        this.age = builder.age;
        this.address = builder.address;
        this.description = builder.description;
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

    public static class MemberBuilder {

        private String name;
        private String nickName;
        private int age;
        private String address;
        private String description;
        private String createdBy;

        public MemberBuilder name(String value) {

            this.name = value;

            return this;
        }

        public MemberBuilder nickName(String value) {

            this.nickName = value;

            return this;
        }

        public MemberBuilder age(int value) {

            this.age = value;

            return this;
        }

        public MemberBuilder address(String value) {

            this.address = value;

            return this;
        }

        public MemberBuilder description(String value) {

            this.description = value;

            return this;
        }

        public MemberBuilder createdBy(String value) {

            this.createdBy = value;

            return this;
        }

        public Member build() {

            return new Member(this);
        }
    }

    public static MemberBuilder builder() {

        return new MemberBuilder();
    }
}
