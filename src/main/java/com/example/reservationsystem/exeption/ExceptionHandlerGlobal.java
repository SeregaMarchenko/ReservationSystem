package com.example.reservationsystem.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerGlobal {
    @ExceptionHandler(value = {CustomValidException.class, jakarta.validation.ValidationException.class})
    public ResponseEntity<HttpStatusCode> customValidExceptionHandler(Exception exception) {
        log.error("valid exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<HttpStatusCode> noSuchElementExceptionHandler(Exception exception) {
        log.error("no such element exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(value = FullCapacityException.class)
    public ResponseEntity<HttpStatusCode> fullCapacityExceptionHandler(Exception exception) {
        log.error("full capacity exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = UpdateException.class)
    public ResponseEntity<HttpStatusCode> updateExceptionHandler(Exception exception) {
        log.error("update exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = EmptyFileException.class)
    public ResponseEntity<HttpStatusCode> emptyFileExceptionHandler(Exception exception) {
        log.error("file exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<HttpStatusCode> runtimeExceptionHandler(Exception exception) {
        log.error("runtime exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}
