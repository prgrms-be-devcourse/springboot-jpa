package com.jpaweekily.domain.product.infrastructrue;

import com.jpaweekily.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
