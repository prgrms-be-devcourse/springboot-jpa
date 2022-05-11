package com.part4.jpa2.domain.parent.embeddedid;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class ParentIdv2 implements Serializable {
    private String id1;
    private String id2;
}
