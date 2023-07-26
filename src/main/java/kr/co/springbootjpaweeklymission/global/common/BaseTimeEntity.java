package kr.co.springbootjpaweeklymission.global.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", length = 20, nullable = false, updatable = false)
    protected LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", length = 20)
    protected LocalDate modifiedAt;
}
