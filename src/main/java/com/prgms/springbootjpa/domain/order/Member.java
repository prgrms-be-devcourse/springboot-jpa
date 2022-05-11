package com.prgms.springbootjpa.domain.order;

import com.prgms.springbootjpa.domain.order.vo.Name;
import com.prgms.springbootjpa.domain.order.vo.NickName;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private NickName nickName;

    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    private String description;
}
