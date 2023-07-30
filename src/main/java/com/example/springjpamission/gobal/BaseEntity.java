package com.example.springjpamission.gobal;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
