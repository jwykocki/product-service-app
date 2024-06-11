package com.jw.service;

import com.jw.dto.ProductRequest;
import com.jw.dto.ProductResponse;
import com.jw.dto.ProductsResponse;
import com.jw.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product saveProduct(ProductRequest productRequest) {
        return productRepository.save(productMapper.toProduct(productRequest));
    }

    public ProductsResponse getAllProducts() {
        return new ProductsResponse(productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList());
    }

    public ProductResponse getProductById(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }

    public ProductResponse updateProduct(ProductRequest productRequest) {
        checkIfProductExistsOrElseThrowException(productRequest.id());
        Product product = productRepository.save(productMapper.toProduct(productRequest));
        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        productRepository.deleteById(id);
    }

    private void checkIfProductExistsOrElseThrowException(Long id) {
        productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
