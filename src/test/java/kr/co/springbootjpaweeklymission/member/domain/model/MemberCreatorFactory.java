package kr.co.springbootjpaweeklymission.member.domain.model;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;

public class MemberCreatorFactory {
    public static Member createMember() {
        return Member.builder()
                .name("testName")
                .email("example@domain.top")
                .cellPhone("010-0000-0000")
                .address(Address.builder()
                        .street("00도 00시")
                        .build())
                .build();
    }

    public static Member createMember(String email) {
        return Member.builder()
                .name("testName")
                .email(email)
                .cellPhone("010-0000-0000")
                .address(Address.builder()
                        .street("00도 00시")
                        .build())
                .build();
    }

    public static Member createMember(String email, String cellPhone, Address address) {
        return Member.builder()
                .name("testName")
                .email(email)
                .cellPhone(cellPhone)
                .address(address)
                .build();
    }

    public static MemberCreatorRequest createMemberCreatorRequest() {
        return MemberCreatorRequest.builder()
                .name("testName")
                .email("example@domain.top")
                .cellPhone("010-0000-0000")
                .street("00도 00시")
                .build();
    }

    public static MemberCreatorRequest createMemberCreatorRequest(String name, String email, String cellPhone) {
        return MemberCreatorRequest.builder()
                .name(name)
                .email(email)
                .cellPhone(cellPhone)
                .street("00도 00시")
                .build();
    }
}
