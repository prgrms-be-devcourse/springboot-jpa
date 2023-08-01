package com.programmers.springbootjpa.dto.response;

import com.programmers.springbootjpa.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor
public class ResponseDto {

    private Long memberId;
    private String name;
    private String nickName;
    private String address;

    @Builder
    private ResponseDto(Long memberId, String name, String nickName, String address) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.address = address;
    }

    public static ResponseDto fromEntity(Member member) {
        return ResponseDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .address(member.getAddress())
                .build();
    }
}
