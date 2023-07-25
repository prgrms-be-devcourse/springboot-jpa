package kr.co.springbootjpaweeklymission.member.domain.model;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;

public class MemberCreatorFactory {
    public static Member createMember() {
        return Member.builder()
                .name("testName")
                .email("example@domain.top")
                .cellPhone("010-0000-0000")
                .address("00도 00시 00동 000동 0000호")
                .build();
    }

    public static Member createMember(String email, String cellPhone, String address) {
        return Member.builder()
                .name("testName")
                .email(email)
                .cellPhone(cellPhone)
                .address(address)
                .build();
    }
}
