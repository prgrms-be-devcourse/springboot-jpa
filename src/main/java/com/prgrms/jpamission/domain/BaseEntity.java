package com.prgrms.jpamission.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}

