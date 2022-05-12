package com.part4.jpa2.domain.parent.embeddedid;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Parentv2 {
    @EmbeddedId
    private ParentIdv2 id;
}
