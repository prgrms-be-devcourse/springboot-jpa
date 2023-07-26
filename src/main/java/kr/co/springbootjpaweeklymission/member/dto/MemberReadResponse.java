package kr.co.springbootjpaweeklymission.member.dto;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberReadResponse(
        String email,
        String name,
        String cellPhone,
        String street,
        String detail,
        LocalDate createdAt,
        LocalDate modifiedAt
) {

    public static MemberReadResponse toDto(Member member) {
        return MemberReadResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .cellPhone(member.getCellPhone())
                .street(member.getStreet())
                .detail(member.getDetail())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }

}
