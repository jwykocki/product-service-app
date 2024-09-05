package com.jw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class QueueProcessor {

    private final QueueSender queueSender;
    private final ReservationService reservationService;

    @RabbitListener(queues = "unprocessed-products")
    public void receiveMessage(String request) throws JsonProcessingException {
        System.out.println("Received reservation request: " + request);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductReservationRequest productReservationRequest =
                objectMapper.readValue(request, ProductReservationRequest.class);
        System.out.println(productReservationRequest);
        String status = reservationService.processProductReservation(productReservationRequest);
        ProductReservationResult result = new ProductReservationResult(
                productReservationRequest.orderId(), productReservationRequest.product(), status);
        queueSender.send(result);
    }
}
