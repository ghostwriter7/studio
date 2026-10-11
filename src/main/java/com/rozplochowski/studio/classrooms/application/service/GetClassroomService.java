package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.application.port.in.GetClassroomByIdUseCase;
import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GetClassroomService implements GetClassroomByIdUseCase {
    private final LoadClassroomPort loadClassroomPort;

    @Override
    public Classroom getById(Long id) {
        return loadClassroomPort.loadById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));
    }
}
