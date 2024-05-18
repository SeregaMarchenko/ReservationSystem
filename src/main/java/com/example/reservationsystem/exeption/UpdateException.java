package com.example.reservationsystem.exeption;

public class UpdateException extends RuntimeException{
    private String message;

    public UpdateException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Update is fail ! Exceptions: " + message;
    }
}
