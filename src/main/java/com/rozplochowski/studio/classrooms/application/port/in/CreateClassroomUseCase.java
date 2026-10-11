package com.rozplochowski.studio.classrooms.application.port.in;

import com.rozplochowski.studio.classrooms.domain.Classroom;

public interface CreateClassroomUseCase {

    Classroom create(CreateClassroomCommand command);
}
