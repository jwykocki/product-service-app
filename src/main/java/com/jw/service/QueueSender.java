package com.jw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class QueueSender {

    private final RabbitTemplate template;
    private final Queue queue;

    public void send(ProductReservationResult productReservationResult) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(productReservationResult);
        log.info("Sending reservation result: {}", s);
        template.send(queue.getName(), new Message(s.getBytes()));
    }
}
