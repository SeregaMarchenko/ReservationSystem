package com.example.reservationsystem.exeption.custom_exception;

public class FullCapacityException extends RuntimeException {
    private String message;

    public FullCapacityException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Problem with reservation ! Exceptions: " + message;
    }
}
