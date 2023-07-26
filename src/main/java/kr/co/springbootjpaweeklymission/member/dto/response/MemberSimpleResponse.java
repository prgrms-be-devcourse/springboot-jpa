package kr.co.springbootjpaweeklymission.member.dto.response;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record MemberSimpleResponse(
        String email,
        String name
) {
    public static MemberSimpleResponse toDto(Member member) {
        return MemberSimpleResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
