package com.jpaweekily.domain.product.application;

import com.jpaweekily.domain.product.dto.ProductCreateRequest;

public interface ProductService {
    Long createProduct(ProductCreateRequest request);
}
