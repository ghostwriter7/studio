package com.rozplochowski.studio.classrooms.application.port.out;

import com.rozplochowski.studio.classrooms.domain.Classroom;

public interface SaveClassroomPort {

    Classroom save(Classroom classroom);
}
