package com.jw.error;

import com.jw.dto.reservation.ReservationResult;
import java.util.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse globalHandler(Exception exception) {
        log.error(exception);
        return new ErrorResponse(exception.getMessage(), Collections.emptyList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ErrorResponse productNotFoundHandler(ProductNotFoundException exception) {
        log.error(exception);
        return new ErrorResponse(exception.getMessage(), Collections.emptyList());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ReservationFailException.class)
    @ResponseBody
    public ReservationResult reservationFailHandler(ReservationFailException exception) {
        log.error(exception);
        return new ReservationResult(
                exception.getOrderId(), "FAIL", exception.getCause().getMessage());
    }
}
