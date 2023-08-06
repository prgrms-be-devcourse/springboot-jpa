package com.example.jpaweekly.domain.product.service;

import com.example.jpaweekly.domain.product.dto.ProductCreateRequest;

public interface ProductService {
    Long createProduct(ProductCreateRequest request);
}
