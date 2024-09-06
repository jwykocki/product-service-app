package com.jw.dto.reservation;

public record ProductReservationResult(Long orderId, OrderProductRequest product, String status) {}
