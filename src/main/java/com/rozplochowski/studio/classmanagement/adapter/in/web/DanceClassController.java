package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/dance-classes")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DanceClassController {
    private final CreateDanceClassUseCase createDanceClassUseCase;
    private final UpdateDanceClassUseCase updateDanceClassUseCase;
    private final WebDanceClassMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DanceClassResponse> createDanceClass(@Valid @RequestBody CreateDanceClassRequest request) {
        var command = mapper.toCommand(request);
        var danceClass = createDanceClassUseCase.create(command);

        var location = URI.create("/dance-classes/" + danceClass.getId());
        return ResponseEntity
                .created(location)
                .body(mapper.toResponse(danceClass));
    }

    @PutMapping("/{id}")
    DanceClassResponse updateDanceClass(@PathVariable @Positive long id, @Valid @RequestBody UpdateDanceClassRequest request) {
        var command = mapper.toCommand(id, request);
        var danceClass = updateDanceClassUseCase.updateDanceClass(command);
        return mapper.toResponse(danceClass);
    }
}
