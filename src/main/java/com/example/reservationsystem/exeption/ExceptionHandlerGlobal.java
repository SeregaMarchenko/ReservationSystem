package com.example.reservationsystem.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerGlobal {
    @ExceptionHandler(value = {CustomValidException.class, jakarta.validation.ValidationException.class})
    public ResponseEntity<HttpStatusCode> customValidExceptionHandler(Exception exception) {
        log.error("valid exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}
