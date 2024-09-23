package com.jw.service;

import com.jw.dto.reservation.ProductReservationResult;
import com.jw.stock.UpdateProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueWriter {

    private final RabbitTemplate template;
    private final OrderProductMapper mapper;

    public void sendProductReservationResult(ProductReservationResult productReservationResult) {
        log.info(
                "Sending reservation result on processed-products queue (id = {})", productReservationResult.orderId());
        String result = mapper.toJson(productReservationResult);
        template.send("processed-products", new Message(result.getBytes()));
    }

    public void sendUpdateProduct(UpdateProduct updateProduct) {
        String result = mapper.toJson(updateProduct);
        template.send("update-products", new Message(result.getBytes()));
    }
}
