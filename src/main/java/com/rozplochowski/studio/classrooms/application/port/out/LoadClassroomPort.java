package com.rozplochowski.studio.classrooms.application.port.out;

import com.rozplochowski.studio.classrooms.domain.Classroom;

import java.util.Optional;

public interface LoadClassroomPort {

    Optional<Classroom> loadById(long id);
}
