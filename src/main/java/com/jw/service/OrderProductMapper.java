package com.jw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jw.dto.reservation.ProductReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderProductMapper {

    private final ObjectMapper objectMapper;

    public ProductReservationRequest toProductReservationRequest(String json) {
        return (ProductReservationRequest) jsonToObject(json, ProductReservationRequest.class);
    }

    @SneakyThrows
    public String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    private Object jsonToObject(String json, Class<?> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}
