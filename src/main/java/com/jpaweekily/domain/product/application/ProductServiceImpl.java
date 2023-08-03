package com.jpaweekily.domain.product.application;


import com.jpaweekily.domain.product.Product;
import com.jpaweekily.domain.product.dto.ProductCreateRequest;
import com.jpaweekily.domain.product.infrastructrue.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .productName(request.productName())
                .price(request.price())
                .build();
        productRepository.save(product);
        return product.getId();
    }
}
