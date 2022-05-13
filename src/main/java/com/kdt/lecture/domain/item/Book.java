package com.kdt.lecture.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item{

    private String writer;
}
