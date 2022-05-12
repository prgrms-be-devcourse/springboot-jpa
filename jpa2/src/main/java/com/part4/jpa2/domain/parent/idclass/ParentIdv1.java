package com.part4.jpa2.domain.parent.idclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Deprecated
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParentIdv1 implements Serializable {
    private String id1;
    private String id2;
}
