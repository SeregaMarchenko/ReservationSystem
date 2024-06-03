package com.example.reservationsystem.exeption.custom_exception;

public class SameUserInDatabase extends RuntimeException{
    private String message;

    public SameUserInDatabase(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Registration problem ! We already have user with username: " + message;
    }
}
