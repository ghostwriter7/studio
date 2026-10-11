package com.rozplochowski.studio.classrooms.application.port.in;

import com.rozplochowski.studio.classrooms.domain.Classroom;

public interface GetClassroomByIdUseCase {

    Classroom getById(Long id);
}
