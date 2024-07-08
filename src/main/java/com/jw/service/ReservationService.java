package com.jw.service;

import com.jw.dto.reservation.OrderProductRequest;
import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.entity.Product;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final DbService dbService;

    public void processProductReservation(ProductReservationRequest productReservationRequest) {
        for (OrderProductRequest orderProduct : productReservationRequest.orderProducts()) {
            Product product = Optional.of(dbService.getProductById(orderProduct.productId()))
                    .orElseThrow(() -> new RuntimeException(""));
            if (checkIfReservationIsAvailable(orderProduct, product)) {
                makeReservation(orderProduct, product);
            } else {
                throw new RuntimeException("Reservation cannot be processed");
            }
        }
    }

    private boolean checkIfReservationIsAvailable(OrderProductRequest orderProduct, Product product) {
        int available = product.getAvailable();
        return available >= orderProduct.quantity();
    }

    private void makeReservation(OrderProductRequest orderProduct, Product product) {
        int toReserve = orderProduct.quantity();
        int quantityLeft = product.getAvailable() - toReserve;
        product.setAvailable(quantityLeft);
        product.setReserved(toReserve);
        dbService.updateProduct(product);
    }
}
