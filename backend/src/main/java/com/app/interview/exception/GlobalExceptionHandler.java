package com.app.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SlotFullException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSlotFull(SlotFullException ex) {
        return ex.getMessage();
    }
}
