package com.example.jpaweekly.domain.product.service;

import com.example.jpaweekly.domain.product.dto.ProductCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Test
    void createTest(){

        // Given
        ProductCreateRequest productCreateRequest = new ProductCreateRequest("아이스크림", 2000);

        // When
        Long productId = productService.createProduct(productCreateRequest);

        //Then
        assertThat(productId).isNotNull();
    }
}
