package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.application.port.in.UpdateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.in.UpdateClassroomUseCase;
import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateClassroomService implements UpdateClassroomUseCase {

    private final LoadClassroomPort loadClassroomPort;
    private final SaveClassroomPort saveClassroomPort;

    @Override
    @Transactional
    public Classroom update(UpdateClassroomCommand command) {
        var classroom = loadClassroomPort.loadById(command.id())
                .orElseThrow(() -> new ClassroomNotFoundException(command.id()));

        classroom.updateDetails(command.name(), command.capacity());

        return saveClassroomPort.save(classroom);
    }
}
