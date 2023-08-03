package com.jpaweekly.domain.product.infrastructrue;

import com.jpaweekly.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
