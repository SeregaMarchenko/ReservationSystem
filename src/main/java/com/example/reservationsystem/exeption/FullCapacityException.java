package com.example.reservationsystem.exeption;

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
