package com.jpaweekly.domain.product.infrastructrue;

import com.jpaweekly.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);
}
