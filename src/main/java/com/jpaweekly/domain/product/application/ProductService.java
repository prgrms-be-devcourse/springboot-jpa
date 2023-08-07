package com.jpaweekly.domain.product.application;

import com.jpaweekly.domain.product.dto.ProductCreateRequest;
import com.jpaweekly.domain.product.dto.ProductResponse;
import com.jpaweekly.domain.product.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Long createProduct(ProductCreateRequest request);

    ProductResponse findProductById(Long id);

    ProductResponse findProductByProductName(String productName);

    Page<ProductResponse> findProducts(Pageable pageable);

    void updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);
}
