package com.example.reservationsystem.exeption.custom_exception;

public class EmptyFileException extends RuntimeException{
    private String message;

    public EmptyFileException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "File is empty ! Exceptions: " + message;
    }
}
