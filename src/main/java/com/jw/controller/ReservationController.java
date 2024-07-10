package com.jw.controller;

import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.dto.reservation.ReservationResult;
import com.jw.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseBody
    public ReservationResult reserveProducts(@RequestBody @Valid ProductReservationRequest productReservationRequest) {
        log.info("reserve products request: {}", productReservationRequest);
        reservationService.processReservationRequest(productReservationRequest);
        return new ReservationResult(
                productReservationRequest.orderId(), "SUCCESS", "Reservation was processed successfully");
    }
}
