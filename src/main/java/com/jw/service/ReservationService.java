package com.jw.service;

import com.jw.dto.reservation.OrderProductRequest;
import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.entity.Product;
import com.jw.error.ProductNotAvailableException;
import com.jw.error.ProductNotFoundException;
import com.jw.error.ReservationFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final DbService dbService;

    @Transactional
    public void processReservationRequest(ProductReservationRequest productReservationRequest) {
        try {
            log.info("Processing reservation request");
            productReservationRequest.orderProducts().forEach(this::processProductReservation);
        } catch (ProductNotAvailableException | ProductNotFoundException e) {
            throw new ReservationFailException(productReservationRequest.orderId(), e);
        }
    }

    private void processProductReservation(OrderProductRequest orderProductRequest) {
        Product product = dbService.getProductById(orderProductRequest.productId());
        checkIfProductIsAvailable(orderProductRequest, product);
        makeReservation(orderProductRequest, product);
    }

    private void checkIfProductIsAvailable(OrderProductRequest orderProduct, Product product) {
        int available = product.getAvailable();
        if (available < orderProduct.quantity()) {
            throw new ProductNotAvailableException("Requested product is not available.");
        }
    }

    private void makeReservation(OrderProductRequest orderProduct, Product product) {
        int requestedQuantity = orderProduct.quantity();
        updateAvailableQuantity(product, requestedQuantity);
        updateReservedQuantity(product, requestedQuantity);
        dbService.updateProduct(product);
    }

    private void updateAvailableQuantity(Product product, int requestedQuantity) {
        int quantityLeft = product.getAvailable() - requestedQuantity;
        product.setAvailable(quantityLeft);
    }

    private void updateReservedQuantity(Product product, int requestedQuantity) {
        int alreadyReserved = product.getReserved();
        product.setReserved(alreadyReserved + requestedQuantity);
    }
}
