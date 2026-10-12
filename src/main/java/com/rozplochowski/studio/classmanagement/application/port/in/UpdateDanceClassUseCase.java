package com.rozplochowski.studio.classmanagement.application.port.in;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;

public interface UpdateDanceClassUseCase {

    DanceClass updateDanceClass(UpdateDanceClassCommand command);
}
