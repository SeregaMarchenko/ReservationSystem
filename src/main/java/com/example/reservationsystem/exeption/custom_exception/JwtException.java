package com.example.reservationsystem.exeption.custom_exception;

public class JwtException extends RuntimeException{
    private String message;

    public JwtException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Jwt exception ! Exceptions: " + message;
    }
}
