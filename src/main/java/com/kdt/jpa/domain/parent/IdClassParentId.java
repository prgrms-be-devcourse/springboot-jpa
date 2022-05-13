package com.kdt.jpa.domain.parent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IdClassParentId implements Serializable {
    private String id1;
    private String id2;
}
