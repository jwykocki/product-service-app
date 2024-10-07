package com.jw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jw.dto.finalize.FinalizedOrderQueue;
import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.stock.UpdateProduct;
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

    public FinalizedOrderQueue toFinalizedOrderQueue(String json) {
        return (FinalizedOrderQueue) jsonToObject(json, FinalizedOrderQueue.class);
    }

    public UpdateProduct toUpdateProduct(String json) {
        return (UpdateProduct) jsonToObject(json, UpdateProduct.class);
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
