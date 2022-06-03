package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.prgrms.springbootjpa.global.util.Validator.*;

@Entity
@Table(name = "member")
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name="nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    protected Member() {
    }

    public Member(String name, String nickName, int age, String address, String description) {
        validateMemberField(name, nickName, age, address);
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public Member(String name, String nickName, int age, String address) {
        validateMemberField(name, nickName, age, address);
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }

    private void validateMemberField(String name, String nickName, int age, String address) {
        if(!StringUtils.hasText(name)) {
            throw new WrongFieldException("Member.name", name, "please input name");
        }

        if(!validateFieldLength(name, 1, 30)) {
            throw new WrongFieldException("Member.name", name, "1 <= name <= 30");
        }

        if(!StringUtils.hasText(nickName)) {
            throw new WrongFieldException("Member.nickName", nickName, "please input nickName");
        }

        if(!validateFieldLength(nickName, 1, 30)) {
            throw new WrongFieldException("Member.nickName", nickName, "1 <= nickName <= 30");
        }

        if(!validateNumberSize(age, 1)) {
            throw new WrongFieldException("Member.age", 1, "1 <= age");
        }

        if(!StringUtils.hasText(address)) {
            throw new WrongFieldException("Member.address", address, "please input address");
        }

        if(!validateFieldLength(address, 1, 200)) {
            throw new WrongFieldException("Member.address", address, "1 <= address <= 30");
        }
    }
}
