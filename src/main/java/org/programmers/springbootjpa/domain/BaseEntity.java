package org.programmers.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
