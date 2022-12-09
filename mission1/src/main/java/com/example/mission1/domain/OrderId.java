package com.example.mission1.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class OrderId implements Serializable {
    private String email;
    private String address;
}
