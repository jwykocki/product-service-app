package com.jw.dto.reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductReservationRequest(
        @NotNull Long orderId, @NotNull List<@Valid OrderProductRequest> orderProducts) {}
