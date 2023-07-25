package kr.co.springbootjpaweeklymission.member.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import kr.co.springbootjpaweeklymission.global.common.Regexp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", length = 4, nullable = false, unique = true)
    private Long memberId;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Pattern(regexp = Regexp.EMAIL_PATTERN)
    @Column(name = "email", length = 20, nullable = false, unique = true)
    private String email;

    @Column(name = "cell_phone", length = 15, nullable = false, unique = true)
    private String cellPhone;

    @Column(name = "address", length = 30, nullable = false)
    private String address;

    @Builder
    private Member(String name, String email, String cellPhone, String address) {
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.address = address;
    }
}
