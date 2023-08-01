package org.wonu606.jpashop.item.domain;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    protected Book() {
    }

    public Book(Long id, Integer price, Integer stockQuantity, String author, String isbn) {
        super(id, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(Integer price, Integer stockQuantity, String author, String isbn) {
        super(null, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
