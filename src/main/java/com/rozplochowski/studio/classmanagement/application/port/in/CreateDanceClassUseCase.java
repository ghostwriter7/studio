package com.rozplochowski.studio.classmanagement.application.port.in;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;

public interface CreateDanceClassUseCase {

    DanceClass create(CreateDanceClassCommand command);
}
