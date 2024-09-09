package com.jw.service;

import com.jw.dto.finalize.FinalizedOrderQueue;
import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.dto.reservation.ProductReservationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class QueueReader {

    private final QueueWriter queueWriter;
    private final ReservationService reservationService;
    private final OrderProductMapper mapper;
    private final FinalizeService finalizeService;

    @RabbitListener(queues = "unprocessed-products")
    public void readFromUnprocessedProducts(String request) {
        log.info("Received product reservation request");
        ProductReservationRequest productReservationRequest = mapper.toProductReservationRequest(request);
        String status = reservationService.processProductReservation(productReservationRequest);
        log.info("Processed product reservation request (id = {})", productReservationRequest.orderId());
        ProductReservationResult result = new ProductReservationResult(
                productReservationRequest.orderId(), productReservationRequest.product(), status);
        queueWriter.send(result);
    }

    @RabbitListener(queues = "finalized-products")
    public void readFromFinalizedProducts(String request) {
        log.info("Received finalize reservation request");
        FinalizedOrderQueue finalizedOrderQueue = mapper.toFinalizedOrderQueue(request);
        finalizeService.finalizeProduct(finalizedOrderQueue);
        log.info("Successfully processed finalize reservation request (id = {})", finalizedOrderQueue.orderId());
    }
}
