package com.prgrms.be.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 17)
    private String name;

    @NotBlank
    @Size(max = 12)
    @Column(unique = true)
    private String nickName;

    @Positive
    @Max(value = 200)
    private int age;

    @NotBlank
    private String address;

    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String address) {
        this(name, nickName, age, address, "");
    }

    public Member(String name, String nickName, int age, String address, String description) {
        checkArgument(!name.isBlank(), "이름이 비어있거나 공백으로만 이루어지면 안됩니다.", name);
        checkArgument(2 <= name.length() && name.length() <= 17, "이름은 2자 ~ 17자 범위에 속해야합니다.", name);
        checkArgument(!nickName.isBlank(), "닉네임이 비어있거나 공백으로만 이루어지면 안됩니다.", nickName);
        checkArgument(nickName.length() <= 12, "닉네임은 12자이하여야 합니다.", nickName);
        checkArgument(0 < age && age <= 200, "나이가 음수이거나 150세를 넘을 수 없습니다.", age);
        checkArgument(!address.isBlank(), "주소가 비어있거나 공백으로만 이루어지면 안됩니다.", address);

        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
