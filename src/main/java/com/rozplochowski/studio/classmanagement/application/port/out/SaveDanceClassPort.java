package com.rozplochowski.studio.classmanagement.application.port.out;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;

public interface SaveDanceClassPort {

    DanceClass save(DanceClass danceClass);
}
