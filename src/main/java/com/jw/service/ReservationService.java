package com.jw.service;

import com.jw.dto.reservation.ProductReservationRequest;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    public boolean processProductReservation(ProductReservationRequest productReservationRequest) {
        return true;
    }
}
