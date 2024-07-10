package com.jw.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderProductRequest(@NotNull Long productId, @Min(1) @NotNull Integer quantity) {}
