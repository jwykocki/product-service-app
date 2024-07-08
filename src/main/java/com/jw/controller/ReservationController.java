package com.jw.controller;

import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.service.ReservationService;
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
    public void reserveProducts(@RequestBody ProductReservationRequest productReservationRequest) {
        log.info("reserve products request: {}", productReservationRequest);
        reservationService.processProductReservation(productReservationRequest);
    }
}
