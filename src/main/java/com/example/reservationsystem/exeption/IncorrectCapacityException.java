package com.example.reservationsystem.exeption;

public class IncorrectCapacityException extends RuntimeException{
    private String message;

    public IncorrectCapacityException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Problem with capacity ! Exceptions: " + message;
    }
}
