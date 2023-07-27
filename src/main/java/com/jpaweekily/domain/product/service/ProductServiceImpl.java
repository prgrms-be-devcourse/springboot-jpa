package com.jpaweekily.domain.product.service;


import com.jpaweekily.domain.product.Product;
import com.jpaweekily.domain.product.dto.ProductCreateRequest;
import com.jpaweekily.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .productName(request.productName())
                .price(request.price())
                .build();

        productRepository.save(product);
    }
}
