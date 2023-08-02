package com.example.jpaweekly.domain.product.service;

import com.example.jpaweekly.domain.product.Product;
import com.example.jpaweekly.domain.product.dto.ProductCreateRequest;
import com.example.jpaweekly.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long createProduct(ProductCreateRequest request){
        Product product = Product.builder()
                .productName(request.productName())
                .price(request.price())
                .build();

        productRepository.save(product);

        return product.getId();
    }
}
