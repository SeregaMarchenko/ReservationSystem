package com.example.reservationsystem.exeption;

import com.example.reservationsystem.exeption.custom_exception.AccessException;
import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.exeption.custom_exception.EmptyFileException;
import com.example.reservationsystem.exeption.custom_exception.FieldException;
import com.example.reservationsystem.exeption.custom_exception.FullCapacityException;
import com.example.reservationsystem.exeption.custom_exception.JwtException;
import com.example.reservationsystem.exeption.custom_exception.SameUserInDatabase;
import com.example.reservationsystem.exeption.custom_exception.UpdateException;
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

    @ExceptionHandler(value = {SameUserInDatabase.class})
    public ResponseEntity<HttpStatusCode> sameUserInDatabase(Exception exception) {
        log.error(String.valueOf(exception));
        return new ResponseEntity<>(HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<HttpStatusCode> jwtException(Exception exception) {
        log.error("jwt exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(value = {AccessException.class})
    public ResponseEntity<HttpStatusCode> accessException(Exception exception) {
        log.error("access exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(value = {FieldException.class})
    public ResponseEntity<HttpStatusCode> fieldException(Exception exception) {
        log.error("field exception: " + exception);
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}
