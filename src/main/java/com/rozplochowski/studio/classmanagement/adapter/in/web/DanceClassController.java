package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassUseCase;
import jakarta.validation.Valid;
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
    private final DanceClassMapper danceClassMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DanceClassResponse> createDanceClass(@Valid @RequestBody CreateDanceClassRequest request) {
        var command = danceClassMapper.toCommand(request);
        var danceClass = createDanceClassUseCase.create(command);

        var location = URI.create("/dance-classes/" + danceClass.getId());
        return ResponseEntity
                .created(location)
                .body(danceClassMapper.toResponse(danceClass));
    }
}
