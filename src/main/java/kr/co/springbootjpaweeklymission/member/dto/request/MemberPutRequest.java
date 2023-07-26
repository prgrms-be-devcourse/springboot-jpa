package kr.co.springbootjpaweeklymission.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.springbootjpaweeklymission.global.common.Regexp;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.Address;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPutRequest {
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Pattern(regexp = Regexp.EMAIL_PATTERN, message = "이메일을 형식에 맞게 입력하세요.")
    private String email;

    @Pattern(regexp = Regexp.KOREA_CELL_PHONE_PATTERN, message = "핸드폰 번호 형식에 맞게 입력하세요.")
    private String cellPhone;

    @NotBlank(message = "지번 주소를 입력하세요.")
    private String street;

    private String details = "";

    public Member toMember() {
        final Address address = Address.builder()
                .street(this.street)
                .detail(this.details)
                .build();

        return Member.builder()
                .name(this.name)
                .cellPhone(this.cellPhone)
                .email(this.email)
                .address(address)
                .build();
    }
}
