package kr.co.springbootjpaweeklymission.member.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import kr.co.springbootjpaweeklymission.global.common.BaseTimeEntity;
import kr.co.springbootjpaweeklymission.global.common.Regexp;
import kr.co.springbootjpaweeklymission.member.domain.model.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", length = 4, nullable = false, unique = true)
    private Long memberId;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Pattern(regexp = Regexp.EMAIL_PATTERN)
    @Column(name = "email", length = 20, nullable = false, unique = true)
    private String email;

    @Pattern(regexp = Regexp.KOREA_CELL_PHONE_PATTERN)
    @Column(name = "cell_phone", length = 15, nullable = false, unique = true)
    private String cellPhone;

    @Embedded
    private Address address;

    @Builder
    private Member(String name, String email, String cellPhone, Address address) {
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.address = address;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getDetail() {
        return address.getDetail();
    }
}
