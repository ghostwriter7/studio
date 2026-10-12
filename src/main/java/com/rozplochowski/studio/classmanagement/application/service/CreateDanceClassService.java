package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.out.SaveDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDanceClassService implements CreateDanceClassUseCase {

    private final SaveDanceClassPort saveDanceClassPort;

    @Override
    @Transactional
    public DanceClass create(CreateDanceClassCommand command) {
        var danceClass = new DanceClass(command.activity(), command.description(), command.difficulty());
        return saveDanceClassPort.save(danceClass);
    }
}
