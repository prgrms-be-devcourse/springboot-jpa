package com.jpaweekly.domain.product.application;

import com.jpaweekly.domain.product.dto.ProductCreateRequest;

public interface ProductService {
    Long createProduct(ProductCreateRequest request);
}
