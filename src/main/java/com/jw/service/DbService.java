package com.jw.service;

import com.jw.entity.Product;
import com.jw.error.ProductNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DbService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        return productRepository.findById(id).get();
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

    @Transactional
    public void updateProductQuantity(Long productId, int quantity) {
        checkIfProductExistsOrElseThrowException(productId);
        Product product = getProductById(productId);
        product.setAvailable(product.getAvailable() + quantity);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkIfProductExistsOrElseThrowException(id);
        productRepository.deleteById(id);
    }

    private void checkIfProductExistsOrElseThrowException(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
