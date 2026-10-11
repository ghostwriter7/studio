package com.rozplochowski.studio.shared.in.web;

import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.domain.exception.InvalidClassroomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(exception = {
            ClassroomNotFoundException.class,
    })
    ProblemDetail handleClassNotFound(ClassroomNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(exception = {
            InvalidClassroomException.class
    })
    ProblemDetail handleInvalidClassroom(InvalidClassroomException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
