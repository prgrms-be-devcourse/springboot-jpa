package com.example.jpaweekly.domain.product.repository;

import com.example.jpaweekly.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
