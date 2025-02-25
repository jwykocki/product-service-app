package com.jw.service;

import com.jw.entity.Product;
import com.jw.error.ProductNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DbService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return getProductOrElseThrowException(id);
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        checkIfProductExistsOrElseThrowException(product.getProductid());
        return productRepository.save(product);
    }

    public void updateProductQuantity(Long productId, int quantity) {
        Product product = productRepository
                .findProductByProductid(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product with id %s was not found".formatted(productId)));
        product.setAvailable(product.getAvailable() + quantity);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        productRepository.deleteById(id);
    }

    private void checkIfProductExistsOrElseThrowException(Long id) {
        productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id %s was not found".formatted(id)));
    }

    private Product getProductOrElseThrowException(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id %s was not found".formatted(id)));
    }
}
