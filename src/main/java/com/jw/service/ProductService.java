package com.jw.service;

import com.jw.dto.ProductRequest;
import com.jw.dto.ProductsResponse;
import com.jw.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductsResponse getAllProducts() {
        return new ProductsResponse(productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(ProductRequest productRequest) {
        return productRepository.save(productMapper.toProduct(productRequest));
    }
}
