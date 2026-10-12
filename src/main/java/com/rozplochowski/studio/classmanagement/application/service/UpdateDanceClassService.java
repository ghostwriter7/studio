package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.application.port.out.UpdateDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UpdateDanceClassService implements UpdateDanceClassUseCase {
    private final LoadDanceClassPort loadDanceClassPort;
    private final UpdateDanceClassPort updateDanceClassPort;

    @Override
    @Transactional
    public DanceClass updateDanceClass(UpdateDanceClassCommand command) {
        var danceClass = loadDanceClassPort.findById(command.id())
                .orElseThrow(() -> new DanceClassNotFoundException(command.id()));

        danceClass.updateDetails(command.activity(), command.description(), command.difficulty());

        return updateDanceClassPort.update(danceClass);
    }
}
