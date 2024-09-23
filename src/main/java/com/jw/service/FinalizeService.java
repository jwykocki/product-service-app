package com.jw.service;

import com.jw.dto.finalize.FinalizedOrderQueue;
import com.jw.dto.finalize.FinalizedProductQueue;
import com.jw.entity.Product;
import com.jw.error.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinalizeService {

    private final ProductRepository productRepository;

    @Transactional
    public void finalizeProduct(FinalizedOrderQueue finalizedOrderQueue) {
        log.info("Processing finalize reservation request (id = {})", finalizedOrderQueue.orderId());
        FinalizedProductQueue finalizedProduct = finalizedOrderQueue.product();
        Product product = productRepository
                .findById(finalizedProduct.productId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id %s was not found".formatted(finalizedProduct.productId())));
        int notFinalized = countNotFinalizedProducts(finalizedProduct);
        increaseAvailableProductsByNotFinalized(product, notFinalized);
        decreaseReservedProductsByFinalized(product, finalizedProduct);
    }

    private void increaseAvailableProductsByNotFinalized(Product product, int notFinalized) {
        product.setAvailable(product.getAvailable() + notFinalized);
    }

    private void decreaseReservedProductsByFinalized(Product product, FinalizedProductQueue finalizedProduct) {
        product.setReserved(product.getReserved() - finalizedProduct.finalized());
    }

    private int countNotFinalizedProducts(FinalizedProductQueue finalizedProduct) {
        return finalizedProduct.reserved() - finalizedProduct.finalized();
    }
}
