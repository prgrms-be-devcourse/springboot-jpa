package kr.co.springbootjpaweeklymission.member.dto.response;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberDetailResponse(
        String email,
        String name,
        String cellPhone,
        String street,
        String detail,
        LocalDate createdAt,
        LocalDate modifiedAt
) {
    public static MemberDetailResponse toDto(Member member) {
        return MemberDetailResponse.builder()
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
