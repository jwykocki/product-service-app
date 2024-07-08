package com.jw.dto.reservation;

import java.util.List;

public record ProductReservationRequest(Long orderId, List<OrderProductRequest> orderProducts){}
