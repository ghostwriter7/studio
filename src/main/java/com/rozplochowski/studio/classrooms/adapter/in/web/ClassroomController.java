package com.rozplochowski.studio.classrooms.adapter.in.web;

import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomUseCase;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ClassroomController {
    private final CreateClassroomUseCase createClassroomUseCase;

    @PostMapping
    ClassroomResponse createClassroom(@Valid @RequestBody CreateClassroomRequest request) {
       var command = new CreateClassroomCommand(request.name(), request.capacity());
       var classroom = createClassroomUseCase.create(command);
       return new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getCapacity());
    }
}
