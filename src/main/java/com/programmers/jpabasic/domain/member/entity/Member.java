package com.programmers.jpabasic.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.programmers.jpabasic.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    @Column
    private int age;

    @Column(nullable = false)
    private String address;

    @Column(length = 100)
    private String description;
}
