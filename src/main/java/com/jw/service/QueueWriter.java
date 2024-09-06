package com.jw.service;

import com.jw.dto.reservation.ProductReservationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueWriter {

    private final RabbitTemplate template;
    private final Queue queue;
    private final OrderProductMapper mapper;

    public void send(ProductReservationResult productReservationResult) {
        String result = mapper.toJson(productReservationResult);
        log.info("Sending reservation result: {}", result);
        template.send(queue.getName(), new Message(result.getBytes()));
    }
}
