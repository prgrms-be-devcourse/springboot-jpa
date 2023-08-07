package com.jpaweekly.domain.product.application;


import com.jpaweekly.domain.product.Product;
import com.jpaweekly.domain.product.dto.ProductCreateRequest;
import com.jpaweekly.domain.product.dto.ProductResponse;
import com.jpaweekly.domain.product.dto.ProductUpdateRequest;
import com.jpaweekly.domain.product.infrastructrue.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(ProductCreateRequest request) {
        Product product = request.toEntity();
        return productRepository.save(product).getId();
    }

    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ProductResponse.from(product);
    }

    public ProductResponse findProductByProductName(String productName) {
        Product product = productRepository.findByProductName(productName).orElseThrow(EntityNotFoundException::new);
        return ProductResponse.from(product);
    }

    public Page<ProductResponse> findProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.update(request.productName(), request.price());
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
