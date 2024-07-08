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

    private final DbService dbService;
    private final ProductMapper productMapper;

    public ProductResponse saveProduct(ProductRequest productRequest) {
        return productMapper.toResponse(dbService.saveProduct(productMapper.toProduct(productRequest)));
    }

    public ProductsResponse getAllProducts() {
        return new ProductsResponse(dbService.getAllProducts().stream()
                .map(productMapper::toResponse)
                .toList());
    }

    public ProductResponse getProductById(Long id) {
        return productMapper.toResponse(dbService.getProductById(id));
    }

    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product product = dbService.updateProduct(productMapper.toProduct(productRequest));
        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long id) {
        dbService.deleteProduct(id);
    }
}
