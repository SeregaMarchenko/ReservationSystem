package com.example.reservationsystem.exeption.custom_exception;

public class AccessException extends RuntimeException{
    private String message;

    public AccessException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Problem with access ! Exceptions: " + message;
    }
}
