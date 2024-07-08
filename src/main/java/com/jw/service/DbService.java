package com.jw.service;

import com.jw.entity.Product;
import com.jw.error.ProductNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DbService {

    private final ProductRepository productRepository;

    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    Product getProductById(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        return productRepository.findById(id).get();
    }

    Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    Product updateProduct(Product product) {
        checkIfProductExistsOrElseThrowException(product.getProductid());
        return productRepository.save(product);
    }

    void deleteProduct(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        productRepository.deleteById(id);
    }

    private void checkIfProductExistsOrElseThrowException(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
