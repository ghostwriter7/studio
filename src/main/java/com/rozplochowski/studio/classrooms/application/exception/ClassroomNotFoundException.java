package com.rozplochowski.studio.classrooms.application.exception;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(long classroomId) {
        super("Classroom not found: " + classroomId);
    }
}
