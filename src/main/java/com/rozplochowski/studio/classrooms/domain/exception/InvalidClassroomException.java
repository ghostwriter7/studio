package com.rozplochowski.studio.classrooms.domain.exception;

public class InvalidClassroomException extends RuntimeException{
    public InvalidClassroomException(String message) {
        super(message);
    }
}
