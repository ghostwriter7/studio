package com.rozplochowski.studio.shared.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.application.exception.DuplicateClassroomNameException;
import com.rozplochowski.studio.classrooms.domain.exception.InvalidClassroomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(exception = {
            ClassroomNotFoundException.class,
            DanceClassNotFoundException.class
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

    @ExceptionHandler(exception = {
            DuplicateClassroomNameException.class
    })
    ProblemDetail handleDuplicateClassroomName(DuplicateClassroomNameException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
}
