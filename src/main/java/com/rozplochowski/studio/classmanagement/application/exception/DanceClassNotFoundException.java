package com.rozplochowski.studio.classmanagement.application.exception;

public class DanceClassNotFoundException extends RuntimeException {
    public DanceClassNotFoundException(Long id) {
        super("Dance class with id " + id + " not found");
    }
}
