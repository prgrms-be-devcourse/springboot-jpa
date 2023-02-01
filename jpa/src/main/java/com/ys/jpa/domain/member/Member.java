package com.ys.jpa.domain.member;

import com.ys.jpa.domain.base.AbstractTimeColumn;
import com.ys.jpa.domain.order.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "이름은 빈 값일 수 없습니다")
    @Size(min = 2)
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull(message = "닉네임은 빈 값일 수 없습니다")
    @Size(min = 2)
    private String nickName;

    @ColumnDefault("1")
    @Column(nullable = false)
    @Min(1)
    private int age;

    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.changeMember(this);
    }

    public Member(String name, String nickName, int age, String address) {
        validateName(name);
        validateAge(age);
        validateNickName(nickName);
        validateAddress(address);
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
    }

    private void validateName(String name) {
        Assert.hasText(name, "이름은 빈 값이여서는 안됩니다.");
        Assert.isTrue(name.length() >= 2, "이름은 2자 이상이여야 합니다.");
    }

    private void validateNickName(String nickName) {
        Assert.hasText(nickName, "닉네임은 빈 값이여서는 안됩니다.");
        Assert.isTrue(nickName.length() >= 2, "이름은 2자 이상이여야 합니다.");
    }

    private void validateAge(int age) {
        Assert.isTrue(age >= 1, "나이는 1살 이상이여야 합니다.");
    }

    private void validateAddress(String address) {
        Assert.hasText(address, "주소는 빈 값이여서는 안됩니다.");
        Assert.isTrue(address.length() <= 255, "주소는 255글자 이하여야 합니다.");
    }

}
