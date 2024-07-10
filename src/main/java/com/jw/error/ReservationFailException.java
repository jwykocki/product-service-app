package com.jw.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFailException extends RuntimeException {
    private Long orderId;
    private Throwable cause;
}
