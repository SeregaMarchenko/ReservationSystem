package com.example.reservationsystem.exeption.custom_exception;

public class FieldException extends RuntimeException{
    private String message;

    public FieldException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Incorrect field ! Exceptions: " + message;
    }
}
