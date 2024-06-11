package com.jw.error;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse globalHandler(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse("An error occurred", Collections.emptyList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ErrorResponse productNotFoundHandler(ProductNotFoundException exception) {
        exception.printStackTrace();
        return new ErrorResponse(exception.getMessage(), Collections.emptyList());
    }
}
